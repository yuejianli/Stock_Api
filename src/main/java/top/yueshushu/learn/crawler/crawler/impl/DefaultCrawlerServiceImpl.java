package top.yueshushu.learn.crawler.crawler.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import top.yueshushu.learn.crawler.crawler.CrawlerService;
import top.yueshushu.learn.crawler.entity.*;
import top.yueshushu.learn.crawler.parse.DailyTradingInfoParse;
import top.yueshushu.learn.crawler.parse.StockInfoParser;
import top.yueshushu.learn.crawler.parse.StockShowInfoParse;
import top.yueshushu.learn.crawler.properties.DefaultProperties;
import top.yueshushu.learn.crawler.properties.ExtendProperties;
import top.yueshushu.learn.crawler.util.HttpUtil;
import top.yueshushu.learn.crawler.util.ImageUtil;
import top.yueshushu.learn.crawler.util.QueryParamUtil;
import top.yueshushu.learn.mode.info.StockShowInfo;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
    private ExtendProperties extendProperties;

    @Resource(name = "restTemplate")
    private RestTemplate restTemplate;

    @Resource(name = "defaultDailyTradingInfoParse")
    private DailyTradingInfoParse dailyTradingInfoParse;

    @Override
    public List<DownloadStockInfo> getStockList() {
        //处理，拼接成信息
        String url = MessageFormat.format(defaultProperties.getAllStockUrl(), "f12,f13,f14");
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
    public StockShowInfo getNowInfo(String fullCode) {
        //处理，拼接成信息
        String url = MessageFormat.format(defaultProperties.getShowDayUrl(), fullCode);
        try {
            //获取内容
            Map<String, String> header = new HashMap<>();
            header.put("Referer", "https://finance.sina.com.cn");
            String content = HttpUtil.sendGet(httpClient, url, header, "gbk");
            //将内容进行转换，解析
            return stockShowInfoParse.parse(content);
        } catch (Exception e) {
            log.error("获取股票{} 当前信息 列表出错", fullCode, e);
            return null;
        }
    }

    @Override
    public String getMinUrl(String code) {
        //处理，拼接成信息
        String url = MessageFormat.format(defaultProperties.getMinUrl(), code);
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
    public Map<String, BigDecimal> sinaGetPrice(List<String> fullCodeList) {
        //处理，拼接成信息
        String codeListStr = StrUtil.join(",", fullCodeList);
        String url = MessageFormat.format(defaultProperties.getShowDayUrl(), codeListStr);
        try {
            //获取内容
            //获取内容
            Map<String, String> header = new HashMap<>();
            header.put("Referer", "https://finance.sina.com.cn");
            String content = HttpUtil.sendGet(httpClient, url, header, "gbk");
            //将内容直接按照 ,号进行拆分
            BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(content.getBytes(Charset.forName("utf8"))), Charset.forName("utf8")));

            Map<String, BigDecimal> result = new HashMap<>();
            String prefix = "var hq_str_sz";
            String singleContent = "";
            while ((singleContent = br.readLine()) != null) {
                // 对数据进行处理.
                String[] splitValue = singleContent.split("\\,");
                result.put(splitValue[0].substring(prefix.length(), prefix.length() + 6), new BigDecimal(splitValue[3]));
            }
            return result;
        } catch (Exception e) {
            log.error("获取股票{} 当前 价格 出错", fullCodeList, e);
            return Collections.emptyMap();
        }
    }

    @Override
    public Map<String, BigDecimal> txGetPrice(List<String> fullCodeList) {
        List<TxStockHistoryInfo> txStockHistoryInfos = parseTxMoneyYesHistory(fullCodeList, new DateTime());
        if (CollectionUtils.isEmpty(txStockHistoryInfos)) {
            return Collections.emptyMap();
        }
        return txStockHistoryInfos.stream().collect(Collectors.toMap(TxStockHistoryInfo::getCode, TxStockHistoryInfo::getNowPrice));
    }

    @Override
    public Map<String, BigDecimal> xueQiuGetPrice(List<String> fullCodeList) {
        //处理，拼接成信息
        String codeListStr = StrUtil.join(",", fullCodeList);
        String xueQiuUrl = "https://stock.xueqiu.com/v5/stock/realtime/quotec.json?symbol={0}&_=" + System.currentTimeMillis();
        String url = MessageFormat.format(xueQiuUrl, codeListStr);
        try {
            String content = HttpUtil.sendGet(httpClient, url, null, "gbk");
            //将内容进行转换，解析
            if (!StringUtils.hasText(content)) {
                return Collections.emptyMap();
            }
            XueQiuResponseStockInfo xueQiuResponseStockInfo = JSON.parseObject(content, XueQiuResponseStockInfo.class);
            if (CollectionUtils.isEmpty(xueQiuResponseStockInfo.getData())) {
                return Collections.emptyMap();
            }

            Map<String, BigDecimal> result = new HashMap<>();

            for (XueQiuStockInfo xueQiuStockInfo : xueQiuResponseStockInfo.getData()) {
                result.put(xueQiuStockInfo.getSymbol().substring(xueQiuStockInfo.getSymbol().length() - 6),
                        new BigDecimal(xueQiuStockInfo.getCurrent()));
            }
            return result;
        } catch (Exception e) {
            log.error("获取股票最近历史记录 {} 当前信息 列表出错", fullCodeList, e);
            return Collections.emptyMap();
        }
    }

    @Override
    public Map<String, BigDecimal> easyMoneyGetPrice(List<String> codeList) {
        List<StockHistoryCsvInfo> easyMoneyStockHistoryCsvInfos = parseEasyMoneyYesHistory(codeList, new DateTime());

        if (CollectionUtils.isEmpty(easyMoneyStockHistoryCsvInfos)) {
            return Collections.emptyMap();
        }
        return easyMoneyStockHistoryCsvInfos.stream().collect(Collectors.toMap(StockHistoryCsvInfo::getCode, StockHistoryCsvInfo::getNowPrice));
    }


    @Override
    public Map<String, BigDecimal> souHuGetPrice(List<String> fullCodeList) {
        String wangYiUrl = "http://q.stock.sohu.com/hisHq?code={0}&start={1}&end={2}";
        //处理，拼接成信息
        String codeListStr = StrUtil.join(",", fullCodeList);
        String timeStr = DateUtil.format(DateUtil.date(), DatePattern.PURE_DATE_PATTERN);
        String url = MessageFormat.format(wangYiUrl, codeListStr, timeStr, timeStr);
        try {
            String content = HttpUtil.sendGet(httpClient, url, null, "gbk");
            //将内容进行转换，解析
            if (!StringUtils.hasText(content)) {
                return Collections.emptyMap();
            }
            //将内容直接按照 ,号进行拆分
            BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(content.getBytes(Charset.forName("utf8"))), Charset.forName("utf8")));
            Map<String, BigDecimal> result = new HashMap<>();
            String prefix = "var hq_str_sz";
            String singleContent = "";
            while ((singleContent = br.readLine()) != null) {
                // 对数据进行处理.
                String[] splitValue = singleContent.split("\\,");
                result.put(splitValue[0].substring(prefix.length(), prefix.length() + 6), new BigDecimal(splitValue[3]));
            }
            return result;
        } catch (Exception e) {
            log.error("获取股票最近历史记录 {} 当前信息 列表出错", fullCodeList, e);
            return Collections.emptyMap();
        }
    }

    @Override
    public HotStockInfo hotMapList(Date date) {
        //处理，拼接成信息
        String time = DateUtil.format(Optional.ofNullable(date).orElse(DateUtil.date()), DatePattern.NORM_DATE_PATTERN);
        String url = MessageFormat.format(extendProperties.getHotMapUrl(), time);
        try {
            //获取内容
            Map<String, String> header = new HashMap<>();
            header.put("Referer", "https://26d3254z77.zicp.fun");
            String content = HttpUtil.sendGet(httpClient, url, header);


            //将内容进行转换，解析
            if (!StringUtils.hasText(content)) {
                return null;
            }

            Map<String, List<String>> resultMap = new HashMap<>();
            JSONArray arrays = JSON.parseArray(content);
            for (int i = 0; i < arrays.size(); i++) {
                //用toJavaObject toJavaObject
                Map<String, List<String>> dataMap = JSONObject.parseObject(arrays.getJSONObject(i).toJSONString(), new TypeReference<Map<String, List<String>>>() {
                });
                resultMap.putAll(dataMap);
            }
            return new HotStockInfo(resultMap);
        } catch (Exception e) {
            log.error("获取 {} 热点行情出错", time, e);
            return null;
        }
    }

    @Override
    public List<StockHistoryCsvInfo> parseEasyMoneyYesHistory(List<String> codeList, DateTime beforeLastWorking) {
        //处理，拼接成信息
        String url = defaultProperties.getEasyMoneyHistoryUrl();
        try {
            //获取内容
            String content = HttpUtil.sendGet(httpClient, url);
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


    /**
     * [
     * {
     * "symbol": "sz001322",
     * "name": "箭牌家居",
     * "ticktime": "09:25:00",
     * "price": "15.020",
     * "volume": "619700",
     * "prev_price": "0.000",
     * "kind": "D"
     * },
     * {
     * "symbol": "sz001322",
     * "name": "箭牌家居",
     * "ticktime": "09:30:00",
     * "price": "15.000",
     * "volume": "241200",
     * "prev_price": "15.020",
     * "kind": "D"
     * }
     * ]
     */
    @Override
    public List<StockBigDealInfo> parseBigDealByCode(String fullCode, Integer minVolume, String day) {
        //处理，拼接成信息
        // 定义一个 Map 参数信息
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("symbol", fullCode);
        paramMap.put("num", "100");
        paramMap.put("sort", "ticktime");
        paramMap.put("asc", "1");
        paramMap.put("volume", minVolume + "");
        paramMap.put("amount", "0");
        paramMap.put("type", "0");
        paramMap.put("day", day);
        // 处理url 进行访问
        String url = "https://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/CN_Bill.GetBillList";

        //获取内容
        Map<String, String> header = new HashMap<>();
        header.put("Referer", "https://vip.stock.finance.sina.com.cn/quotes_service/view/vMS_tradedetail.php?symbol=" + fullCode);
        header.put("Host", "vip.stock.finance.sina.com.cn");

        boolean stopGetBigDeal;

        List<StockBigDealInfo> result = new ArrayList<>();
        try {
            int page = 1;
            do {
                paramMap.put("page", String.valueOf(page));

                String queryUrl = QueryParamUtil.getUrlWithQueryString(url, paramMap);
                log.info(">>>访问地址: {}", queryUrl);
                String content = HttpUtil.sendGet(httpClient, queryUrl, header, "gbk");
                // 表示没有数据了
                stopGetBigDeal = StringUtils.isEmpty(content) || content.length() < 20;

                if (!stopGetBigDeal) {

                    JSONArray arrays = JSON.parseArray(content);
                    List<StockBigDealInfo> allList = new ArrayList<>();
                    for (int i = 0; i < arrays.size(); i++) {
                        //用toJavaObject toJavaObject
                        StockBigDealInfo u = JSON.toJavaObject(arrays.getJSONObject(i), StockBigDealInfo.class);
                        allList.add(u);
                    }
                    result.addAll(allList);
                }
                page = page + 1;
                // 休眠 3s钟，避免被检测到。
                TimeUnit.SECONDS.sleep(3);
            } while (!stopGetBigDeal);
            //将内容进行转换，解析
            return result;
        } catch (Exception e) {
            log.error("获取股票当前的大交易信息失败", e);
            return null;
        }
    }
}
