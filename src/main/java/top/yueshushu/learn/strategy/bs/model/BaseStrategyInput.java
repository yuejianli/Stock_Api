package top.yueshushu.learn.strategy.bs.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseStrategyInput implements Serializable {

    private TradeStockRuleDto tradeStockRuleDto;

    public int getUserId() {
        return tradeStockRuleDto.getUserId();
    }

    public int getMockType() {
        return tradeStockRuleDto.getMockType();
    }

}
