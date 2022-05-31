package top.yueshushu.learn.util;

import org.apache.commons.beanutils.BeanMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import top.yueshushu.learn.api.request.BaseTradeRequest;
import top.yueshushu.learn.common.Const;
import top.yueshushu.learn.domain.TradeMethodDo;
import top.yueshushu.learn.domain.TradeUserDo;
import top.yueshushu.learn.domainservice.TradeMethodDomainService;
import top.yueshushu.learn.domainservice.TradeUserDomainService;
import top.yueshushu.learn.service.TradeMethodService;
import top.yueshushu.learn.service.TradeUserService;

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

    /**
     * 获取请求的信息
     * @param request
     * @return
     */
    public Map<String, String> getHeader(BaseTradeRequest request) {
        TradeUserDo tradeUserDo = tradeUserDomainService.getByUserId(request.getUserId());
        Assert.notNull(tradeUserDo,"登录用户"+request.getUserId()+"对应的交易用户不能为空");
        HashMap<String, String> header = new HashMap<>();
        String cookie= tradeUserDo.getCookie();
        Assert.notNull(cookie,"交易用户"+request.getUserId()+"没有cookie,未登录");
        header.put("cookie",cookie);
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
}
