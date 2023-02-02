package top.yueshushu.learn.strategy.bs.model;

import lombok.Data;
import top.yueshushu.learn.entity.TradeRule;

/**
 * 股票交易规则 Dto
 *
 * @author yuejianli
 * @date 2023-01-30
 */
@Data
public class TradeStockRuleDto extends TradeRule {
    private String stockCode;
    private String stockName;
}
