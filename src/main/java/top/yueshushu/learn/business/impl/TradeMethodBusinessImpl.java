package top.yueshushu.learn.business.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import top.yueshushu.learn.business.TradeMethodBusiness;
import top.yueshushu.learn.common.Const;
import top.yueshushu.learn.crawler.util.HttpUtil;
import top.yueshushu.learn.entity.TradeMethod;
import top.yueshushu.learn.enumtype.TradeMethodType;
import top.yueshushu.learn.mode.ro.TradeMethodRo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.service.TradeMethodService;
import top.yueshushu.learn.util.RedisUtil;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;

/**
 * @Description 交易方法信息实现
 * @Author 岳建立
 * @Date 2022/5/20 22:33
 **/
@Service
@Slf4j
public class TradeMethodBusinessImpl implements TradeMethodBusiness {
    @Resource
    private TradeMethodService tradeMethodService;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private CloseableHttpClient httpClient;

    @Override
    public TradeMethod getMethod(TradeMethodType tradeMethodType) {
        return tradeMethodService.getMethod(tradeMethodType);
    }

    @Override
    public OutputResult list(TradeMethodRo tradeMethodRo) {
        return tradeMethodService.pageList(
                tradeMethodRo
        );
    }

    @Override
    public OutputResult<String> yzm(Integer userId) {
        TradeMethod tradeMethod = getMethod(TradeMethodType.yzm);
        //获取方法的 url
        String url = tradeMethod.getUrl();

        long nowDateTimestamp = System.currentTimeMillis();

        String randNum = "0.903" + nowDateTimestamp;

        String raNum = "0.483" + nowDateTimestamp;

        String yzmUrl = MessageFormat.format(url, randNum, raNum);

        redisUtil.set(userId + "_RAND_NUM", randNum, Const.YZM_TIME);
        redisUtil.set(userId + "_RA", raNum, Const.YZM_TIME);

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
