package top.yueshushu.learn.enumtype;

/**
 * 展示的版块查询维护
 *
 * @author 两个蝴蝶飞
 */
public enum StockCharMoneyType {
    /**
     * 开盘价
     */
    OPENING_PRICE(1, "开盘价"),
    /**
     * 收盘价
     */
    CLOSING_PRICE(2, "收盘价"),
    /**
     * 最高价
     */
    HIGHEST_PRICE(3, "最高价"),
    /**
     * 最低价
     */
    LOWEST_PRICE(4, "最低价"),
    /**
     * 涨幅度比例
     */
    AMPLITUDE_PROPORTION(5, "涨幅度比例"),

    /**
     * 成交量
     */
    TRADING_VOLUME(6, "成交量"),
    /**
     * 换手率
     */
    CHANGING_PROPORTION(7, "换手率"),

    /**
     * 量比
     */
    THAN(8, "量比"),
    /**
     * 平均价格
     */
    AVG_PRICE(9, "平均价格"),
    ;


    private Integer code;

    private String desc;

    private StockCharMoneyType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
