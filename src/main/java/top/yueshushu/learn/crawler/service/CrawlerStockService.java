package top.yueshushu.learn.crawler.service;

import top.yueshushu.learn.crawler.entity.StockBigDealInfo;
import top.yueshushu.learn.mode.ro.StockRo;
import top.yueshushu.learn.response.OutputResult;

import java.util.List;

/**
 * @InterfaceName StockBaseService
 * @Description TODO
 * @Author 岳建立
 * @Date 2021/11/14 0:04
 * @Version 1.0
 **/
public interface CrawlerStockService {
    /**
     * 根据股票的编码，爬虫获取当前的股票信息
     * @param stockCode 股票编码
     * @return 根据股票的编码，爬虫获取当前的股票信息
     */
    OutputResult getCrawlerStockInfoByCode(String stockCode);

    /**
     * 获取当前股票的爬虫K线图
     *
     * @param code 股票的编码
     * @param type K线图类型
     * @return 获取当前股票的爬虫K线图
     */
    OutputResult getCrawlerLine(String code, Integer type);

    /**
     * 获取当前股票的爬虫K线图
     *
     * @param fullCode 股票的编码
     * @param type     K线图类型
     * @return 获取当前股票的爬虫K线图
     */
    OutputResult getCrawlerLineByFullCode(String fullCode, Integer type);

    /**
     * 股票信息全量同步
     *
     * @param stockRo 股票对象
     * @return 返回股票信息全量同步
     */
    OutputResult stockAsync(StockRo stockRo);

    /**
     * 获取股票的当前价格
     *
     * @param fullCode 股票的编码
     * @return 获取股票的当前价格
     */
    OutputResult getCrawlerPrice(String fullCode);

    /**
     * 获取大宗交易信息
     *
     * @param fullCode  股票全编码
     * @param minVolume 最低量
     * @param day
     */
    OutputResult<List<StockBigDealInfo>> getBigDealList(String fullCode, Integer minVolume, String day);
}
