package top.yueshushu.learn.business.impl;

import org.springframework.stereotype.Service;
import top.yueshushu.learn.business.StockUpdateLogBusiness;
import top.yueshushu.learn.mode.ro.StockUpdateLogRo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.service.StockUpdateLogService;

import javax.annotation.Resource;

/**
 * @Description 股票更新日志
 * @Author yuejianli
 * @Date 2022/6/4 10:12
 **/
@Service
public class StockUpdateLogBusinessImpl implements StockUpdateLogBusiness {
    @Resource
    private StockUpdateLogService stockUpdateLogService;

    @Override
    public OutputResult list(StockUpdateLogRo stockUpdateLogRo) {
        return stockUpdateLogService.pageLastMonth(stockUpdateLogRo);
    }
}
