package top.yueshushu.learn.crawler.parse;
import top.yueshushu.learn.crawler.entity.*;
import top.yueshushu.learn.enumtype.DBStockType;
import top.yueshushu.learn.enumtype.StockPoolType;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 股票信息转换接口
 */
public interface StockInfoParser {
    /**
     * 将content 信息转换成对应的股票实体信息
     *
     * @param content 内容
     * @return 信息转换成对应的股票实体信息
     */
    default List<DownloadStockInfo> parseStockInfoList(String content) {
        return Collections.EMPTY_LIST;
    }

    /**
     * 将版块,概念，地域 解析成相应的内容
     *
     * @param content 内容
     */
    default List<BKInfo> parseBkInfoList(String content) {
        return Collections.EMPTY_LIST;
    }

    /**
     * 将版块,概念，地域的今日资金注入 解析成相应的内容
     *
     * @param content 内容
     */
    default List<BKMoneyInfo> parseTodayBKMoneyInfoList(String content) {
        return Collections.EMPTY_LIST;
    }

    /**
     * 解析打板的数据信息， 获取当前股票市场上的涨停价格和幅度
     *
     * @param content 内容
     */
    default List<DBStockInfo> parseDbStockInfoList(String content, DBStockType dbStockType) {
        return Collections.EMPTY_LIST;
    }

    /**
     * 解析股票关联的概念版块信息
     *
     * @param content 内容
     */
    default List<StockBKStockInfo> parseBkStockList(String content, String code) {
        return Collections.EMPTY_LIST;
    }

    /**
     * 解析股票池信息
     *
     * @param content 内容
     */
    default List<StockPoolInfo> parsePoolInfoList(String content, StockPoolType stockPoolType, Date currentDate) {
        return Collections.EMPTY_LIST;
    }

    /**
     * 解析板块,概念，地域 历史注入信息
     *
     * @param content 内容
     */
    default List<BKMoneyInfo> parseTodayBKMoneyHistoryInfoList(String content) {
        return Collections.EMPTY_LIST;
    }
}
