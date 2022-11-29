package top.yueshushu.learn.business.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.yueshushu.learn.business.StockHistoryBusiness;
import top.yueshushu.learn.common.ResultCode;
import top.yueshushu.learn.mode.ro.StockDayStatRo;
import top.yueshushu.learn.mode.ro.StockRo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.service.StockHistoryService;
import top.yueshushu.learn.service.cache.StockCacheService;

import javax.annotation.Resource;

/**
 * @Description 股票历史实现编排层
 * @Author yuejianli
 * @Date 2022/5/20 23:54
 **/
@Service
@Slf4j
public class StockHistoryBusinessImpl implements StockHistoryBusiness {
    @Resource
    private StockHistoryService stockHistoryService;
    @Resource
    private StockCacheService stockCacheService;

    @Override
    public OutputResult listHistory(StockRo stockRo) {
        if (stockCacheService.selectByCode(stockRo.getCode()) == null) {
            return OutputResult.buildAlert(
                    ResultCode.STOCK_CODE_NO_EXIST
            );
        }
        return stockHistoryService.pageHistory(stockRo);
    }

    @Override
    public OutputResult listDayRange(StockDayStatRo stockDayStatRo) {
        if (stockCacheService.selectByCode(stockDayStatRo.getCode()) == null) {
            return OutputResult.buildAlert(
                    ResultCode.STOCK_CODE_NO_EXIST
            );
        }
        return stockHistoryService.pageDayRange(stockDayStatRo);
    }
}
