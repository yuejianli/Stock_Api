package top.yueshushu.learn.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.yueshushu.learn.assembler.TradePositionAssembler;
import top.yueshushu.learn.domain.TradeEntrustDo;
import top.yueshushu.learn.domainservice.TradeEntrustDomainService;
import top.yueshushu.learn.entity.TradeMoney;
import top.yueshushu.learn.entity.TradePosition;
import top.yueshushu.learn.enumtype.DealType;
import top.yueshushu.learn.enumtype.EntrustStatusType;
import top.yueshushu.learn.mode.ro.RevokeRo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.service.RevokeService;
import top.yueshushu.learn.service.TradeEntrustService;
import top.yueshushu.learn.service.TradeMoneyService;
import top.yueshushu.learn.service.TradePositionService;
import top.yueshushu.learn.util.BigDecimalUtil;

import javax.annotation.Resource;

/**
 * @ClassName:RevokeServiceImpl
 * @Description TODO
 * @Author 岳建立
 * @Date 2022/1/8 16:56
 * @Version 1.0
 **/
@Service
@Slf4j(topic = "撤销委托单")
@Transactional
public class RevokeServiceImpl implements RevokeService {
    @Autowired
    private TradeMoneyService tradeMoneyService;
    @Autowired
    private TradeEntrustService tradeEntrustService;
    @Autowired
    private TradePositionService tradePositionService;
    @Resource
    private TradePositionAssembler tradePositionAssembler;
    @Resource
    private TradeEntrustDomainService tradeEntrustDomainService;

    @Override
    public OutputResult revoke(RevokeRo revokeRo) {
        if(revokeRo.getId()==null){
            return OutputResult.buildAlert("请选择要撤销的委托单信息");
        }
        //查询单号信息
        TradeEntrustDo tradeEntrustDo = tradeEntrustDomainService.getById(revokeRo.getId());
        if(null== tradeEntrustDo){
            return OutputResult.buildAlert("传入的委托编号id不正确");
        }
        if(!tradeEntrustDo.getUserId().equals(revokeRo.getUserId())){
            return OutputResult.buildAlert("你不能撤销别人的记录信息");
        }
        if(!EntrustStatusType.ING.getCode().equals(tradeEntrustDo.getEntrustStatus())){
            return OutputResult.buildAlert("委托状态不是进行中");
        }
        //获取委托的类型
        Integer dealType = tradeEntrustDo.getDealType();
        if(DealType.BUY.getCode().equals(dealType)){
           return buyRevoke(tradeEntrustDo);
        }else{
            return sellRevoke(tradeEntrustDo);
        }
    }

    private OutputResult sellRevoke(TradeEntrustDo tradeEntrustDo) {
        //取消的话，改变这个记录的状态。
        tradeEntrustDo.setEntrustStatus(EntrustStatusType.REVOKE.getCode());
        //更新
        tradeEntrustDomainService.updateById(tradeEntrustDo);

        TradePosition tradePosition = tradePositionService.getPositionByCode(
                tradeEntrustDo.getUserId(),
                tradeEntrustDo.getMockType(),
                tradeEntrustDo.getCode()
        );
        if(tradePosition ==null){
            return OutputResult.buildAlert("没有持仓信息，出现了异常");
        }
        tradePosition.setUseAmount(
                tradePosition.getUseAmount()
                        + tradeEntrustDo.getEntrustNum()
        );
        //更新
      //  tradePositionService.update(tradePositionAssembler.entityToDo(tradePosition));
        return OutputResult.buildSucc("撤销卖出委托成功");
    }

    private OutputResult buyRevoke(TradeEntrustDo tradeEntrustDo) {
        //取消的话，改变这个记录的状态。
        tradeEntrustDo.setEntrustStatus(EntrustStatusType.REVOKE.getCode());
        //更新
        tradeEntrustDomainService.updateById(tradeEntrustDo);

        //将金额回滚
       TradeMoney tradeMoneyDo =  tradeMoneyService.getByUserIdAndMockType(
                tradeEntrustDo.getUserId(),
                tradeEntrustDo.getMockType()
        );
        if(null== tradeMoneyDo){
            log.error("没有获取到用户的资金信息");
        }
        //获取到后，需要进行更新
        tradeMoneyDo.setUseMoney(
                BigDecimalUtil.addBigDecimal(
                        tradeMoneyDo.getUseMoney(),
                        tradeEntrustDo.getUseMoney()
                )
        );
        tradeMoneyDo.setTakeoutMoney(
                BigDecimalUtil.addBigDecimal(
                        tradeMoneyDo.getTakeoutMoney(),
                        tradeEntrustDo.getTakeoutMoney()
                )
        );
        tradeMoneyService.updateMoney(
               // tradeMoneyDo
                null
        );
        return OutputResult.buildSucc("撤销买的委托成功");
    }
}
