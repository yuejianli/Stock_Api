package top.yueshushu.learn.crawler.business;

import top.yueshushu.learn.crawler.entity.StockBigDealInfo;
import top.yueshushu.learn.mode.ro.StockRo;
import top.yueshushu.learn.response.OutputResult;

import java.util.List;

/**
 * @Description stock 股票 的编排层处理
 * @Author 岳建立
 * @Date 2022/5/20 22:33
 **/
public interface CrawlerStockBusiness {
    /**
     * 根据股票的编码获取当前的股票信息
     *
     * @param stockCode 股票编码
     * @return 根据股票的编码获取当前的股票信息
     */
    OutputResult getStockInfo(String stockCode);

    /**
     * 根据股票的编码和K线图类型获取当前的K线图
     *
     * @param stockRo 股票对象
     * @return 根据股票的编码和K线图类型获取当前的K线图
     */
    OutputResult getStockKline(StockRo stockRo);

    /**
     * 股票信息全量同步
     *
     * @param stockRo 股票对象
     * @return 股票信息全量同步
     */
    OutputResult stockAsync(StockRo stockRo);

    /**
     * 获取股票的价格
     *
     * @param fullCode 股票的全编码
     * @return 获取股票的价格
     */
    OutputResult getStockPrice(String fullCode);

    /**
     * 获取大宗交易信息
     *
     * @param fullCode  股票全编码
     * @param minVolume 最低量
     * @param day
     */
    OutputResult<List<StockBigDealInfo>> getBigDealList(String fullCode, Integer minVolume, String day);
}
