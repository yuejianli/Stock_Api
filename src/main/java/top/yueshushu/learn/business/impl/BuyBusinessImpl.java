package top.yueshushu.learn.business.impl;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.yueshushu.learn.api.TradeResultVo;
import top.yueshushu.learn.api.request.SubmitRequest;
import top.yueshushu.learn.api.response.SubmitResponse;
import top.yueshushu.learn.business.BuyBusiness;
import top.yueshushu.learn.common.ResultCode;
import top.yueshushu.learn.common.SystemConst;
import top.yueshushu.learn.domain.TradeEntrustDo;
import top.yueshushu.learn.domainservice.TradeEntrustDomainService;
import top.yueshushu.learn.entity.Stock;
import top.yueshushu.learn.entity.TradeMoney;
import top.yueshushu.learn.enumtype.ConfigCodeType;
import top.yueshushu.learn.enumtype.DataFlagType;
import top.yueshushu.learn.enumtype.DealType;
import top.yueshushu.learn.enumtype.EntrustStatusType;
import top.yueshushu.learn.helper.TradeRequestHelper;
import top.yueshushu.learn.mode.ro.BuyRo;
import top.yueshushu.learn.mode.vo.ConfigVo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.service.ConfigService;
import top.yueshushu.learn.service.TradeMoneyService;
import top.yueshushu.learn.service.cache.StockCacheService;
import top.yueshushu.learn.util.BigDecimalUtil;
import top.yueshushu.learn.util.StockUtil;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @Description 委托买入编排处理
 * @Author yuejianli
 * @Date 2022/5/28 19:44
 **/
@Slf4j(topic = "buy")
@Service
public class BuyBusinessImpl implements BuyBusiness {

    @Resource
    private TradeMoneyService tradeMoneyService;
    @Resource
    private ConfigService configService;
    @Resource
    private TradeEntrustDomainService tradeEntrustDomainService;
    @Resource
    private StockCacheService stockCacheService;
    @Resource
    private TradeRequestHelper tradeRequestHelper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OutputResult buy(BuyRo buyRo) {
        log.info(">>>试图买入股票 {},股票信息是:{}", buyRo.getCode(), buyRo);
        Stock stock = stockCacheService.selectByCode(buyRo.getCode());
        if (stock == null) {
            return OutputResult.buildAlert(ResultCode.STOCK_CODE_NO_EXIST);
        }
        log.info("买入股票时用户{}的股票编码验证通过", buyRo.getUserId());
        //查询该员工对应的资产信息
       TradeMoney tradeMoney = tradeMoneyService.getByUserIdAndMockType(
                buyRo.getUserId(), buyRo.getMockType());
        if(tradeMoney ==null){
            log.error("查询用户{}资产信息失败",buyRo.getUserId());
           return OutputResult.buildAlert(ResultCode.TRADE_NO_MONEY);
        }
        log.info("买入股票时用户{}的资产验证通过", buyRo.getUserId());
        //验证钱够不够
        ConfigVo priceConfigVo = configService.getConfigByCode(
                buyRo.getUserId(),
                ConfigCodeType.TRAN_PRICE.getCode()
        );
        //获取对应的手续费
        BigDecimal buyMoney = getBuyMoney(
                buyRo.getAmount(), buyRo.getPrice(),
                BigDecimalUtil.toBigDecimal(priceConfigVo.getCodeValue())
        );
        BigDecimal useMoney = tradeMoney.getUseMoney();
        if(useMoney.compareTo(buyMoney)<0){
            log.error("查询用户{}资产信息不足",buyRo.getUserId());
            return OutputResult.buildAlert(ResultCode.TRADE_MONEY_LESS);
        }
        log.info("买入股票时用户{}的资产信息验证通过", buyRo.getUserId());
        //可以买入了
        //计算出，可用的与可取的之间的差值信息.
        BigDecimal takeoutMoney = tradeMoney.getTakeoutMoney();
        //获取之间的差值。
        BigDecimal subMoney = BigDecimalUtil.subBigDecimal(useMoney, takeoutMoney);
        BigDecimal useMoneyChange;
        BigDecimal takeoutMoneyChange = SystemConst.DEFAULT_EMPTY;
        tradeMoney.setUseMoney(
                BigDecimalUtil.subBigDecimal(
                        useMoney,buyMoney
                )
        );
        //目前手里可用的就够了, 可用的减少,可取的，不变
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
                tradeMoney
        );
        log.info("买入股票时用户{}修改金额成功", buyRo.getUserId());
        // 生成一个买入的委托单
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
        tradeEntrustDo.setEntrustType(buyRo.getEntrustType());
        tradeEntrustDo.setMockType(buyRo.getMockType());
        tradeEntrustDo.setFlag(DataFlagType.NORMAL.getCode());
        //放入一条记录到委托信息里面.
        log.info("买入股票时用户{}生成委托信息{}", buyRo.getUserId(), tradeEntrustDo);
        tradeEntrustDomainService.save(tradeEntrustDo);
        return OutputResult.buildSucc();
    }

    @Override
    public OutputResult realBuy(BuyRo buyRo) {
        SubmitRequest request = new SubmitRequest();
        Stock stock = stockCacheService.selectByCode(buyRo.getCode());
        if (stock == null) {
            return OutputResult.buildAlert(ResultCode.STOCK_CODE_NO_EXIST);
        }
        request.setUserId(buyRo.getUserId());
        request.setAmount(buyRo.getAmount());
        request.setPrice(buyRo.getPrice().doubleValue());
        request.setStockCode(buyRo.getCode());
        request.setZqmc(stock.getName());
        request.setTradeType(SubmitRequest.B);
        request.setMarket(StockUtil.getStockMarket(request.getStockCode()));
        TradeResultVo<SubmitResponse> response = tradeRequestHelper.sendRealBuyReq(request);
        if (!response.getSuccess()) {
            return OutputResult.buildFail(ResultCode.REAL_BUY_ERROR);
        }
        return OutputResult.buildSucc(response.getData().get(0).getWtbh());
    }

    /**
     * 获取买入，总共需要的手续费
     *
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
}
