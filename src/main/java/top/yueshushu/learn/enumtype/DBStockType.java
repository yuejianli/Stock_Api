package top.yueshushu.learn.enumtype;

import org.springframework.util.Assert;

/**
 * 打版股票的类型
 *
 * @author 两个蝴蝶飞
 */
public enum DBStockType {
    SH(1, "上海"),
    SZ(2, "深圳"),
    CY(3, "创业板"),
    BJ(4, "北京板"),
    SH_SZ(5, "上海和深圳"),
    SH_SZ_CY(6, "上海和深圳和创业"),
    SH_SZ_CY_BJ(7, "上海和深圳和创业和北京"),
    ALL(8, "所有");

    private Integer code;

    private String desc;

    private DBStockType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 获取交易的方法
     *
     * @param code
     * @return
     */
    public static DBStockType getStockType(Integer code) {
        Assert.notNull(code, "code编号不能为空");
        for (DBStockType configCodeType : DBStockType.values()) {
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

    public boolean contains(StockCodeType typeByStockCode) {
        boolean flag = false;
        switch (this) {
            case ALL: {
                flag = true;
                break;
            }
            case SH: {
                if (SH.code.equals(typeByStockCode.getCode())) {
                    flag = true;
                }
                break;
            }
            case SZ: {
                if (SZ.code.equals(typeByStockCode.getCode())) {
                    flag = true;
                }
                break;
            }
            case CY: {
                if (CY.code.equals(typeByStockCode.getCode())) {
                    flag = true;
                }
                break;
            }
            case BJ: {
                if (BJ.code.equals(typeByStockCode.getCode())) {
                    flag = true;
                }
                break;
            }
            case SH_SZ: {
                if (SH.code.equals(typeByStockCode.getCode()) || SZ.code.equals(typeByStockCode.getCode())) {
                    flag = true;
                }
                break;
            }
            case SH_SZ_CY: {
                if (SH.code.equals(typeByStockCode.getCode()) || SZ.code.equals(typeByStockCode.getCode())
                        || CY.code.equals(typeByStockCode.getCode())) {
                    flag = true;
                }
                break;
            }
            case SH_SZ_CY_BJ: {
                if (SH.code.equals(typeByStockCode.getCode()) || SZ.code.equals(typeByStockCode.getCode())
                        || CY.code.equals(typeByStockCode.getCode())
                        || BJ.code.equals(typeByStockCode.getCode())) {
                    flag = true;
                }
                break;
            }
            default: {
                break;
            }
        }
        return flag;
    }
}
