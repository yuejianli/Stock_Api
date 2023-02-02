package top.yueshushu.learn.enumtype;

/**
 * 虚拟盘的类型
 *
 * @author 两个蝴蝶飞
 */
public enum RuleConditionType {
    // 收盘策略
    YES_CLOSING_PRICE_BUY("yes_closing_price_1", "yesClosingPriceBuyHandler"),
    YES_CLOSING_PRICE_SELL("yes_closing_price_2", "yesClosingPriceSellHandler"),

    // 开盘策略
    YES_OPENING_PRICE_BUY("yes_opening_price_1", "yesOpeningPriceBuyHandler"),
    YES_OPENING_PRICE_SELL("yes_opening_price_2", "yesOpeningPriceSellHandler"),


    // 高点策略
    YES_HIGH_PRICE_BUY("yes_highest_price_1", "yesHighPriceBuyHandler"),
    YES_HIGH_PRICE_SELL("yes_highest_price_2", "yesHighPriceSellHandler"),

    // 低点策略

    YES_LOWEST_PRICE_BUY("yes_lowest_price_1", "yesLowestPriceBuyHandler"),
    YES_LOWEST_PRICE_SELL("yes_lowest_price_2", "yesLowestPriceSellHandler"),

    // 最后成交买入 卖出价

    LAST_BUY_PRICE_BUY("last_buy_price_1", "lastBuyPriceBuyHandler"),
    LAST_BUY_PRICE_SELL("last_buy_price_2", "lastBuyPriceSellHandler"),

    LAST_SELL_PRICE_BUY("last_sell_price_1", "lastSellPriceBuyHandler"),
    LAST_SELL_PRICE_SELL("last_sell_price_2", "lastSellPriceSellHandler"),


    ;

    private String conditionCode;

    private String beanName;

    private RuleConditionType(String conditionCode, String beanName) {
        this.conditionCode = conditionCode;
        this.beanName = beanName;
    }

    /**
     * 通过条件，获取对应的 bean 名称
     *
     * @param code
     * @param ruleType
     * @return
     */
    public static RuleConditionType getByConditionCode(String code, Integer ruleType) {
        for (RuleConditionType configCodeType : RuleConditionType.values()) {
            if (configCodeType.conditionCode.equals(code + "_" + ruleType)) {
                return configCodeType;
            }
        }
        return null;
    }

    public String getConditionCode() {
        return conditionCode;
    }

    public String getBeanName() {
        return beanName;
    }
}
