package top.yueshushu.learn.enumtype;

/**
 * 条件类型  小于，大于
 * @author 两个蝴蝶飞
 */
public enum ConditionType {
    /**
     * <
     */
    LT(1,"小于"),
    /**
     * >
     */
    GT(2,"大于");

    private Integer code;

    private String desc;

    private ConditionType(Integer code, String desc){
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
