package top.yueshushu.learn.crawler.crawler.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import top.yueshushu.learn.crawler.crawler.ExtCrawlerService;
import top.yueshushu.learn.crawler.entity.BKInfo;
import top.yueshushu.learn.crawler.entity.BKMoneyInfo;
import top.yueshushu.learn.crawler.parse.StockInfoParser;
import top.yueshushu.learn.crawler.properties.ExtendProperties;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;

/**
 * 默认的扩展程序
 *
 * @author yuejianli
 * @date 2023-02-02
 */
@Service("defaultExtCrawlerService")
@Slf4j
public class DefaultExtCrawlerServiceImpl implements ExtCrawlerService {
    public static final String CB_SIGN = "jQuery112308426405317436305_1675081771315";
    @Resource
    private CloseableHttpClient httpClient;
    @Resource
    private ExtendProperties extendProperties;

    @Resource(name = "defaultStockInfoParser")
    private StockInfoParser stockInfoParser;

    @Resource(name = "restTemplate")
    private RestTemplate restTemplate;

    @Override
    public List<BKInfo> findAllBkList() {
        //处理，拼接成信息
        String url = MessageFormat.format(extendProperties.getBkListUrl(), CB_SIGN);
        try {
            //获取内容
            String content = restTemplate.getForObject(url, String.class);
            //将内容进行转换，解析
            content = content.substring(CB_SIGN.length() + 1);
            content = content.substring(0, content.length() - 2);
            return stockInfoParser.parseBkInfoList(content);
        } catch (Exception e) {
            log.error("获取版块列表出错", e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<BKMoneyInfo> findTodayBKMoneyList() {
        //处理，拼接成信息
        String url = MessageFormat.format(extendProperties.getTodayPlatMoneyUrl(), CB_SIGN);
        try {
            //获取内容
            String content = restTemplate.getForObject(url, String.class);
            //将内容进行转换，解析
            content = content.substring(CB_SIGN.length() + 1);
            content = content.substring(0, content.length() - 2);
            return stockInfoParser.parseTodayBKMoneyInfoList(content);
        } catch (Exception e) {
            log.error("获取版块今日注入列表出错", e);
            return Collections.emptyList();
        }
    }
}
