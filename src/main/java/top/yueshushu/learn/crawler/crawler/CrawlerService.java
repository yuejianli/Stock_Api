package top.yueshushu.learn.crawler.crawler;

import cn.hutool.core.date.DateTime;
import top.yueshushu.learn.crawler.entity.*;
import top.yueshushu.learn.mode.info.StockShowInfo;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description 爬虫的相关service接口
 * @Author 岳建立
 * @Date 2021/11/7 10:37
 **/
public interface CrawlerService {
    /**
     * 获取股票的全量列表信息
     * @return 获取股票的全量列表信息
     */
    default List<DownloadStockInfo> getStockList() {
        return Collections.EMPTY_LIST;
    }

    /**
     * 获取该股票这期间内的历史数据
     * @param code 股票编码
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 获取该股票这期间内的历史数据
     */
    default List<StockHistoryCsvInfo> parseStockHistoryList(String code,
                                                            String startDate,
                                                            String endDate) {
        return Collections.EMPTY_LIST;
    }

    /**
     * 获取该股票展示的数据
     *
     * @param fullCode 股票编码
     * @return 获取该股票展示的数据
     */
    default StockShowInfo getNowInfo(String fullCode) {
        return new StockShowInfo();
    }

    /**
     * 获取股票分钟的K线
     * @param code 股票编码
     * @return 获取股票分钟的K线
     */
    default String getMinUrl(String code) {
        return "";
    }

    /**
     * 获取股票 天的K线
     * @param code 股票编码
     * @return 获取股票 天 的K线
     */
    default String getDayUrl(String code) {
        return "";
    }

    /**
     * 获取股票 周的K线
     * @param code 股票编码
     * @return 获取股票 周 的K线
     */
    default String getWeekUrl(String code) {
        return "";
    }

    /**
     * 获取股票 月 的K线
     * @param code 股票编码
     * @return 获取股票 月 的K线
     */
    public default String getMonthUrl(String code) {
        return "";
    }

    /**
     * 新浪获取股票当前的价格
     *
     * @param fullCodeList 股票的全编码
     * @return 新浪获取股票当前的价格
     */
    Map<String, BigDecimal> sinaGetPrice(List<String> fullCodeList);

    /**
     * 腾讯获取当前股票的价格
     *
     * @param fullCodeList 股票代码
     * @return
     */
    Map<String, BigDecimal> txGetPrice(List<String> fullCodeList);

    /**
     * 东方财富，同步股票交易信息
     *
     * @param codeList          股票编码集合
     * @param beforeLastWorking 最近的一个工作日
     * @return 东方财富，同步股票交易信息
     */
    List<StockHistoryCsvInfo> parseEasyMoneyYesHistory(List<String> codeList, DateTime beforeLastWorking);

    /**
     * 腾讯财富，同步股票交易信息
     *
     * @param codeList          股票编码集合
     * @param beforeLastWorking 最近的一个工作日
     * @return 腾讯接口, 解析历史数据。
     */
    List<TxStockHistoryInfo> parseTxMoneyYesHistory(List<String> codeList, DateTime beforeLastWorking);

    /**
     * 新浪财富,获取大单数据
     *
     * @param fullCode  股票编码
     * @param minVolume 最小的数量(股数)
     * @param day       日期, 形式是  2022-11-23
     * @return 新浪财富, 获取大单数据
     */
    List<StockBigDealInfo> parseBigDealByCode(String fullCode, Integer minVolume, String day);

    /**
     * 雪球获取当前股票的价格信息
     *
     * @param fullCodeList 股票编码，是大写的
     * @return
     */
    Map<String, BigDecimal> xueQiuGetPrice(List<String> fullCodeList);

    /**
     * 东方财富获取当前股票的价格信息
     *
     * @param codeList 股票编码
     * @return
     */
    Map<String, BigDecimal> easyMoneyGetPrice(List<String> codeList);

    /**
     * 搜狐获取当前股票的价格信息
     *
     * @param fullCodeList 股票编码
     * @return
     */
    Map<String, BigDecimal> souHuGetPrice(List<String> fullCodeList);

    /**
     * 根据日期爬取热力图
     *
     * @param date 日期
     */
    HotStockInfo hotMapList(Date date);
}
