package top.yueshushu.learn.enumtype;

/**
 * 展示的版块查询维护
 *
 * @author 两个蝴蝶飞
 */
public enum BKCharMoneyType {
    /**
     * 最新价
     */
    BK_NOW_PRICE(1, "最新价"),
    /**
     * 版块涨跌比例
     */
    BK_NOW_PROPORTION(2, "版块涨跌比例"),
    /**
     * 今日主力净注入净额
     */
    TODAY_MAIN_INFLOW(3, "今日主力净注入净额"),
    /**
     * 今日主力净注入净额 占比
     */
    TODAY_MAIN_INFLOW_PROPORTION(4, "今日主力净注入净额 占比"),
    /**
     * 超大净注入净额
     */
    TODAY_SUPER_INFLOW(5, "超大净注入净额"),
    /**
     * 超大净注入净额 占比
     */
    TODAY_SUPER_INFLOW_PROPORTION(6, "超大净注入净额 占比"),

    /**
     * 大单净注入净额
     */
    TODAY_MORE_INFLOW(7, "大单净注入净额"),
    /**
     * 大单净注入净额 占比
     */
    TODAY_MORE_INFLOW_PROPORTION(8, "大单净注入净额 占比"),

    /**
     * 中单净注入净额
     */
    TODAY_MIDDLE_INFLOW(9, "中单净注入净额"),
    /**
     * 中单净注入净额 占比
     */
    TODAY_MIDDLE_INFLOW_PROPORTION(10, "中单净注入净额 占比"),

    /**
     * 小单净注入净额
     */
    TODAY_SMALL_INFLOW(11, "小单净注入净额"),
    /**
     * 小单净注入净额 占比
     */
    TODAY_SMALL_INFLOW_PROPORTION(12, "小单净注入净额 占比"),


    ;


    private Integer code;

    private String desc;

    private BKCharMoneyType(Integer code, String desc) {
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
