package top.yueshushu.learn.crawler.crawler.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import top.yueshushu.learn.crawler.crawler.ExtCrawlerService;
import top.yueshushu.learn.crawler.entity.*;
import top.yueshushu.learn.crawler.parse.StockInfoParser;
import top.yueshushu.learn.crawler.properties.ExtendProperties;
import top.yueshushu.learn.enumtype.BKType;
import top.yueshushu.learn.enumtype.DBStockType;
import top.yueshushu.learn.enumtype.StockPoolType;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
    public static final String CB_DY = "jQuery112306068289769579489_1675942226362";
    public static final String CB_STOCK_BK = "jQuery35104923822815685899_1675941795109";
    public static final String CB_POOL = "callbackdata3383558";
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

    @Override
    public List<BKInfo> findAllDyList() {
        //处理，拼接成信息
        String url = MessageFormat.format(extendProperties.getDyListUrl(), CB_DY);
        return parseBKInfoList(url, BKType.DY);
    }

    private List<BKInfo> parseBKInfoList(String url, BKType bkType) {
        try {
            //获取内容
            String content = restTemplate.getForObject(url, String.class);
            String cb = getCb(bkType);
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

    public static String getCb(BKType bkType) {
        if (null == bkType) {
            return "";
        }
        switch (bkType) {
            case BK: {
                return CB_BK;
            }
            case GN: {
                return CB_GN;
            }
            case DY: {
                return CB_DY;
            }
            default: {
                break;
            }
        }
        return null;
    }

    @Override
    public List<BKMoneyInfo> findTodayBkMoneyList() {
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

    @Override
    public List<BKMoneyInfo> findTodayDyMoneyList() {
        //处理，拼接成信息
        String url = MessageFormat.format(extendProperties.getTodayDyPlatMoneyUrl(), CB_DY);
        return parseMoneyInfoList(url, BKType.DY);
    }

    @Override
    public List<StockBKStockInfo> findRelationBkListByCode(String code) {
        // 对 code 进行处理
        int prefix = 0;
        if (code.startsWith("60")) {
            prefix = 1;
        }

        String fullCode = prefix + "." + code;
        //处理，拼接成信息
        String url = MessageFormat.format(extendProperties.getStockBkStockUrl(), CB_STOCK_BK, fullCode);
        try {
            //获取内容
            String content = restTemplate.getForObject(url, String.class);
            content = content.substring(CB_STOCK_BK.length() + 1);
            content = content.substring(0, content.length() - 2);
            List<StockBKStockInfo> bkMoneyInfos = stockInfoParser.parseBkStockList(content, code);
            return bkMoneyInfos;
        } catch (Exception e) {
            log.error("获取版块列表出错", e);
            return Collections.emptyList();
        }
    }

    private List<BKMoneyInfo> parseMoneyInfoList(String url, BKType bkType) {
        try {
            //获取内容
            String content = restTemplate.getForObject(url, String.class);
            //将内容进行转换，解析
            String cb = getCb(bkType);
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

    @Override
    public List<DBStockInfo> findDbStock(DBStockType dbStockType) {
        //处理，拼接成信息
        String url = MessageFormat.format(extendProperties.getDbStockUrl(), "f3,f12,f14,f350");
        try {
            //获取内容
            String content = restTemplate.getForObject(url, String.class);
            //将内容进行转换，解析
            return stockInfoParser.parseDbStockInfoList(content, dbStockType);
        } catch (Exception e) {
            log.error("获取股票打版列表出错", e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<DBStockInfo> findWillDbStockList(DBStockType dbStockType) {
        // 获取将要涨停的股票.
        List<DBStockInfo> dbStockList = findDbStock(dbStockType);
        List<DBStockInfo> result = new ArrayList<>();

        for (DBStockInfo dbStockInfo : dbStockList) {

            int amplitude = getAmplitude(dbStockInfo);

            if (dbStockInfo.getAmplitude() > amplitude) {
                result.add(dbStockInfo);
            }


        }
        return result;
    }

    private int getAmplitude(DBStockInfo dbStockInfo) {
        if (dbStockInfo.getName().contains("ST")) {
            return 475;
        } else if (dbStockInfo.getCode().startsWith("30")) {
            return 1900;
        } else {
            return 950;
        }
    }

    @Override
    public List<StockPoolInfo> findPoolByType(StockPoolType stockPoolType, Date currentDate) {
        if (null == stockPoolType) {
            return Collections.emptyList();
        }
        // 进行处理和解析
        if (null == currentDate) {
            currentDate = DateUtil.date();
        }
        //根据不同的类型，获取相应的 url
        String url = getRealUrlByPoolType(stockPoolType, currentDate);
        try {
            //获取内容
            String content = restTemplate.getForObject(url, String.class);
            content = content.substring(CB_POOL.length() + 1);
            content = content.substring(0, content.length() - 2);
            return stockInfoParser.parsePoolInfoList(content, stockPoolType, currentDate);
        } catch (Exception e) {
            log.error("获取 股票池 {} 列表出错", stockPoolType.getDesc(), e);
            return Collections.emptyList();
        }
    }

    private String getRealUrlByPoolType(StockPoolType stockPoolType, Date currentDate) {
        String url = "";
        switch (stockPoolType) {
            case ZT: {
                url = extendProperties.getZtTopicUrl();
                break;
            }
            case DT: {
                url = extendProperties.getDtTopicUrl();
                break;
            }
            case YES_ZT: {
                url = extendProperties.getYesZtTopicUrl();
                break;
            }
            case QS: {
                url = extendProperties.getQsTopicUrl();
                break;
            }
            case CX: {
                url = extendProperties.getCxTopicUrl();
                break;
            }
            case ZB: {
                url = extendProperties.getZbTopicUrl();
                break;
            }
            default: {
                break;
            }
        }
        String date = DateUtil.format(currentDate, DatePattern.PURE_DATE_PATTERN);
        return MessageFormat.format(url, CB_POOL, date);
    }
}
