package top.yueshushu.learn.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import top.yueshushu.learn.business.TradeMethodBusiness;
import top.yueshushu.learn.crawler.util.HttpUtil;
import top.yueshushu.learn.entity.TradeMethod;
import top.yueshushu.learn.enumtype.TradeMethodType;
import top.yueshushu.learn.mode.ro.TradeMethodRo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.util.RedisUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;

/**
 * <p>
 * 交易，包括爬虫所使用的url信息 我是自定义的
 * </p>
 *
 * @author 岳建立
 * @date 2022-01-02
 */
@RestController
@RequestMapping("/tradeMethod")
@Api("交易相关方法的处理")
public class TradeMethodController {

    @Resource
    private TradeMethodBusiness tradeMethodBusiness;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private CloseableHttpClient httpClient;


    @PostMapping("/list")
    @ApiOperation("查询提供的交易方法")
    public OutputResult list(@RequestBody TradeMethodRo tradeMethodRo) {
        return tradeMethodBusiness.list(tradeMethodRo);
    }

    @GetMapping("/yzm")
    @ApiOperation("获取验证码")
    public OutputResult yzm(HttpSession httpSession) {
        TradeMethod tradeMethod = tradeMethodBusiness.getMethod(
                TradeMethodType.yzm
        );
        //获取方法的 url
        String url = tradeMethod.getUrl();

        long nowDateTimestamp = System.currentTimeMillis();

        String randNum = "0.903" + nowDateTimestamp;

        String raNum = "0.483" + nowDateTimestamp;

        String yzmUrl = MessageFormat.format(url, randNum, raNum);

        redisUtil.set(httpSession.getId() + "RAND_NUM", randNum);
        redisUtil.set(httpSession.getId() + "RA", raNum);

        // 对网址进行处理,返回一个 base64 的数据信息。

        try (CloseableHttpResponse response = HttpUtil.sendGetResponse(httpClient, yzmUrl)) {
            InputStream in = response.getEntity().getContent();
            byte[] buf = FileCopyUtils.copyToByteArray(in);
            String base64 = Base64.encodeBase64String(buf);
            return OutputResult.buildSucc(base64);
        } catch (IOException e) {
            return OutputResult.buildFail();
        }
    }
}
