package top.yueshushu.learn.crawler.crawler.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import top.yueshushu.learn.crawler.crawler.ExtCrawlerService;
import top.yueshushu.learn.crawler.entity.BKInfo;
import top.yueshushu.learn.crawler.entity.BKMoneyInfo;
import top.yueshushu.learn.crawler.parse.StockInfoParser;
import top.yueshushu.learn.crawler.properties.ExtendProperties;
import top.yueshushu.learn.enumtype.BKType;

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
    public static final String CB_BK = "jQuery112308426405317436305_1675081771315";
    public static final String CB_GN = "jQuery1123011794716360084134_1675835544241";
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
        String url = MessageFormat.format(extendProperties.getBkListUrl(), CB_BK);
        return parseBKInfoList(url, BKType.BK);
    }

    @Override
    public List<BKInfo> findAllGnList() {
        //处理，拼接成信息
        String url = MessageFormat.format(extendProperties.getGnListUrl(), CB_GN);
        return parseBKInfoList(url, BKType.GN);
    }

    private List<BKInfo> parseBKInfoList(String url, BKType bkType) {
        try {
            //获取内容
            String content = restTemplate.getForObject(url, String.class);
            String cb = BKType.BK.equals(bkType) ? CB_BK : CB_GN;
            //将内容进行转换，解析
            content = content.substring(cb.length() + 1);
            content = content.substring(0, content.length() - 2);
            List<BKInfo> bkInfos = stockInfoParser.parseBkInfoList(content);
            if (!CollectionUtils.isEmpty(bkInfos)) {
                bkInfos.forEach(
                        n -> {
                            n.setType(bkType.getCode());
                        }
                );
            }
            return bkInfos;
        } catch (Exception e) {
            log.error("获取版块列表出错", e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<BKMoneyInfo> findTodayBKMoneyList() {
        //处理，拼接成信息
        String url = MessageFormat.format(extendProperties.getTodayPlatMoneyUrl(), CB_BK);
        return parseMoneyInfoList(url, BKType.BK);
    }

    @Override
    public List<BKMoneyInfo> findTodayGnMoneyList() {
        //处理，拼接成信息
        String url = MessageFormat.format(extendProperties.getTodayGnPlatMoneyUrl(), CB_GN);
        return parseMoneyInfoList(url, BKType.GN);
    }

    private List<BKMoneyInfo> parseMoneyInfoList(String url, BKType bkType) {
        try {
            //获取内容
            String content = restTemplate.getForObject(url, String.class);
            //将内容进行转换，解析
            String cb = BKType.BK.equals(bkType) ? CB_BK : CB_GN;
            content = content.substring(cb.length() + 1);
            content = content.substring(0, content.length() - 2);
            List<BKMoneyInfo> bkMoneyInfos = stockInfoParser.parseTodayBKMoneyInfoList(content);
            if (!CollectionUtils.isEmpty(bkMoneyInfos)) {
                bkMoneyInfos.forEach(
                        n -> {
                            n.setType(bkType.getCode());
                        }
                );
            }
            return bkMoneyInfos;
        } catch (Exception e) {
            log.error("获取版块列表出错", e);
            return Collections.emptyList();
        }
    }
}
