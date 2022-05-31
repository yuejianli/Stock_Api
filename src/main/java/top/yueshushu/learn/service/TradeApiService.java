package top.yueshushu.learn.service;


import top.yueshushu.learn.api.TradeResultVo;
import top.yueshushu.learn.api.request.*;
import top.yueshushu.learn.api.response.*;

public interface TradeApiService {

    /**
     * 我的资产
     */
    TradeResultVo<GetAssetsResponse> getAsserts(GetAssetsRequest request);

    /**
     * 挂单
     */
    TradeResultVo<SubmitResponse> submit(SubmitRequest request);

    /**
     * 撤单
     */
    TradeResultVo<RevokeResponse> revoke(RevokeRequest request);

    /**
     * 我的持仓
     */
    TradeResultVo<GetStockListResponse> getStockList(GetStockListRequest request);

    /**
     * 当日委托
     */
    TradeResultVo<GetOrdersDataResponse> getOrdersData(GetOrdersDataRequest request);

    /**
     * 当日成交
     */
    TradeResultVo<GetDealDataResponse> getDealData(GetDealDataRequest request);

    /**
     * 登录
     */
    TradeResultVo<AuthenticationResponse> authentication(AuthenticationRequest request);

    /**
     * 历史成交
     */
    TradeResultVo<GetHisDealDataResponse> getHisDealData(GetHisDealDataRequest request);

    /**
     * 历史委托
     */
    TradeResultVo<GetHisOrdersDataResponse> getHisOrdersData(GetHisOrdersDataRequest request);

    /**
     * 查询可申购新股列表
     */
    TradeResultVo<GetCanBuyNewStockListV3Response> getCanBuyNewStockListV3(GetCanBuyNewStockListV3Request request);

    /**
     * 查询可申购新股列表
     */
    TradeResultVo<GetConvertibleBondListV2Response> getConvertibleBondListV2(GetConvertibleBondListV2Request request);

    /**
     * 批量申购
     */
    TradeResultVo<SubmitBatTradeV2Response> submitBatTradeV2(SubmitBatTradeV2Request request);

}
