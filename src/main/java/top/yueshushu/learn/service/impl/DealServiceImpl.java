package top.yueshushu.learn.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import top.yueshushu.learn.assembler.TradePositionAssembler;
import top.yueshushu.learn.common.SystemConst;
import top.yueshushu.learn.domain.TradeEntrustDo;
import top.yueshushu.learn.domainservice.TradeEntrustDomainService;
import top.yueshushu.learn.entity.TradeEntrust;
import top.yueshushu.learn.entity.TradeMoney;
import top.yueshushu.learn.entity.TradePosition;
import top.yueshushu.learn.enumtype.DealType;
import top.yueshushu.learn.enumtype.EntrustStatusType;
import top.yueshushu.learn.mode.ro.DealRo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.service.*;
import top.yueshushu.learn.util.BigDecimalUtil;
import top.yueshushu.learn.util.StockRedisUtil;
import top.yueshushu.learn.util.StockUtil;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * @ClassName:RevokeServiceImpl
 * @Description TODO
 * @Author 岳建立
 * @Date 2022/1/8 16:56
 * @Version 1.0
 **/
@Service
@Slf4j(topic = "成交委托单")
@Transactional
public class DealServiceImpl implements DealService {
    @Autowired
    private TradeMoneyService tradeMoneyService;
    @Autowired
    private TradeEntrustService tradeEntrustService;
    @Autowired
    private TradePositionService tradePositionService;
    @Autowired
    private TradeDealService tradeDealService;
    @Autowired
    private StockRedisUtil stockRedisUtil;
    @Resource
    private TradePositionAssembler tradePositionAssembler;
    @Resource
    private TradeEntrustDomainService tradeEntrustDomainService;
    @Override
    @Async
    public OutputResult deal(DealRo dealRo) {
        if(dealRo.getId()==null){
            return OutputResult.buildAlert("请选择要成交的委托单信息");
        }
        //查询单号信息
        TradeEntrustDo tradeEntrustDo = tradeEntrustDomainService.getById(dealRo.getId());
        if(null== tradeEntrustDo){
            return OutputResult.buildAlert("传入的委托编号id不正确");
        }
        if(!tradeEntrustDo.getUserId().equals(dealRo.getUserId())){
            return OutputResult.buildAlert("你不能成交别人的记录信息");
        }
        if(!EntrustStatusType.ING.getCode().equals(tradeEntrustDo.getEntrustStatus())){
            return OutputResult.buildAlert("委托状态不是进行中");
        }
        //获取委托的类型
        Integer dealType = tradeEntrustDo.getDealType();
        if(DealType.BUY.getCode().equals(dealType)){
           return buyDeal(tradeEntrustDo);
        }else{
            return sellDeal(tradeEntrustDo);
        }
    }

    @Override
    public void mockDealXxlJob(DealRo dealRo) {
        //获取当前所有的今日委托单信息，正在委托的.
        List<TradeEntrust> tradeEntrustDoList = tradeEntrustService.listNowRunEntrust(dealRo.getUserId(),
                dealRo.getMockType());
        if(CollectionUtils.isEmpty(tradeEntrustDoList)){
            return ;
        }
        //进行处理.
        for(TradeEntrust tradeEntrustDo : tradeEntrustDoList){
            //获取当前的股票
            String code = tradeEntrustDo.getCode();
            //获取信息
            BigDecimal price = stockRedisUtil.getPrice(code);
            if(price.compareTo(SystemConst.DEFAULT_DEAL_PRICE)<=0){
                //没有从缓存里面获取到价格
                //todo 异常
                return ;
            }
            if(DealType.BUY.getCode().equals(tradeEntrustDo.getDealType())){
                //买的时候，  当前价格 < 买入价格，则成交.
                if(price.compareTo(tradeEntrustDo.getEntrustPrice())<0){
                    DealRo newRo = new DealRo();
                    BeanUtils.copyProperties(dealRo,newRo);
                    newRo.setId(tradeEntrustDo.getId());
                    deal(newRo);
                    //重置昨天的价格 为当天买入的价格.
                    stockRedisUtil.setYesPrice(
                            code,price
                    );
                }
            }else{
                //买的时候，  当前价格 > 卖出价格，则成交.
                if(price.compareTo(tradeEntrustDo.getEntrustPrice())>0){
                    DealRo newRo = new DealRo();
                    BeanUtils.copyProperties(dealRo,newRo);
                    newRo.setId(tradeEntrustDo.getId());
                    deal(newRo);
                    //重置昨天的价格 为当天买入的价格.
                    stockRedisUtil.setYesPrice(
                            code,price
                    );
                }
            }
        }

    }

    private OutputResult sellDeal(TradeEntrustDo tradeEntrustDo) {
        //取消的话，改变这个记录的状态。
        tradeEntrustDo.setEntrustStatus(EntrustStatusType.SUCCESS.getCode());
        tradeEntrustDo.setDealCode(StockUtil.generateDealCode());
        //更新
        tradeEntrustDomainService.updateById(tradeEntrustDo);
        //成交了，金额不动。 动持仓信息
        //看持仓里面，是否有此信息.
        TradePosition tradePositionDo = tradePositionService.getPositionByCode(
                tradeEntrustDo.getUserId(),
                tradeEntrustDo.getMockType(),
                tradeEntrustDo.getCode()
        );
        if(null== tradePositionDo){
           log.error("没有持仓信息，出现异常");
           return OutputResult.buildFail("没有持仓信息，出现异常");
        }

        //查询一下可用数量
        if(tradePositionDo.getAllAmount().equals(tradeEntrustDo.getEntrustNum())){
            //说明全卖完了
            log.info("股票{}进行清仓成交", tradePositionDo.getName());
            //需要删除
           // tradePositionService.removeById(tradePositionDo.getId());
        }else{
            //修改成本价
            tradePositionDo.setAvgPrice(
                    StockUtil.calcSellAvgPrice(
                            tradePositionDo.getAllAmount(),
                            tradePositionDo.getAvgPrice(),
                            tradeEntrustDo.getTotalMoney(),
                            tradeEntrustDo.getEntrustNum()
                    )
            );
            //买入成功
            tradePositionDo.setAllAmount(
                    tradePositionDo.getAllAmount()-
                            tradeEntrustDo.getEntrustNum()
            );
        }
     //   tradePositionService.updateById(tradePositionAssembler.entityToDo(tradePositionDo));

        //对个人的资产，需要进行添加的操作.

        TradeMoney tradeMoneyDo = tradeMoneyService.getByUserIdAndMockType(
                tradeEntrustDo.getUserId(),
                tradeEntrustDo.getMockType()
        );
        if(null== tradeMoneyDo){
            log.error("个人资产{} 信息出现异常", tradeEntrustDo.getUserId());
        }
        //增加
        tradeMoneyDo.setUseMoney(
                BigDecimalUtil.addBigDecimal(
                        tradeMoneyDo.getUseMoney(),
                        tradeEntrustDo.getTotalMoney()
                )
        );
        //市值金额减少
        tradeMoneyDo.setMarketMoney(
                BigDecimalUtil.subBigDecimal(
                        tradeMoneyDo.getMarketMoney(),
                        BigDecimalUtil.toBigDecimal(
                                tradeEntrustDo.getEntrustPrice(),
                                new BigDecimal(
                                        tradeEntrustDo.getEntrustNum()
                                )
                        )
                )
        );
        //总金额会去掉相关的手续费
        tradeMoneyDo.setTotalMoney(
                BigDecimalUtil.subBigDecimal(
                        tradeMoneyDo.getTotalMoney(),
                        tradeEntrustDo.getHandMoney()
                )
        );
        tradeMoneyService.updateMoney(tradeMoneyDo);
        //添加一条记录到成交表里面
        tradeDealService.addDealRecord(tradeEntrustDo);
        return OutputResult.buildSucc("成交卖的委托");
    }

    private OutputResult buyDeal(TradeEntrustDo tradeEntrustDo) {
        //取消的话，改变这个记录的状态。
        tradeEntrustDo.setEntrustStatus(EntrustStatusType.SUCCESS.getCode());
        tradeEntrustDo.setDealCode(StockUtil.generateDealCode());
        //更新
        tradeEntrustDomainService.updateById(tradeEntrustDo);
        //成交了，金额不动。 动持仓信息
        //看持仓里面，是否有此信息.
        TradePosition tradePositionDo = tradePositionService.getPositionByCode(
                tradeEntrustDo.getUserId(),
                tradeEntrustDo.getMockType(),
                tradeEntrustDo.getCode()
        );
        if(null== tradePositionDo){
            //新添加持仓信息
            tradePositionDo = new TradePosition();
            tradePositionDo.setCode(tradeEntrustDo.getCode());
            tradePositionDo.setName(tradeEntrustDo.getName());
            tradePositionDo.setAllAmount(tradeEntrustDo.getEntrustNum());
            tradePositionDo.setUseAmount(tradeEntrustDo.getEntrustNum());
            tradePositionDo.setAvgPrice(
                    BigDecimalUtil.div(
                            tradeEntrustDo.getTotalMoney(),
                            new BigDecimal(
                                    tradeEntrustDo.getEntrustNum()
                            )
                    )
            );
            tradePositionDo.setUserId(tradeEntrustDo.getUserId());
            tradePositionDo.setMockType(tradeEntrustDo.getMockType());
           // tradePositionService.save(tradePositionAssembler.entityToDo(tradePositionDo));
        }else{
            //修改成本价
            tradePositionDo.setAvgPrice(
                    StockUtil.calcBuyAvgPrice(
                            tradePositionDo.getAllAmount(),
                            tradePositionDo.getAvgPrice(),
                            tradeEntrustDo.getTotalMoney(),
                            tradeEntrustDo.getEntrustNum()
                    )
            );
            //买入成功
            tradePositionDo.setAllAmount(
                    tradePositionDo.getAllAmount()+
                            tradeEntrustDo.getEntrustNum()
            );
           // tradePositionService.updateById(tradePositionAssembler.entityToDo(tradePositionDo));
        }

        //对个人的资产，需要进行减少的操作.
        TradeMoney tradeMoneyDo = tradeMoneyService.getByUserIdAndMockType(
                tradeEntrustDo.getUserId(),
                tradeEntrustDo.getMockType()
        );
        if(null== tradeMoneyDo){
            log.error("个人资产{} 信息出现异常", tradeEntrustDo.getUserId());
        }
        //市值金额增加
        tradeMoneyDo.setMarketMoney(
                BigDecimalUtil.addBigDecimal(
                        tradeMoneyDo.getMarketMoney(),
                        BigDecimalUtil.toBigDecimal(
                                tradeEntrustDo.getEntrustPrice(),
                                new BigDecimal(tradeEntrustDo.getEntrustNum())
                        )
                )
        );
        //总金额会去掉相关的手续费
        tradeMoneyDo.setTotalMoney(
                BigDecimalUtil.subBigDecimal(
                        tradeMoneyDo.getTotalMoney(),
                        tradeEntrustDo.getHandMoney()
                )
        );
      //  tradeMoneyService.updateById(tradeMoneyDo);
        //添加一条记录到成交表里面
        tradeDealService.addDealRecord(tradeEntrustDo);
        return OutputResult.buildSucc("成交买的委托");
    }
}
