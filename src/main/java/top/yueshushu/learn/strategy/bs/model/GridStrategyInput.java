package top.yueshushu.learn.strategy.bs.model;


import lombok.Data;
import top.yueshushu.learn.mode.vo.TradeDealVo;
import top.yueshushu.learn.mode.vo.TradeEntrustVo;

import java.io.Serializable;
import java.util.List;

@Data
public class GridStrategyInput extends BaseStrategyInput implements Serializable {

    public GridStrategyInput(TradeStockRuleDto tradeRuleVo) {
        super(tradeRuleVo);
    }

    private List<TradeDealVo> dealDataList;

    private List<TradeEntrustVo> tradeOrderList;


}
