package top.yueshushu.learn.enumtype;

/**
 * 数据信息
 *
 * @author 两个蝴蝶飞
 */
public enum DataFlagType {
    /**
     * 正常
     */
    NORMAL(1,"正常"),
    /**
     * 删除
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
