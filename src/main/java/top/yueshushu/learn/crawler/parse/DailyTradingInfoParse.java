package top.yueshushu.learn.crawler.parse;

import cn.hutool.core.date.DateTime;
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

    /**
     * 东方财富解析最近的工作日的股票记录
     * @param content 内容信息
     * @param codeList 股票编码
     * @param beforeLastWorking 最近的工作日
     * @return 返回记录信息
     */
    default List<StockHistoryCsvInfo> parseEasyMoneyHistory(String content,List<String> codeList,
                                                            DateTime beforeLastWorking){
        return Collections.EMPTY_LIST;
    }
}
