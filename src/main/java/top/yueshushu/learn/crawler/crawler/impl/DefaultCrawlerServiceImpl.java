package top.yueshushu.learn.crawler.crawler.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import top.yueshushu.learn.crawler.crawler.CrawlerService;
import top.yueshushu.learn.crawler.entity.DownloadStockInfo;
import top.yueshushu.learn.crawler.entity.StockHistoryCsvInfo;
import top.yueshushu.learn.crawler.entity.TxStockHistoryInfo;
import top.yueshushu.learn.crawler.parse.DailyTradingInfoParse;
import top.yueshushu.learn.crawler.parse.StockInfoParser;
import top.yueshushu.learn.crawler.parse.StockShowInfoParse;
import top.yueshushu.learn.crawler.properties.DefaultProperties;
import top.yueshushu.learn.crawler.util.HttpUtil;
import top.yueshushu.learn.crawler.util.ImageUtil;
import top.yueshushu.learn.mode.info.StockShowInfo;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 东方财富的相关实现
 * @Author 岳建立
 * @Date 2021/11/7 10:37
 **/
@Service("defaultCrawlerService")
@Slf4j
public class DefaultCrawlerServiceImpl implements CrawlerService {

    @Resource
    private CloseableHttpClient httpClient;

    @Resource(name = "defaultStockShowInfoParse")
    private StockShowInfoParse stockShowInfoParse;

    @Resource(name = "defaultStockInfoParser")
    private StockInfoParser stockInfoParser;

    @Resource
    private DefaultProperties defaultProperties;

    @Resource
    private RestTemplate restTemplate;

    @Resource(name = "defaultDailyTradingInfoParse")
    private DailyTradingInfoParse dailyTradingInfoParse;

    @Override
    public List<DownloadStockInfo> getStockList() {
        //处理，拼接成信息
        String url = MessageFormat.format(defaultProperties.getAllStockUrl(), "f12,f13,f14");
        log.info(">>>访问地址:" + url);
        try {
            //获取内容
            String content = restTemplate.getForObject(url, String.class);
            //将内容进行转换，解析
            return stockInfoParser.parseStockInfoList(content);
        } catch (Exception e) {
            log.error("获取股票全量列表出错",e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<StockHistoryCsvInfo> parseStockHistoryList(String code,
                                                           String startDate,
                                                           String endDate) {
        //处理，拼接成信息
        String url = MessageFormat.format(defaultProperties.getStockHistoryDownloadUrl(), code,
                startDate, endDate);
        log.info(">>>访问地址:" + url);
        try {
            //获取内容
            ResponseEntity<byte[]> forEntity = restTemplate.getForEntity(url, byte[].class);
            //将内容进行转换，解析
            List<StockHistoryCsvInfo> stockHistoryCsvInfoList = dailyTradingInfoParse.parseStockHistoryList(
                    new ByteArrayInputStream(forEntity.getBody())
            );
            if (!CollectionUtils.isEmpty(stockHistoryCsvInfoList)) {
                return stockHistoryCsvInfoList;
            }
            log.info(">>转换股票类型,继续查询");
            //继续同步
            Integer newType = 0;
            url = MessageFormat.format(defaultProperties.getStockHistoryDownloadUrl(),
                    newType + code.substring(1),
                    startDate, endDate);

            //获取内容
            forEntity = restTemplate.getForEntity(url, byte[].class);
            //将内容进行转换，解析
            stockHistoryCsvInfoList = dailyTradingInfoParse.parseStockHistoryList(
                    new ByteArrayInputStream(forEntity.getBody())
            );
            return stockHistoryCsvInfoList;
        } catch (Exception e) {
            log.error("获取股票 {} 历史数据列表出错",code,e);
            return Collections.emptyList();
        }
    }

    @Override
    public StockShowInfo getNowInfo(String code) {
        //处理，拼接成信息
        String url = MessageFormat.format(defaultProperties.getShowDayUrl(), code);
        log.info(">>>访问地址:" + url);
        try {
            //获取内容
            Map<String, String> header = new HashMap<>();
            header.put("Referer","https://finance.sina.com.cn");
            String content = HttpUtil.sendGet(httpClient, url, header,"gbk");
            //将内容进行转换，解析
            return stockShowInfoParse.parse(content);
        } catch (Exception e) {
            log.error("获取股票{} 当前信息 列表出错",code,e);
            return null;
        }
    }

    @Override
    public String getMinUrl(String code) {
        //处理，拼接成信息
        String url = MessageFormat.format(defaultProperties.getMinUrl(), code);
        log.info(">>>小时访问地址:" + url);
        try {
            //获取内容
            byte[] btImg1 = ImageUtil.getImageFromNetByUrl(url);
            String content = Base64.encode(btImg1);
            //将内容进行转换，解析
            return content;
        } catch (Exception e) {
            log.error("获取股票{} 当前信息分钟K线出错",code,e);
            return null;
        }
    }

    @Override
    public String getDayUrl(String code) {
        //处理，拼接成信息
        String url = MessageFormat.format(defaultProperties.getDailyUrl(), code);
        log.info(">>>访问地址:" + url);
        try {
            //获取内容
            byte[] btImg1 = ImageUtil.getImageFromNetByUrl(url);
            String content = Base64.encode(btImg1);
            //将内容进行转换，解析
            return content;
        } catch (Exception e) {
            log.error("获取股票{} 当前信息天 K线出错",code,e);
            return null;
        }
    }

    @Override
    public String getWeekUrl(String code) {
        //处理，拼接成信息
        String url = MessageFormat.format(defaultProperties.getWeeklyUrl(), code);
        log.info(">>>访问地址:" + url);
        try {
            //获取内容
            byte[] btImg1 = ImageUtil.getImageFromNetByUrl(url);
            String content = Base64.encode(btImg1);
            //将内容进行转换，解析
            return content;
        } catch (Exception e) {
            log.error("获取股票{} 当前信息周 K线出错",code,e);
            return null;
        }
    }

    @Override
    public String getMonthUrl(String code) {
        //处理，拼接成信息
        String url = MessageFormat.format(defaultProperties.getMonthlyUrl(), code);
        log.info(">>>访问地址:" + url);
        try {
            //获取内容
            byte[] btImg1 = ImageUtil.getImageFromNetByUrl(url);
            String content = Base64.encode(btImg1);
            //将内容进行转换，解析
            return content;
        } catch (Exception e) {
            log.error("获取股票{} 当前信息月 K线出错",code,e);
            return null;
        }
    }

    @Override
    public String sinaGetPrice(String fullCode) {
        //处理，拼接成信息
        String url = MessageFormat.format(defaultProperties.getShowDayUrl(), fullCode);
      //  log.info(">>>访问地址:" + url);
        try {
            //获取内容
            //获取内容
            Map<String, String> header = new HashMap<>();
            header.put("Referer","https://finance.sina.com.cn");
            String content = HttpUtil.sendGet(httpClient, url, header,"gbk");
            //将内容直接按照 ,号进行拆分
            return content.split("\\,")[3];
        } catch (Exception e) {
            log.error("获取股票{} 当前 价格 出错",fullCode,e);
            return "0.00";
        }
    }

    @Override
    public List<StockHistoryCsvInfo> parseEasyMoneyYesHistory(List<String> codeList, DateTime beforeLastWorking) {
        //处理，拼接成信息
        String url = defaultProperties.getEasyMoneyHistoryUrl();
        log.info(">>>访问地址:" + url);
        try {
            //获取内容
            String content = HttpUtil.sendGet(httpClient,url);
            //将内容进行转换，解析
            List<StockHistoryCsvInfo> stockHistoryCsvInfoList = dailyTradingInfoParse.parseEasyMoneyHistory(content,
                    codeList, beforeLastWorking);
            return stockHistoryCsvInfoList;
        } catch (Exception e) {
            log.error("获取股票 {} 最近交易日数据列表出错", codeList, e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<TxStockHistoryInfo> parseTxMoneyYesHistory(List<String> codeList, DateTime beforeLastWorking) {
        //处理，拼接成信息
        String qParam = StrUtil.join(",", codeList);
        String url = MessageFormat.format(defaultProperties.getTxMoneyHistoryUrl(), qParam);
        log.info(">>>访问地址:" + url);
        try {
            //获取内容
            Map<String, String> header = new HashMap<>();
            header.put("Referer", "http://qt.gtimg.cn");
            String content = HttpUtil.sendGet(httpClient, url, header, "gbk");
            //将内容进行转换，解析
            return dailyTradingInfoParse.parseTxMoneyHistory(content, codeList, beforeLastWorking);
        } catch (Exception e) {
            log.error("获取股票最近历史记录 {} 当前信息 列表出错", qParam, e);
            return null;
        }
    }

}
