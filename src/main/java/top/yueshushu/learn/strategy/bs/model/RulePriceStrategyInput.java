package top.yueshushu.learn.strategy.bs.model;


import lombok.Data;

import java.io.Serializable;

@Data
public class RulePriceStrategyInput extends BaseStrategyInput implements Serializable {
    private TradeStockRuleDto tradeStockRuleDto;
}
