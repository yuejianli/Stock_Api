package top.yueshushu.learn.enumtype;

/**
 * 类型， 1为金额 2为比例
 * @author 两个蝴蝶飞
 */
public enum RuleValueType {
    /**
     * <
     */
    MONEY(1,"金额"),
    /**
     * >
     */
    PROPORTION(2,"比例");

    private Integer code;

    private String desc;

    private RuleValueType(Integer code, String desc){
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
