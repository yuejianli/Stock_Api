package top.yueshushu.learn.strategy.bs.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 策略关联的股票 Dto
 *
 * @author yuejianli
 * @date 2023-01-31
 */
@Data
public class StrategyRelationStockRuleDto implements Serializable {
    /**
     * 买入规则
     */
    private TradeStockRuleDto buyRule;
    /**
     * 卖出规则
     */
    private TradeStockRuleDto sellRule;
}
