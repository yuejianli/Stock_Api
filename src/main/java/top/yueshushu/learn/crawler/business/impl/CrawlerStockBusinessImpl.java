package top.yueshushu.learn.crawler.business.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.yueshushu.learn.crawler.business.CrawlerStockBusiness;
import top.yueshushu.learn.crawler.service.CrawlerStockService;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.mode.ro.StockRo;

import javax.annotation.Resource;

/**
 * @Description 菜单实现编排层
 * @Author yuejianli
 * @Date 2022/5/20 23:54
 **/
@Service
@Slf4j
public class CrawlerStockBusinessImpl implements CrawlerStockBusiness {
    @Resource
    private CrawlerStockService crawlerStockService;

    @Override
    public OutputResult getStockInfo(String stockCode) {
        return crawlerStockService.getCrawlerStockInfoByCode(stockCode);
    }

    @Override
    public OutputResult getStockKline(StockRo stockRo) {
        return crawlerStockService.getCrawlerLine(
                stockRo.getCode(),
                stockRo.getType()
        );
    }

    @Override
    public OutputResult stockAsync(StockRo stockRo) {
        return crawlerStockService.stockAsync(
                stockRo
        );
    }

    @Override
    public OutputResult getStockPrice(String fullCode) {
        return crawlerStockService.getCrawlerPrice(
                fullCode
        );
    }
}
