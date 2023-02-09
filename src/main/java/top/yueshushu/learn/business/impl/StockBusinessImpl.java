package top.yueshushu.learn.business.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.yueshushu.learn.business.StockBusiness;
import top.yueshushu.learn.crawler.crawler.ExtCrawlerService;
import top.yueshushu.learn.crawler.entity.DBStockInfo;
import top.yueshushu.learn.enumtype.DBStockType;
import top.yueshushu.learn.mode.info.StockInfo;
import top.yueshushu.learn.mode.ro.StockRo;
import top.yueshushu.learn.mode.vo.StockVo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.service.StockService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description 股票实现编排层
 * @Author yuejianli
 * @Date 2022/5/20 23:54
 **/
@Service
@Slf4j
public class StockBusinessImpl implements StockBusiness {
    @Resource
    private StockService stockService;
    @Resource
    private ExtCrawlerService extCrawlerService;


    @Override
    public OutputResult<StockInfo> listStock(StockRo stockRo) {
        return stockService.pageStock(stockRo);
    }

    @Override
    public OutputResult<StockVo> getStockInfo(String code) {
        return stockService.getStockInfo(code);
    }

    @Override
    public OutputResult<List<DBStockInfo>> dbList(DBStockType dbStockType) {
        return OutputResult.buildSucc(extCrawlerService.findDbStock(dbStockType));
    }

    @Override
    public OutputResult<List<DBStockInfo>> willDbList(DBStockType dbStockType) {
        return OutputResult.buildSucc(extCrawlerService.findWillDbStockList(dbStockType));
    }
}
