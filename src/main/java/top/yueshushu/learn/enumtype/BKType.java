package top.yueshushu.learn.enumtype;

import org.springframework.util.Assert;

/**
 * 交易的类型
 *
 * @author 两个蝴蝶飞
 */
public enum BKType {
    BK(1, "版块"),
    GN(2, "概念"),
    DY(3, "地域");

    private Integer code;

    private String desc;

    private BKType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 获取交易的方法
     *
     * @param code
     * @return
     */
    public static BKType getType(Integer code) {
        Assert.notNull(code, "code编号不能为空");
        for (BKType configCodeType : BKType.values()) {
            if (configCodeType.code.equals(code)) {
                return configCodeType;
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
