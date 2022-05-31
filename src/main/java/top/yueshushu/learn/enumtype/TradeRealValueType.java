package top.yueshushu.learn.enumtype;

import lombok.Getter;

/**
 * 交易真实值类型
 * @author 两个蝴蝶飞
 */
@Getter
public enum TradeRealValueType {
    TRADE_POSITION("position","持仓信息"),
    TRADE_MONEY("money","持仓金额信息"),
    TRADE_ENTRUST("entrust","委托单信息"),
    TRADE_DEAL("deal","成交信息");

    private String code;

    private String desc;

    TradeRealValueType(String code, String desc){
        this.code=code;
        this.desc=desc;
    }
}
