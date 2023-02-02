package top.yueshushu.learn.strategy.bs.baseimpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import top.yueshushu.learn.service.cache.StockCacheService;
import top.yueshushu.learn.strategy.bs.base.BaseStrategyHandler;
import top.yueshushu.learn.strategy.bs.model.RulePriceStrategyInput;
import top.yueshushu.learn.strategy.bs.model.RulePriceStrategyOutput;
import top.yueshushu.learn.strategy.bs.model.TradeStockRuleDto;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Component("yesOpeningPriceBuyHandler")
@Slf4j
public class YesOpeningPriceBuyStrategyHandler
        extends BaseStrategyHandler<RulePriceStrategyInput, RulePriceStrategyOutput> {
    @Resource
    private StockCacheService stockCacheService;

    @Override
    public BigDecimal getOriginPrice(TradeStockRuleDto tradeStockRuleDto) {
        BigDecimal todayBuyCachePrice = stockCacheService.getTodayBuyCachePrice(tradeStockRuleDto.getUserId(), tradeStockRuleDto.getMockType(),
                tradeStockRuleDto.getStockCode());
        if (!ObjectUtils.isEmpty(todayBuyCachePrice)) {
            return todayBuyCachePrice;
        }
        return stockCacheService.getYesterdayOpenCachePrice(tradeStockRuleDto.getStockCode());
    }
}
