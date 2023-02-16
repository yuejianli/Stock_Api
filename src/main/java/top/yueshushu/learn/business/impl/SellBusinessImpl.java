package top.yueshushu.learn.business.impl;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.yueshushu.learn.api.TradeResultVo;
import top.yueshushu.learn.api.request.SubmitRequest;
import top.yueshushu.learn.api.response.SubmitResponse;
import top.yueshushu.learn.business.SellBusiness;
import top.yueshushu.learn.common.ResultCode;
import top.yueshushu.learn.common.SystemConst;
import top.yueshushu.learn.domain.TradeEntrustDo;
import top.yueshushu.learn.domainservice.TradeEntrustDomainService;
import top.yueshushu.learn.entity.Stock;
import top.yueshushu.learn.entity.TradePosition;
import top.yueshushu.learn.enumtype.ConfigCodeType;
import top.yueshushu.learn.enumtype.DataFlagType;
import top.yueshushu.learn.enumtype.DealType;
import top.yueshushu.learn.enumtype.EntrustStatusType;
import top.yueshushu.learn.helper.TradeRequestHelper;
import top.yueshushu.learn.mode.ro.SellRo;
import top.yueshushu.learn.mode.vo.ConfigVo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.service.ConfigService;
import top.yueshushu.learn.service.TradePositionService;
import top.yueshushu.learn.service.cache.StockCacheService;
import top.yueshushu.learn.util.BigDecimalUtil;
import top.yueshushu.learn.util.StockUtil;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * 委托卖出
 *
 * @author Yue Jianli
 * @date 2022-05-26
 */
@Service("sellBusiness")
@Slf4j(topic = "sell")

public class SellBusinessImpl implements SellBusiness {
    @Resource
    private TradePositionService tradePositionService;
    @Resource
    private ConfigService configService;
    @Resource
    private StockCacheService stockCacheService;
    @Resource
    private TradeEntrustDomainService tradeEntrustDomainService;
    @Resource
    private TradeRequestHelper tradeRequestHelper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OutputResult sell(SellRo sellRo) {
        log.info(">>>试图卖出股票 {},股票信息是:{}", sellRo.getCode(), sellRo);
        Stock stock = stockCacheService.selectByCode(sellRo.getCode());
        if (stock == null) {
            return OutputResult.buildAlert(ResultCode.STOCK_CODE_NO_EXIST);
        }
        log.info("卖出股票时用户{}的股票编码验证通过", sellRo.getUserId());
        //获取当前该股票的持仓数和可用数.
        TradePosition tradePosition = tradePositionService.getPositionByCode(
                sellRo.getUserId(), sellRo.getMockType(), sellRo.getCode());
        if(tradePosition ==null){
            return OutputResult.buildAlert(ResultCode.TRADE_POSITION_NO);
        }
        log.info("卖出股票时用户{}的资产验证通过", sellRo.getUserId());
        if(tradePosition.getUseAmount()<sellRo.getAmount()){
            return OutputResult.buildAlert(ResultCode.TRADE_POSITION_NUM_SUPPORT);
        }
        log.info("卖出股票时用户{}的可用数量验证通过", sellRo.getUserId());
        tradePosition.setUseAmount(
                tradePosition.getUseAmount()
                        - sellRo.getAmount()
        );
        //更新
        tradePositionService.updateById(tradePosition);
        log.info("卖出股票时用户{}修改可用数量成功", sellRo.getUserId());
        //获取对应的交易手续费
        ConfigVo priceConfigVo = configService.getConfigByCode(
                sellRo.getUserId(), ConfigCodeType.TRAN_PRICE.getCode()
        );
        TradeEntrustDo tradeEntrustDo = new TradeEntrustDo();
        tradeEntrustDo.setCode(sellRo.getCode());
        tradeEntrustDo.setName(sellRo.getName());
        tradeEntrustDo.setEntrustDate(DateUtil.date());
        tradeEntrustDo.setDealType(DealType.SELL.getCode());
        tradeEntrustDo.setEntrustNum(sellRo.getAmount());
        tradeEntrustDo.setEntrustPrice(BigDecimalUtil.convertFour(sellRo.getPrice()));
        tradeEntrustDo.setEntrustStatus(EntrustStatusType.ING.getCode());
        tradeEntrustDo.setEntrustCode(StockUtil.generateEntrustCode());
        tradeEntrustDo.setUseMoney(SystemConst.DEFAULT_EMPTY);
        tradeEntrustDo.setTakeoutMoney(SystemConst.DEFAULT_EMPTY);

        tradeEntrustDo.setEntrustMoney(
                StockUtil.allMoney(
                        sellRo.getAmount(),
                        sellRo.getPrice()
                )
        );
        tradeEntrustDo.setHandMoney(
                StockUtil.getSellHandMoney(
                        sellRo.getAmount(),
                        sellRo.getPrice(),
                        BigDecimalUtil.toBigDecimal(priceConfigVo.getCodeValue())
                )
        );
        tradeEntrustDo.setTotalMoney(
                StockUtil.getSellMoney(
                        sellRo.getAmount(),
                        sellRo.getPrice(),
                        BigDecimalUtil.toBigDecimal(priceConfigVo.getCodeValue())
                )
        );
        tradeEntrustDo.setUserId(sellRo.getUserId());
        tradeEntrustDo.setEntrustType(sellRo.getEntrustType());
        tradeEntrustDo.setDbType(Optional.ofNullable(sellRo.getDbBuy()).orElse(0));
        tradeEntrustDo.setMockType(sellRo.getMockType());
        tradeEntrustDo.setFlag(DataFlagType.NORMAL.getCode());
        //放入一条记录到委托信息里面.
        log.info("卖出股票时用户{}生成委托信息{}", sellRo.getUserId(), tradeEntrustDo);
        tradeEntrustDomainService.save(tradeEntrustDo);
        return OutputResult.buildSucc();
    }

    @Override
    public OutputResult realSell(SellRo sellRo) {
        SubmitRequest request = new SubmitRequest();
        request.setUserId(sellRo.getUserId());
        request.setAmount(sellRo.getAmount());
        request.setPrice(sellRo.getPrice().doubleValue());
        request.setStockCode(sellRo.getCode());
        request.setZqmc(sellRo.getName());
        request.setTradeType(SubmitRequest.S);
        request.setMarket(StockUtil.getStockMarket(request.getStockCode()));
        TradeResultVo<SubmitResponse> response = tradeRequestHelper.sendRealSellReq(request);
        if (!response.getSuccess()) {
            return OutputResult.buildFail(ResultCode.REAL_SELL_ERROR);
        }
        return OutputResult.buildSucc(response.getData().get(0).getWtbh());
    }
}
