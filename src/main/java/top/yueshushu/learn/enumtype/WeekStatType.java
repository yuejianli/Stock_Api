package top.yueshushu.learn.enumtype;

import org.springframework.util.Assert;

/**
 * 周统计展示信息
 * @author 两个蝴蝶飞
 */
public enum WeekStatType {
    /**
     * 近一周
     */
    ONE_WEEK(1,"近一周"),
    /**
     * 近一周
     */
    TWO_WEEK(2,"近两周"),
    /**
     * 北京
     */
    THREE_WEEK(3,"近三周"),
    /**
     * 其它的非上述地区
     */
    MONTH(4,"近一个月");

    private Integer code;

    private String desc;

    private WeekStatType(Integer code, String desc){
        this.code=code;
        this.desc=desc;
    }

    /**
     * 获取对应的交易所的类型
     * @param code
     * @return
     */
    public static WeekStatType getExchangeType(int code){
        Assert.notNull(code,"code编号不能为空");
        for(WeekStatType exchangeType: WeekStatType.values()){
            if(exchangeType.code.equals(code)){
                return exchangeType;
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
