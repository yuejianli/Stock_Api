package top.yueshushu.learn.enumtype;

/**
 * 交易地的类型
 * @author 两个蝴蝶飞
 */
public enum DataFlagType {
    /**
     * 深圳
     */
    NORMAL(1,"正常"),
    /**
     * 上海
     */
    DELETE(0,"删除");

    private Integer code;

    private String desc;

    private DataFlagType(Integer code, String desc){
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
