package top.yueshushu.learn.enumtype;

import org.springframework.util.Assert;

/**
 * 虚拟盘的类型
 *
 * @author 两个蝴蝶飞
 */
public enum BigDealKindType {
    D(1, "卖盘"),
    U(2, "买盘"),
    E(3, "中性盘");

    private Integer code;

    private String desc;

    BigDealKindType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 获取交易的方法
     *
     * @param code
     * @return
     */
    public static BigDealKindType getMockType(Integer code) {
        Assert.notNull(code, "code编号不能为空");
        for (BigDealKindType configCodeType : BigDealKindType.values()) {
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
