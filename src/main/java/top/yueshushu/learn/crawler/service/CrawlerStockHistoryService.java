package top.yueshushu.learn.crawler.service;

import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.mode.ro.StockRo;

/**
 * @ClassName:StockHistoryService
 * @Description TODO
 * @Author 岳建立
 * @Date 2021/11/14 11:24
 * @Version 1.0
 **/
public interface CrawlerStockHistoryService {
    /**
     * 股票历史记录同步
     * @param stockRo 股票对象
     * @return 股票历史记录同步
     */
    OutputResult stockCrawlerHistoryAsync(StockRo stockRo);
}
