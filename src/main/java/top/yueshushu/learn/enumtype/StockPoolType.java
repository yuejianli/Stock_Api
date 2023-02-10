package top.yueshushu.learn.enumtype;

import org.springframework.util.Assert;

/**
 * 股票池类型
 *
 * @author 两个蝴蝶飞
 */
public enum StockPoolType {
    ZT(1, "涨停"),
    DT(2, "跌停"),
    YES_ZT(3, "昨日涨停"),
    QS(4, "强势"),
    CX(5, "次新"),
    ZB(6, "炸板"),

    ;

    private Integer code;

    private String desc;

    private StockPoolType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 获取交易的方法
     *
     * @param code
     * @return
     */
    public static StockPoolType getPoolType(Integer code) {
        Assert.notNull(code, "code编号不能为空");
        for (StockPoolType configCodeType : StockPoolType.values()) {
            if (configCodeType.code.equals(code)) {
                return configCodeType;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
