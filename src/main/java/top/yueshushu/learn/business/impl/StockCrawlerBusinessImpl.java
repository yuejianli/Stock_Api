package top.yueshushu.learn.business.impl;


import org.springframework.stereotype.Service;
import top.yueshushu.learn.business.StockCrawlerBusiness;
import top.yueshushu.learn.crawler.crawler.ExtCrawlerService;
import top.yueshushu.learn.crawler.entity.DBStockInfo;
import top.yueshushu.learn.crawler.entity.StockBKStockInfo;
import top.yueshushu.learn.enumtype.DBStockType;
import top.yueshushu.learn.mode.info.StockShowInfo;
import top.yueshushu.learn.mode.ro.StockRo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.service.StockCrawlerService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description 爬虫编排层
 * @Author 岳建立
 * @Date 2021/11/12 23:07
 **/
@Service

public class StockCrawlerBusinessImpl implements StockCrawlerBusiness {
    @Resource
    private StockCrawlerService stockCrawlerService;
    @Resource
    private ExtCrawlerService extCrawlerService;

    @Override
    public OutputResult<StockShowInfo> getStockInfo(StockRo stockRo) {
        return stockCrawlerService.getStockInfo(stockRo);
    }

    @Override
    public OutputResult<String> getStockKline(StockRo stockRo) {
        return stockCrawlerService.getStockKline(stockRo);
    }

    @Override
    public OutputResult<String> stockAsync(StockRo stockRo) {
        return stockCrawlerService.stockAsync(stockRo);
    }

    @Override
    public OutputResult stockHistoryAsync(StockRo stockRo) {
        return stockCrawlerService.stockHistoryAsync(stockRo);
    }

    @Override
    public void updateCodePrice(String code) {
        stockCrawlerService.updateCodePrice(code);
    }

    @Override
    public OutputResult<List<DBStockInfo>> dbList(DBStockType dbStockType) {
        return OutputResult.buildSucc(extCrawlerService.findDbStock(dbStockType));
    }

    @Override
    public OutputResult<List<DBStockInfo>> willDbList(DBStockType dbStockType) {
        return OutputResult.buildSucc(extCrawlerService.findWillDbStockList(dbStockType));
    }

    @Override
    public OutputResult<List<StockBKStockInfo>> listRelationBk(String code) {
        return OutputResult.buildSucc(extCrawlerService.findRelationBkListByCode(code));
    }
}
