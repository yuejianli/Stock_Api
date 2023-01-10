package top.yueshushu.learn.business.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.yueshushu.learn.api.TradeResultVo;
import top.yueshushu.learn.api.request.SubmitBatTradeV2Request;
import top.yueshushu.learn.api.request.SubmitRequest;
import top.yueshushu.learn.api.response.GetCanBuyNewStockListV3Response;
import top.yueshushu.learn.api.response.GetOrdersDataResponse;
import top.yueshushu.learn.business.BuyNewStockBusiness;
import top.yueshushu.learn.entity.User;
import top.yueshushu.learn.helper.TradeRequestHelper;
import top.yueshushu.learn.message.weixin.service.WeChatService;
import top.yueshushu.learn.service.UserService;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yuejianli
 * @date 2023-01-10
 */
@Service
@Slf4j
public class BuyNewStockBusinessImpl implements BuyNewStockBusiness {
    @Resource
    private TradeRequestHelper tradeRequestHelper;
    @Resource
    private WeChatService weChatService;
    @Resource
    private UserService userService;

    @Override
    public boolean applyBuyNewStock(Integer userId) {
        GetCanBuyNewStockListV3Response getCanBuyResponse = tradeRequestHelper.getCanBuyNewStockListV3(userId)
                .getData().get(0);

        List<SubmitBatTradeV2Request.SubmitData> newStockList = getCanBuyResponse.getNewStockList().stream()
                .filter(newStock -> getCanBuyResponse.getNewQuota().stream().anyMatch(v -> v.getMarket().equals(newStock.getMarket())))
                .map(newStock -> {
                    GetCanBuyNewStockListV3Response.NewQuotaInfo newQuotaInfo = getCanBuyResponse.getNewQuota().stream().filter(v -> v.getMarket().equals(newStock.getMarket())).findAny().orElse(null);
                    SubmitBatTradeV2Request.SubmitData submitData = new SubmitBatTradeV2Request.SubmitData();

                    submitData.setAmount(Integer.min(Integer.parseInt(newStock.getKsgsx()), Integer.parseInt(newQuotaInfo.getKsgsz())));
                    submitData.setMarket(newStock.getMarket());
                    submitData.setPrice(newStock.getFxj());
                    submitData.setStockCode(newStock.getSgdm());
                    submitData.setStockName(newStock.getZqmc());
                    submitData.setTradeType(SubmitRequest.B);
                    return submitData;
                }).collect(Collectors.toList());

        TradeResultVo<GetOrdersDataResponse> orderReponse = tradeRequestHelper.findRealEntrust(userId);
        if (orderReponse.getSuccess()) {
            List<GetOrdersDataResponse> orderList = orderReponse.getData().stream().filter(v -> v.getWtzt().equals(GetOrdersDataResponse.YIBAO)).collect(Collectors.toList());
            newStockList = newStockList.stream().filter(v -> orderList.stream().noneMatch(order -> order.getZqdm().equals(v.getStockCode()))).collect(Collectors.toList());
        }
        if (newStockList.isEmpty()) {
            return false;
        }
        SubmitBatTradeV2Request request = new SubmitBatTradeV2Request(userId);
        request.setList(newStockList);

        // TradeResultVo<SubmitBatTradeV2Response> tradeResultVo = tradeRequestHelper.submitBatTradeV2(request);
        // log.info("apply new stock: {}", tradeResultVo);

        List<String> stockInfoList = newStockList.stream().map(n -> n.getStockCode() + "---" + n.getStockName()).collect(Collectors.toList());

        log.info(">>>> 可申购的股票 有: {}", stockInfoList);

        User user = userService.getById(userId);
        String message = MessageFormat.format(
                "可申购提醒: {0}", stockInfoList.toString()
        );
        weChatService.sendTextMessage(user.getWxUserId(), message);
        return true;
    }
}
