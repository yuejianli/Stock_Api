package top.yueshushu.learn.crawler.parse;
import top.yueshushu.learn.crawler.entity.*;
import top.yueshushu.learn.enumtype.DBStockType;

import java.util.Collections;
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

    ;

    default List<BKInfo> parseBkInfoList(String content) {
        return Collections.EMPTY_LIST;
    }

    default List<BKMoneyInfo> parseTodayBKMoneyInfoList(String content) {
        return Collections.EMPTY_LIST;
    }

    default List<DBStockInfo> parseDbStockInfoList(String content, DBStockType dbStockType) {
        return Collections.EMPTY_LIST;
    }

    default List<StockBKStockInfo> parseBkStockList(String content, String code) {
        return Collections.EMPTY_LIST;
    }
}
