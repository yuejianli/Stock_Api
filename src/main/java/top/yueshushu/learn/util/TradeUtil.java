package top.yueshushu.learn.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanMap;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import top.yueshushu.learn.api.request.AuthenticationRequest;
import top.yueshushu.learn.api.request.BaseTradeRequest;
import top.yueshushu.learn.api.responseparse.*;
import top.yueshushu.learn.common.Const;
import top.yueshushu.learn.domain.TradeMethodDo;
import top.yueshushu.learn.domain.TradeUserDo;
import top.yueshushu.learn.domainservice.TradeMethodDomainService;
import top.yueshushu.learn.domainservice.TradeUserDomainService;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description 交易使用的 工具类，将 TradeApiServiceImpl 拆开使用
 * @Author 岳建立
 * @Date 2022/1/3 17:52
 **/
@Component
@Slf4j
public class TradeUtil {
    @Resource
    private TradeMethodDomainService tradeMethodDomainService;
    @Resource
    private TradeUserDomainService tradeUserDomainService;
    /**
     * 获取请求地址
     * @param request
     * @return
     */
    public String getUrl(BaseTradeRequest request) {
        TradeMethodDo tradeMethodDo = tradeMethodDomainService.getMethodByCode(request.getMethod());
        Assert.notNull(tradeMethodDo,"方法"+request.getMethod()+"对应的交易方法不能为空");
        TradeUserDo tradeUserDo = tradeUserDomainService.getByUserId(request.getUserId());
        Assert.notNull(tradeUserDo,"登录用户"+request.getUserId()+"对应的交易用户不能为空");
        String url = tradeMethodDo.getUrl();
        Assert.notNull(url,"方法"+ tradeMethodDo.getCode()+"对应的url网址不能为空");
        return MessageFormat.format(url, tradeUserDo.getValidateKey());
    }

    public Map<String, String> getHeader(BaseTradeRequest request) {
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
     * 获取参数
     * @param request
     * @return
     */
    public Map<String, Object> getParams(BaseTradeRequest request) {
        Map<Object, Object> beanMap = new BeanMap(request);
        HashMap<String, Object> params = new HashMap<>();
        beanMap.entrySet().stream().filter(entry -> !Const.IgnoreList.contains(entry.getKey()))
                .forEach(entry -> params.put(String.valueOf(entry.getKey()), entry.getValue()));
        return params;
    }


    @Resource(name = "dataObjResponseParser")
    private DataObjResponseParser dataObjResponseParser;

    @Resource(name = "msgResponseParser")
    private MsgResponseParser msgResponseParser;

    @Resource(name = "objResponseParser")
    private ObjResponseParser objResponseParser;

    @Resource(name = "dataListResponseParser")
    private DataListResponseParser dataListResponseParser;
    @Resource(name = "dataPropertiesListResponseParser")
    private DataPropertiesListResponseParser dataPropertiesListResponseParser;

    public ResponseParser getResponseParser(BaseTradeRequest request) {
        if (request.responseVersion() == dataObjResponseParser.version()) {
            return dataObjResponseParser;
        }
        if (request.responseVersion() == msgResponseParser.version()) {
            return msgResponseParser;
        }
        if (request.responseVersion() == objResponseParser.version()) {
            return objResponseParser;
        }
        if (request.responseVersion() == dataListResponseParser.version()) {
            return dataListResponseParser;
        }
        if (request.responseVersion() == dataPropertiesListResponseParser.version()) {
            return dataPropertiesListResponseParser;
        }
        // 设置一个默认的
        return dataObjResponseParser;
    }
}
