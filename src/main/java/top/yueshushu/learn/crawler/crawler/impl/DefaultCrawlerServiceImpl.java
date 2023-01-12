package top.yueshushu.learn.crawler.crawler.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import top.yueshushu.learn.common.SystemConst;
import top.yueshushu.learn.crawler.crawler.CrawlerService;
import top.yueshushu.learn.crawler.entity.*;
import top.yueshushu.learn.crawler.parse.DailyTradingInfoParse;
import top.yueshushu.learn.crawler.parse.StockInfoParser;
import top.yueshushu.learn.crawler.parse.StockShowInfoParse;
import top.yueshushu.learn.crawler.properties.DefaultProperties;
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

    @Resource(name = "restTemplate")
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
    public StockShowInfo getNowInfo(String fullCode) {
        //处理，拼接成信息
        String url = MessageFormat.format(defaultProperties.getShowDayUrl(), fullCode);
        log.info(">>>访问地址:" + url);
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
    public BigDecimal sinaGetPrice(String fullCode) {
        //处理，拼接成信息
        String url = MessageFormat.format(defaultProperties.getShowDayUrl(), fullCode);
        //  log.info(">>>访问地址:" + url);
        try {
            //获取内容
            //获取内容
            Map<String, String> header = new HashMap<>();
            header.put("Referer", "https://finance.sina.com.cn");
            String content = HttpUtil.sendGet(httpClient, url, header, "gbk");
            //将内容直接按照 ,号进行拆分
            return new BigDecimal(content.split("\\,")[3]);
        } catch (Exception e) {
            log.error("获取股票{} 当前 价格 出错", fullCode, e);
            return SystemConst.DEFAULT_EMPTY;
        }
    }

    @Override
    public BigDecimal txGetPrice(String fullCode) {
        List<TxStockHistoryInfo> txStockHistoryInfos = parseTxMoneyYesHistory(Collections.singletonList(fullCode), new DateTime());
        if (CollectionUtils.isEmpty(txStockHistoryInfos)) {
            return SystemConst.DEFAULT_EMPTY;
        }
        return txStockHistoryInfos.get(0).getNowPrice();
    }

    @Override
    public BigDecimal xueQiuGetPrice(String fullCode) {
        String xueQiuUrl = "https://stock.xueqiu.com/v5/stock/realtime/quotec.json?symbol={0}&_=" + System.currentTimeMillis();
        String url = MessageFormat.format(xueQiuUrl, fullCode);
        try {
            String content = HttpUtil.sendGet(httpClient, url, null, "gbk");
            //将内容进行转换，解析
            if (!StringUtils.hasText(content)) {
                return SystemConst.DEFAULT_EMPTY;
            }
            XueQiuResponseStockInfo xueQiuResponseStockInfo = JSON.parseObject(content, XueQiuResponseStockInfo.class);
            if (CollectionUtils.isEmpty(xueQiuResponseStockInfo.getData())) {
                return SystemConst.DEFAULT_EMPTY;
            }
            return new BigDecimal(xueQiuResponseStockInfo.getData().get(0).getCurrent());
        } catch (Exception e) {
            log.error("获取股票最近历史记录 {} 当前信息 列表出错", fullCode, e);
            return SystemConst.DEFAULT_EMPTY;
        }
    }

    @Override
    public BigDecimal easyMoneyGetPrice(String code) {
        List<StockHistoryCsvInfo> easyMoneyStockHistoryCsvInfos = parseEasyMoneyYesHistory(Collections.singletonList(code), new DateTime());

        if (CollectionUtils.isEmpty(easyMoneyStockHistoryCsvInfos)) {
            return SystemConst.DEFAULT_EMPTY;
        }
        return easyMoneyStockHistoryCsvInfos.get(0).getNowPrice();
    }

    @Override
    public BigDecimal wangYiGetPrice(String fullCode) {
        String wangYiUrl = "http://quotes.money.163.com/service/chddata.html?code={0}&start={1}&end={2}";
        String timeStr = DateUtil.format(DateUtil.date(), DatePattern.PURE_DATE_PATTERN);
        String url = MessageFormat.format(wangYiUrl, fullCode, timeStr, timeStr);
        try {
            String content = HttpUtil.sendGet(httpClient, url, null, "gbk");
            //将内容进行转换，解析
            if (!StringUtils.hasText(content)) {
                return SystemConst.DEFAULT_EMPTY;
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(content.getBytes(Charset.forName("utf8"))), Charset.forName("utf8")));
            br.readLine();
            //读取第二行的数据才是真正想要的数据信息。
            String line = br.readLine();
            String nowPriceStr = line.split("\\,")[3];
            return new BigDecimal(Optional.ofNullable(nowPriceStr).orElse("0.00"));
        } catch (Exception e) {
            log.error("获取股票最近历史记录 {} 当前信息 列表出错", fullCode, e);
            return SystemConst.DEFAULT_EMPTY;
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
