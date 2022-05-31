package top.yueshushu.learn.crawler.business.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.yueshushu.learn.crawler.business.CrawlerStockHistoryBusiness;
import top.yueshushu.learn.crawler.service.CrawlerStockHistoryService;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.mode.ro.StockRo;

import javax.annotation.Resource;

/**
 * @Description 股票实现编排层
 * @Author yuejianli
 * @Date 2022/5/20 23:54
 **/
@Service
@Slf4j
public class CrawlerStockHistoryBusinessImpl implements CrawlerStockHistoryBusiness {
    @Resource
    private CrawlerStockHistoryService stockCrawlerHistoryAsync;

    @Override
    public OutputResult stockHistoryAsync(StockRo stockRo) {
        return stockCrawlerHistoryAsync.stockCrawlerHistoryAsync(
                stockRo
        );
    }
}
