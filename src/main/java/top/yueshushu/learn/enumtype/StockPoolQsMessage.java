package top.yueshushu.learn.enumtype;

import org.springframework.util.Assert;

/**
 * 股票池类型
 *
 * @author 两个蝴蝶飞
 */
public enum StockPoolQsMessage {
    XG(1, "60日新高"),
    XGAndZT(3, "60日新高且近期多次涨停"),

    ;

    private Integer code;

    private String desc;

    private StockPoolQsMessage(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 获取交易的方法
     *
     * @param code
     * @return
     */
    public static StockPoolQsMessage getPoolQsType(Integer code) {
        Assert.notNull(code, "code编号不能为空");
        for (StockPoolQsMessage configCodeType : StockPoolQsMessage.values()) {
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
