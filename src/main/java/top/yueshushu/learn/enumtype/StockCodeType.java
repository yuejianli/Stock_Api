package top.yueshushu.learn.enumtype;

import org.springframework.util.Assert;

/**
 * 打版股票的类型
 *
 * @author 两个蝴蝶飞
 */
public enum StockCodeType {
    SH(1, "上海"),
    SZ(2, "深圳"),
    CY(3, "创业板"),
    BJ(4, "北京板"),
    ;

    private Integer code;

    private String desc;

    private StockCodeType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 获取交易的方法
     *
     * @param code
     * @return
     */
    public static StockCodeType getTypeByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (StockCodeType configCodeType : StockCodeType.values()) {
            if (configCodeType.code.equals(code)) {
                return configCodeType;
            }
        }
        return null;
    }

    /**
     * 获取交易的方法
     *
     * @param code
     * @return
     */
    public static StockCodeType getTypeByStockCode(String code) {
        Assert.notNull(code, "code编号不能为空");
        // 如果以 60 开头
        if (code.startsWith("6")) {
            return StockCodeType.SH;
        } else if (code.startsWith("0")) {
            return StockCodeType.SZ;
        } else if (code.startsWith("3")) {
            return StockCodeType.CY;
        } else if (code.startsWith("83")) {
            return StockCodeType.BJ;
        } else {
            return null;
        }
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
