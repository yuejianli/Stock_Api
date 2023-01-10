package top.yueshushu.learn.helper;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanMap;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import top.yueshushu.learn.api.TradeResultVo;
import top.yueshushu.learn.api.request.*;
import top.yueshushu.learn.api.response.*;
import top.yueshushu.learn.api.responseparse.ResponseParser;
import top.yueshushu.learn.config.TradeClient;
import top.yueshushu.learn.domain.TradeUserDo;
import top.yueshushu.learn.domainservice.TradeMethodDomainService;
import top.yueshushu.learn.domainservice.TradeUserDomainService;
import top.yueshushu.learn.entity.TradeUser;
import top.yueshushu.learn.enumtype.TradeMethodType;
import top.yueshushu.learn.service.impl.TradeApiServiceImpl;
import top.yueshushu.learn.util.TradeUtil;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 交易使用的接口信息
 *
 * @author yuejianli
 * @date 2023-01-09
 */
@Component
@Slf4j
public class TradeRequestHelper {
    @Resource
    private TradeUtil tradeUtil;
    @Resource
    private TradeClient tradeClient;
    @Resource
    private TradeMethodDomainService tradeMethodDomainService;
    @Resource
    private TradeUserDomainService tradeUserDomainService;
    // @Value(value = "${emSecSecurityServerUrl}")
    private String emSecSecurityServerUrl;

    /**
     * 用户登录。 如果登录失败，返回 null
     *
     * @param tradeUser 财富证券用户信息
     */
    public AuthenticationResponse login(TradeUser tradeUser) {
        AuthenticationRequest request = new AuthenticationRequest(tradeUser.getUserId());
        request.setPassword(tradeUser.getPassword());
        request.setIdentifyCode(tradeUser.getIdentifyCode());
        request.setRandNumber(tradeUser.getRandNum());
        setRequestSecInfo(request);
        Map<String, String> header = getHeader(request);
        if (!StringUtils.hasLength(request.getSecInfo())) {
            log.info("authentication use mac User-Agent");
            header.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/106.0.0.0 Safari/536.66");
        }
        Map<String, Object> params = getParams(request);
        params.put("userId", tradeUser.getAccount());
        TradeResultVo<AuthenticationResponse> resultVo = null;
        log.info("系统用户{}登录时成功构建了登录请求信息", tradeUser.getUserId());

        ;
        try {
            tradeClient.openSession();
            String content = tradeClient.sendNewInstance(
                    tradeMethodDomainService.getMethodByCode(TradeMethodType.AuthenticationRequest.getCode()).getUrl(),
                    params, header);
            ResponseParser responseParse = tradeUtil.getResponseParser(request);
            resultVo = responseParse.parse(content, new TypeReference<AuthenticationResponse>() {
            });
            if (resultVo.getSuccess()) {
                log.info("系统用户{}登录请求成功", tradeUser.getUserId());
                String content2 = tradeClient.sendNewInstance(
                        tradeMethodDomainService.getMethodByCode(TradeMethodType.AuthenticationCheckRequest.getCode()).getUrl(),
                        new HashMap<>(2), header);
                String validateKey = getValidateKey(content2);

                AuthenticationResponse response = new AuthenticationResponse();
                response.setCookie(tradeClient.getCurrentCookie());
                response.setValidateKey(validateKey);
                resultVo.setData(Arrays.asList(response));
                log.info("系统用户{}响应数据成功", tradeUser.getUserId());
            } else {
                log.error("系统用户{}登录请求失败", tradeUser.getUserId(), resultVo.getMessage());
                return null;
            }
        } catch (Exception e) {
            log.error("系统用户{}登录请求失败", tradeUser.getUserId(), e);
            return null;
        } finally {
            tradeClient.destoryCurrentSession();
        }
        if (!resultVo.getSuccess()) {
            log.info("系统用户{}未成功登录", tradeUser.getUserId());
            return null;
        }
        return resultVo.getData().get(0);
    }


    /**
     * 获取真实的股票持仓响应的信息
     *
     * @param userId 用户编号
     * @return 获取真实的股票持仓响应的信息
     */
    public TradeResultVo<GetStockListResponse> findRealPosition(Integer userId) {
        GetStockListRequest request = new GetStockListRequest(userId);
        return componentAndSendReqeust(request, new TypeReference<GetStockListResponse>() {
        });

    }

    /**
     * 获取今日委托的信息
     *
     * @param userId 用户编号
     * @return 获取委托的信息
     */
    public TradeResultVo<GetOrdersDataResponse> findRealEntrust(Integer userId) {
        GetOrdersDataRequest request = new GetOrdersDataRequest(userId);
        return componentAndSendReqeust(request, new TypeReference<GetOrdersDataResponse>() {
        });
    }


    /**
     * 获取历史委托响应信息
     *
     * @param userId
     * @return
     */
    public TradeResultVo<GetHisOrdersDataResponse> findRealHistoryEntrust(Integer userId) {
        GetHisOrdersDataRequest request = new GetHisOrdersDataRequest(userId);
        request.setEt(DateUtil.format(new Date(), "yyyy-MM-dd"));
        Date et = new Date();
        et.setTime(et.getTime() - 7 * 24 * 3600 * 1000);
        request.setSt(DateUtil.format(et, "yyyy-MM-dd"));
        return componentAndSendReqeust(request, new TypeReference<GetHisOrdersDataResponse>() {
        });
    }

    /**
     * 获取响应的信息
     *
     * @param userId 用户编号
     */
    public TradeResultVo<GetDealDataResponse> listRealDeal(Integer userId) {
        GetDealDataRequest request = new GetDealDataRequest(userId);
        return componentAndSendReqeust(request, new TypeReference<GetDealDataResponse>() {
        });

    }


    /**
     * 获取真实的历史成交响应的信息
     *
     * @param userId 用户编号
     */
    public TradeResultVo<GetHisDealDataResponse> listRealHistoryDeal(Integer userId) {

        GetHisDealDataRequest request = new GetHisDealDataRequest(userId);
        request.setEt(DateUtil.format(new Date(), "yyyy-MM-dd"));
        Date et = new Date();
        et.setTime(et.getTime() - 7 * 24 * 3600 * 1000);
        request.setSt(DateUtil.format(et, "yyyy-MM-dd"));
        return componentAndSendReqeust(request, new TypeReference<GetHisDealDataResponse>() {
        });
    }


    /**
     * 发送真实的买入请求
     *
     * @param request 买请求
     */
    public TradeResultVo<SubmitResponse> sendRealBuyReq(SubmitRequest request) {
        return componentAndSendReqeust(request, new TypeReference<SubmitResponse>() {
        });
    }

    /**
     * 发送真实的卖出请求
     *
     * @param request 卖出请求
     */
    public TradeResultVo<SubmitResponse> sendRealSellReq(SubmitRequest request) {
        return componentAndSendReqeust(request, new TypeReference<SubmitResponse>() {
        });
    }

    /**
     * 可申请的股票列表
     *
     * @param userId 用户编号
     */
    public TradeResultVo<GetCanBuyNewStockListV3Response> getCanBuyNewStockListV3(Integer userId) {
        GetCanBuyNewStockListV3Request request = new GetCanBuyNewStockListV3Request(userId);
        return componentAndSendReqeust(request, new TypeReference<GetCanBuyNewStockListV3Response>() {
        });
    }

    /**
     * 发送真实的撤销请求
     *
     * @param request 撤销请求
     * @return
     */
    public TradeResultVo<RevokeResponse> sendRealRevokeReq(RevokeRequest request) {
        return componentAndSendReqeust(request, new TypeReference<RevokeResponse>() {
        });
    }

    /**
     * 批量申购
     *
     * @param submitBatTradeV2Request 申购请求
     */
    public TradeResultVo<SubmitBatTradeV2Response> submitBatTradeV2(SubmitBatTradeV2Request submitBatTradeV2Request) {
        return componentAndSendReqeust(submitBatTradeV2Request, new TypeReference<SubmitBatTradeV2Response>() {
        });
    }

    public <T extends BaseTradeResponse> TradeResultVo<T> componentAndSendReqeust(BaseTradeRequest request, TypeReference<T> responseType) {
        ResponseParser responseParse = tradeUtil.getResponseParser(request);

        String url = tradeUtil.getUrl(request);
        log.debug("trade {} url: {}", request.getMethod(), url);
        Map<String, String> header = getHeader(request);

        List<Map<String, Object>> paramList = null;
        Map<String, Object> params = null;
        boolean isSendList = request instanceof BaseTradeListRequest;
        if (isSendList) {
            paramList = ((BaseTradeListRequest) request).getList().stream().map(this::getParams).collect(Collectors.toList());
            log.debug("trade {} request: {}", request.getMethod(), paramList);
        } else {
            params = getParams(request);
            log.debug("trade {} request: {}", request.getMethod(), params);
        }
        String content;
        if (isSendList) {
            content = tradeClient.sendListJson(url, paramList, header);
        } else {
            content = tradeClient.send(url, params, header);
        }
        log.debug("trade {} response: {}", request.getMethod(), content);
        return responseParse.parse(content, responseType);
    }
    /**
     * 设置信息
     *
     * @param request 请求对象
     */
    private void setRequestSecInfo(AuthenticationRequest request) {
        if (emSecSecurityServerUrl == null) {
            return;
        }

        try {
            tradeClient.openSession();
            String content = tradeClient.sendNewInstance(emSecSecurityServerUrl + request.getIdentifyCode(), null, null);
            @SuppressWarnings("unchecked")
            Map<String, String> map = JSON.parseObject(content, Map.class);
            String userInfo = map.get("userInfo");
            request.setSecInfo(userInfo);
        } catch (Exception e) {
            log.info("not code get from EM SecSecurity Server");
        } finally {
            tradeClient.destoryCurrentSession();
        }
    }

    /**
     * 组装请求头
     *
     * @param request 请求对象
     */
    private Map<String, String> getHeader(BaseTradeRequest request) {
        //根据id 去查询对应的交易账户
        TradeUserDo tradeUserDo = tradeUserDomainService.getByUserId(request.getUserId());
        HashMap<String, String> header = new HashMap<>();
        if (!(request instanceof AuthenticationRequest)) {
            header.put("cookie", tradeUserDo.getCookie());
        }
        header.put("gw_reqtimestamp", System.currentTimeMillis() + "");
        header.put("X-Requested-With", "XMLHttpRequest");
        return header;
    }

    /**
     * 组装参数
     *
     * @param request 请求对象
     * @return 请求信息
     */
    private Map<String, Object> getParams(Object request) {
        Map<String, Object> beanMap = new BeanMap(request);
        HashMap<String, Object> params = new HashMap<>(10);
        beanMap.entrySet().stream().filter(entry -> !TradeApiServiceImpl.IgnoreList.contains(String.valueOf(entry.getKey())))
                .forEach(entry -> params.put(String.valueOf(entry.getKey()), entry.getValue()));
        return params;
    }

    /**
     * 获取验证信息
     *
     * @param content 内容
     * @return 获取验证信息
     */
    private String getValidateKey(String content) {
        String key = "input id=\"em_validatekey\" type=\"hidden\" value=\"";
        int inputBegin = content.indexOf(key) + key.length();
        int inputEnd = content.indexOf("\" />", inputBegin);
        return content.substring(inputBegin, inputEnd);
    }
}
