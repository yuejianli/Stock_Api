package top.yueshushu.learn.business.impl;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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
import top.yueshushu.learn.mode.ro.SellRo;
import top.yueshushu.learn.mode.vo.ConfigVo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.service.ConfigService;
import top.yueshushu.learn.service.StockService;
import top.yueshushu.learn.service.TradeEntrustService;
import top.yueshushu.learn.service.TradePositionService;
import top.yueshushu.learn.util.BigDecimalUtil;
import top.yueshushu.learn.util.StockUtil;

import javax.annotation.Resource;

/**
 * 委托卖出
 *
 * @author Yue Jianli
 * @date 2022-05-26
 */
@Service
@Slf4j
public class SellBusinessImpl implements SellBusiness {

    @Resource
    private TradeEntrustService tradeEntrustService;
    @Resource
    private TradePositionService tradePositionService;
    @Resource
    private ConfigService configService;
    @Resource
    private StockService stockService;
    @Resource
    private TradeEntrustDomainService tradeEntrustDomainService;
    @Override
    public OutputResult sell(SellRo sellRo) {
        Stock stock = stockService.selectByCode(sellRo.getCode());
        if (stock ==null){
            return OutputResult.buildAlert(ResultCode.STOCK_CODE_NO_EXIST);
        }

        //获取当前该股票的持仓数和可用数.
        TradePosition tradePosition = tradePositionService.getPositionByCode(
                sellRo.getUserId(), sellRo.getMockType(), sellRo.getCode());
        if(tradePosition ==null){
            return OutputResult.buildAlert(ResultCode.TRADE_POSITION_NO);
        }
        if(tradePosition.getUseAmount()<sellRo.getAmount()){
            return OutputResult.buildAlert(ResultCode.TRADE_POSITION_NUM_SUPPORT);
        }
        tradePosition.setUseAmount(
                tradePosition.getUseAmount()
                        - sellRo.getAmount()
        );
        //更新
        tradePositionService.updateById(tradePosition);
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
        tradeEntrustDo.setMockType(sellRo.getMockType());
        tradeEntrustDo.setFlag(DataFlagType.NORMAL.getCode());
        //放入一条记录到委托信息里面.
        tradeEntrustDomainService.save(tradeEntrustDo);
        return OutputResult.buildSucc();
    }
}
