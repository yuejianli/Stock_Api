package top.yueshushu.learn.strategy.bs.base;

import top.yueshushu.learn.strategy.bs.model.TradeStockRuleDto;

public interface StrategyHandler {
    /**
     * 处理股票交易
     *
     * @param tradeStockRuleDto 股票交易规则 Dto
     */
    void handle(TradeStockRuleDto tradeStockRuleDto);
}
