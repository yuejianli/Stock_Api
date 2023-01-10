package top.yueshushu.learn.enumtype;

import org.springframework.util.Assert;

/**
 * 交易所的类型
 *
 * @author 两个蝴蝶飞
 */
public enum ExchangeMarketType {
    /**
     * 上海
     */
    SH("sh", "HA"),
    /**
     * 深圳
     */
    SZ("sz", "SA"),
    /**
     * 北京
     */
    BJ("bj", "BA"),
    /**
     * 其它的非上述地区
     */
    OTHER("other", "other");

    private String code;

    private String desc;

    private ExchangeMarketType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 获取对应的交易所的类型
     *
     * @param code
     * @return
     */
    public static ExchangeMarketType getExchangeType(String code) {
        Assert.notNull(code, "code编号不能为空");
        for (ExchangeMarketType exchangeType : ExchangeMarketType.values()) {
            if (exchangeType.code.equals(code)) {
                return exchangeType;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
