package top.yueshushu.learn.enumtype;

import org.springframework.util.Assert;

/**
 * 交易方法类型
 * @author 两个蝴蝶飞
 */
public enum TradeMethodType {
    GetAssertsRequest("get_asserts","我的资产"),
    SubmitRequest("submit","提交挂单"),
    RevokeRequest("revoke","撤单"),
    GetStockList("get_stock_list","我的持仓"),
    GetOrdersDataRequest("get_orders_data","当日委托"),
    GetDealDataRequest("get_deal_data","当日成交"),
    AuthenticationRequest("authentication","登录"),
    AuthenticationCheckRequest("authentication_check","登录验证"),
    GetHisDealDataRequest("get_his_deal_data","历史成交"),
    GetHisOrdersDataRequest("get_his_orders_data","历史委托"),
    GetCanBuyNewStockListV3("get_can_buy_new_stock_list_v3","查询可申购新股列表"),
    GetConvertibleBondListV2("get_convertible_bond_list_v2","查询可申购新债列表"),
    SubmitBatTradeV2("submit_bat_trade_v2","批量申购"),
    yzm("yzm","登录验证码");

    private String code;

    private String desc;

    private TradeMethodType(String code, String desc){
        this.code=code;
        this.desc=desc;
    }

    /**
     * 获取交易的方法
     * @param code
     * @return
     */
    public static TradeMethodType getExchangeType(String code){
        Assert.notNull(code,"code编号不能为空");
        for(TradeMethodType exchangeType: TradeMethodType.values()){
            if(exchangeType.code.equalsIgnoreCase(code)){
                return exchangeType;
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
