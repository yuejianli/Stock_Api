package top.yueshushu.learn.strategy.bs.baseimpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.yueshushu.learn.service.cache.StockCacheService;
import top.yueshushu.learn.strategy.bs.base.BaseStrategyHandler;
import top.yueshushu.learn.strategy.bs.model.RulePriceStrategyInput;
import top.yueshushu.learn.strategy.bs.model.RulePriceStrategyOutput;
import top.yueshushu.learn.strategy.bs.model.TradeStockRuleDto;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Component("lastSellPriceSellHandler")
@Slf4j
public class LastSellPriceSellStrategyHandler
        extends BaseStrategyHandler<RulePriceStrategyInput, RulePriceStrategyOutput> {
    @Resource
    private StockCacheService stockCacheService;

    @Override
    public BigDecimal getOriginPrice(TradeStockRuleDto tradeStockRuleDto) {
        return stockCacheService.getLastSellCachePrice(tradeStockRuleDto.getUserId(), tradeStockRuleDto.getMockType(),
                tradeStockRuleDto.getStockCode());
    }
}
