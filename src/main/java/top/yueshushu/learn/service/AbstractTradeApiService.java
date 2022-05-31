package top.yueshushu.learn.service;

import com.alibaba.fastjson.TypeReference;
import top.yueshushu.learn.api.TradeResultVo;
import top.yueshushu.learn.api.request.*;
import top.yueshushu.learn.api.response.*;

public abstract class AbstractTradeApiService implements TradeApiService {

    @Override
    public TradeResultVo<GetAssetsResponse> getAsserts(GetAssetsRequest request) {
        return send(request, new TypeReference<GetAssetsResponse>() {});
    }

    @Override
    public TradeResultVo<SubmitResponse> submit(SubmitRequest request) {
        return send(request, new TypeReference<SubmitResponse>() {});
    }

    @Override
    public TradeResultVo<RevokeResponse> revoke(RevokeRequest request) {
        return send(request, new TypeReference<RevokeResponse>() {});
    }

    @Override
    public TradeResultVo<GetStockListResponse> getStockList(GetStockListRequest request) {
        return send(request, new TypeReference<GetStockListResponse>() {});
    }

    @Override
    public TradeResultVo<GetOrdersDataResponse> getOrdersData(GetOrdersDataRequest request) {
        return send(request, new TypeReference<GetOrdersDataResponse>() {});
    }

    @Override
    public TradeResultVo<GetDealDataResponse> getDealData(GetDealDataRequest request) {
        return send(request, new TypeReference<GetDealDataResponse>() {});
    }

    @Override
    public TradeResultVo<GetHisDealDataResponse> getHisDealData(GetHisDealDataRequest request) {
        return send(request, new TypeReference<GetHisDealDataResponse>() {});
    }

    @Override
    public TradeResultVo<GetHisOrdersDataResponse> getHisOrdersData(GetHisOrdersDataRequest request) {
        return send(request, new TypeReference<GetHisOrdersDataResponse>() {});
    }

    @Override
    public TradeResultVo<GetCanBuyNewStockListV3Response> getCanBuyNewStockListV3(GetCanBuyNewStockListV3Request request) {
        return send(request, new TypeReference<GetCanBuyNewStockListV3Response>() {});
    }

    @Override
    public TradeResultVo<GetConvertibleBondListV2Response> getConvertibleBondListV2(GetConvertibleBondListV2Request request) {
        return send(request, new TypeReference<GetConvertibleBondListV2Response>() {});
    }

    @Override
    public TradeResultVo<SubmitBatTradeV2Response> submitBatTradeV2(SubmitBatTradeV2Request request) {
        return sendList(request, new TypeReference<SubmitBatTradeV2Response>() {});
    }

    public abstract <T> TradeResultVo<T> send(BaseTradeRequest request, TypeReference<T> responseType);

    public abstract <T> TradeResultVo<T> sendList(BaseTradeListRequest request, TypeReference<T> responseType);

}
