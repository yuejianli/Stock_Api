package top.yueshushu.learn.enumtype;

/**
 * 展示的价格类型
 * @author 两个蝴蝶飞
 */
public enum CharPriceType {
    /**
     * 开盘价
     */
    OPENINGPRICE(1,"开盘价"),
    /**
     * 收盘价
     */
    CLOSINGPRICE(2,"收盘价"),
    /**
     * 最高价
     */
    HIGHESTPRICE(3,"最高价"),
    /**
     * 最低价
     */
    LOWESTPRICE(4,"最低价"),
    /**
     * 涨幅度
     */
    AMPLITUDEPROPORTION(5,"涨幅度"),
    /**
     * 涨幅金额
     */
    AMPLITUDE(6,"涨幅金额");

    private Integer code;

    private String desc;

    private CharPriceType(Integer code, String desc){
        this.code=code;
        this.desc=desc;
    }


    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
