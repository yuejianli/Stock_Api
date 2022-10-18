package top.yueshushu.learn.ocr.impl;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import top.yueshushu.learn.crawler.util.HttpUtil;
import top.yueshushu.learn.ocr.AbstractOcrService;

import java.util.HashMap;
import java.util.Map;

@Service("aliyunOcrService")
public class AliyunOcrServiceImpl extends AbstractOcrService {

    private final Logger logger = LoggerFactory.getLogger(AliyunOcrServiceImpl.class);

    @Value("${ocr.third.aliyun.appcode}")
    private String appcode;

    @Override
    protected String processBase64(String base64) {
        HashMap<String, String> header = new HashMap<>();
        header.put("Authorization", "APPCODE " + appcode);

        HashMap<String, Object> params = new HashMap<>();
        params.put("image", base64);
        params.put("type", "1003");

        String result = HttpUtil.sendPost(httpClient, "https://302307.market.alicloudapi.com/ocr/captcha", params, header);
        logger.info("aliyun result: {}", result);
        @SuppressWarnings("unchecked")
        Map<String, Object> map = JSON.parseObject(result, Map.class);
        @SuppressWarnings("unchecked")
        Map<String, Object> data = (Map<String, Object>) map.get("data");
        return (String) data.get("captcha");
    }

}
