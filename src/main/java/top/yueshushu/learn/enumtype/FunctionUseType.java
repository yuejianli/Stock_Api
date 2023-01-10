package top.yueshushu.learn.enumtype;

/**
 * 功能是否使用
 *
 * @author 两个蝴蝶飞
 */
public enum FunctionUseType {
    /**
     * 正常
     */
    USE("1", "不启用"),
    /**
     * 删除
     */
    DISABLE("0", "启用");

    private String code;

    private String desc;

    private FunctionUseType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
