package top.yueshushu.learn.crawler.parse;

import top.yueshushu.learn.crawler.entity.StockHistoryCsvInfo;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;

/**
 * @author 12905
 * 股票历史交易记录解析器
 */
public interface DailyTradingInfoParse {
    /**
     * 默认的股票历史交易记录解析器
     * @param inputStream 输入流
     * @return 默认的股票历史交易记录解析器
     */
     default List<StockHistoryCsvInfo> parseStockHistoryList(InputStream inputStream){
        return Collections.EMPTY_LIST;
    };


}
