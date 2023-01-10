package top.yueshushu.learn.enumtype;

import org.springframework.util.Assert;

/**
 * 配置参数的类型
 *
 * @author 两个蝴蝶飞
 */
public enum ConfigCodeType {
    MOCK("mock", "0为真实1为虚拟盘"),
    TRAN_PRICE("tranPrice", "券商的交易手续费"),
    MOCK_BUY_SUB_PRICE("buySubPrice", "买入差价"),
    MOCK_SELL_SUB_PRICE("sellSubPrice", "卖出差价"),
    SELECT_MAX_NUM("selectMaxNum", "最大的自选数量"),
    BUY_NEW_STOCK("buyNewStock", "申购新股"),
    AUTO_LOGIN("autoLogin", "自动登录"),
    ;

    private String code;

    private String desc;

    private ConfigCodeType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 获取交易的方法
     *
     * @param code
     * @return
     */
    public static ConfigCodeType getExchangeType(String code) {
        Assert.notNull(code, "code编号不能为空");
        for (ConfigCodeType configCodeType : ConfigCodeType.values()) {
            if(configCodeType.code.equalsIgnoreCase(code)){
                return configCodeType;
            }
        }
        return null;
    }
    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
