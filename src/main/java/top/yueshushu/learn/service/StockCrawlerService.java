package top.yueshushu.learn.service;

import top.yueshushu.learn.mode.info.StockShowInfo;
import top.yueshushu.learn.mode.ro.StockRo;
import top.yueshushu.learn.response.OutputResult;

/**
 * @ClassName:StockService
 * @Description TODO
 * @Author 岳建立
 * @Date 2021/11/12 23:06
 * @Version 1.0
 **/
public interface StockCrawlerService {


    /**
     * 获取股票的相关信息
     * @param stockRo
     * @return
     */
    OutputResult<StockShowInfo> getStockInfo(StockRo stockRo);

    /**
     * 查看股票的K线
     * @param stockRo
     * @return
     */
    OutputResult<String> getStockKline(StockRo stockRo);

    /**
     * 股票同步
     * @param stockRo
     * @return
     */
    OutputResult<String> stockAsync(StockRo stockRo);

    /**
     * 同步股票的历史记录
     * @param stockRo
     * @return
     */
    OutputResult stockHistoryAsync(StockRo stockRo);

    /**
     * 更新股票的当前价格，放置到 redis 缓存里面。
     * @param code
     */
    void updateCodePrice(String code);

}
