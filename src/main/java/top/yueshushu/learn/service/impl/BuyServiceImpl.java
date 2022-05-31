package top.yueshushu.learn.service.impl;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import top.yueshushu.learn.common.SystemConst;
import top.yueshushu.learn.domain.TradeEntrustDo;
import top.yueshushu.learn.domainservice.TradeEntrustDomainService;
import top.yueshushu.learn.enumtype.*;
import top.yueshushu.learn.mode.ro.BuyRo;
import top.yueshushu.learn.mode.ro.TradeMoneyRo;
import top.yueshushu.learn.mode.vo.ConfigVo;
import top.yueshushu.learn.mode.vo.TradeMoneyVo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.service.BuyService;
import top.yueshushu.learn.service.ConfigService;
import top.yueshushu.learn.service.TradeEntrustService;
import top.yueshushu.learn.service.TradeMoneyService;
import top.yueshushu.learn.util.BigDecimalUtil;
import top.yueshushu.learn.util.StockUtil;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @ClassName:BuyServiceImpl
 * @Description TODO
 * @Author 岳建立
 * @Date 2022/1/8 11:17
 * @Version 1.0
 **/
@Service
@Slf4j(topic="买入股票:")
@Transactional
public class BuyServiceImpl implements BuyService {
    @Autowired
    private TradeEntrustService tradeEntrustService;
    @Autowired
    private TradeMoneyService tradeMoneyService;
    @Autowired
    private ConfigService configService;
    @Resource
    private TradeEntrustDomainService tradeEntrustDomainService;
    @Override
    public OutputResult buy(BuyRo buyRo) {
        //对非空的验证信息
        if(!StringUtils.hasText(buyRo.getCode())){
            return OutputResult.buildAlert("请传入买入的股票信息");
        }
        if(buyRo.getAmount()==null){
            return OutputResult.buildAlert("请传入买入的股票股数信息");
        }
        if(buyRo.getPrice()==null){
            return OutputResult.buildAlert("请传入买入的股票的价格信息");
        }
        //查询该员工对应的资产信息
        OutputResult moneyResult = getMoneyByUid(
                buyRo.getUserId(),
                buyRo.getMockType()
        );
        if(!moneyResult.getSuccess()){
            log.error("查询资产信息失败");
            return moneyResult;
        }
        //查询成功，获取对应的信息
        TradeMoneyVo tradeMoney = (TradeMoneyVo) moneyResult.getData();
        //获取对应的金额
        ConfigVo priceConfigVo = configService.getConfigByCode(
                buyRo.getUserId(),
                ConfigCodeType.TRAN_PRICE.getCode()
        );
        //获取对应的手续费
        BigDecimal buyMoney = getBuyMoney(
                buyRo.getAmount(),
                buyRo.getPrice(),
                BigDecimalUtil.toBigDecimal(priceConfigVo.getCodeValue())
        );
        BigDecimal useMoney = tradeMoney.getUseMoney();
        if(useMoney.compareTo(buyMoney)<0){
            return OutputResult.buildAlert("你的资产不足,无法申请买入");
        }
        //处理资产信息表
        //计算出，可用的与可取的之间的差值信息.
        BigDecimal takeoutMoney = tradeMoney.getTakeoutMoney();
        //获取之间的差值。
        BigDecimal subMoney = BigDecimalUtil.subBigDecimal(useMoney, takeoutMoney);
        BigDecimal useMoneyChange = SystemConst.DEFAULT_EMPTY;;
        BigDecimal takeoutMoneyChange = SystemConst.DEFAULT_EMPTY;
        tradeMoney.setUseMoney(
                BigDecimalUtil.subBigDecimal(
                        useMoney,buyMoney
                )
        );
        //自己就够了, 可用的减少,可取的，不变
        useMoneyChange = new BigDecimal(buyMoney.toString());
        if(subMoney.compareTo(buyMoney)<=0){
            //不够，那么 进行相应 的改变
            useMoneyChange = new BigDecimal(buyMoney.toString());
            //剩下的，从可取里面取出
            takeoutMoneyChange = BigDecimalUtil.subBigDecimal(
                    buyMoney,subMoney
            );
            //更改可用金额
            tradeMoney.setTakeoutMoney(
                    BigDecimalUtil.subBigDecimal(
                            takeoutMoney,takeoutMoneyChange
                    )
            );
        }
        //更新金额信息
        tradeMoneyService.updateMoney(
                null
        );

        TradeEntrustDo tradeEntrustDo = new TradeEntrustDo();
        tradeEntrustDo.setCode(buyRo.getCode());
        tradeEntrustDo.setName(buyRo.getName());
        tradeEntrustDo.setEntrustDate(DateUtil.date());
        tradeEntrustDo.setDealType(DealType.BUY.getCode());
        tradeEntrustDo.setEntrustNum(buyRo.getAmount());
        tradeEntrustDo.setEntrustPrice(BigDecimalUtil.convertFour(buyRo.getPrice()));
        tradeEntrustDo.setEntrustStatus(EntrustStatusType.ING.getCode());
        tradeEntrustDo.setEntrustCode(StockUtil.generateEntrustCode());
        tradeEntrustDo.setUseMoney(useMoneyChange);
        tradeEntrustDo.setTakeoutMoney(takeoutMoneyChange);
        tradeEntrustDo.setEntrustMoney(
               StockUtil.allMoney(
                       buyRo.getAmount(),
                       buyRo.getPrice()
               )
        );
        tradeEntrustDo.setHandMoney(
                StockUtil.getBuyHandMoney(
                        buyRo.getAmount(),
                        buyRo.getPrice(),
                        BigDecimalUtil.toBigDecimal(priceConfigVo.getCodeValue())
                )
        );
        tradeEntrustDo.setTotalMoney(buyMoney);
        tradeEntrustDo.setUserId(buyRo.getUserId());
        tradeEntrustDo.setEntrustType(EntrustType.HANDLER.getCode());
        tradeEntrustDo.setMockType(buyRo.getMockType());
        tradeEntrustDo.setFlag(DataFlagType.NORMAL.getCode());
        //放入一条记录到委托信息里面.
        tradeEntrustDomainService.save(tradeEntrustDo);
        return OutputResult.buildSucc("买入股票委托成功");
    }

    /**
     * 获取买入，总共需要的手续费
     * @param amount
     * @param price
     * @param tranPrice
     * @return
     */
    private BigDecimal getBuyMoney(Integer amount, BigDecimal price, BigDecimal tranPrice) {
        return StockUtil.getBuyMoney(
                amount,
                price,
                tranPrice
        );
    }

    private OutputResult getMoneyByUid(Integer userId, Integer mockType) {
        TradeMoneyRo tradeMoneyRo = new TradeMoneyRo();
        tradeMoneyRo.setUserId(userId);
        tradeMoneyRo.setMockType(mockType);
        //return tradeMoneyService.getByUserIdAndMockType(
        //        tradeMoneyRo
        //);
        return null;
    }
}
