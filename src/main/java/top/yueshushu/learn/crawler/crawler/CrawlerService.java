package top.yueshushu.learn.crawler.crawler;

import top.yueshushu.learn.crawler.entity.DownloadStockInfo;
import top.yueshushu.learn.crawler.entity.StockHistoryCsvInfo;
import top.yueshushu.learn.mode.info.StockShowInfo;

import java.util.Collections;
import java.util.List;

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
     * @param code 股票编码
     * @return 获取该股票展示的数据
     */
     default StockShowInfo getNowInfo(String code) {
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
     * @param fullCode 股票的全编码
     * @return 新浪获取股票当前的价格
     */
    String sinaGetPrice(String fullCode);
}
