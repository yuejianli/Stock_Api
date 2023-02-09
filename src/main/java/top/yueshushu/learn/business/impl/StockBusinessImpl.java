package top.yueshushu.learn.business.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.yueshushu.learn.business.StockBusiness;
import top.yueshushu.learn.mode.info.StockInfo;
import top.yueshushu.learn.mode.ro.StockRo;
import top.yueshushu.learn.mode.vo.StockVo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.service.StockService;

import javax.annotation.Resource;

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


    @Override
    public OutputResult<StockInfo> listStock(StockRo stockRo) {
        return stockService.pageStock(stockRo);
    }

    @Override
    public OutputResult<StockVo> getStockInfo(String code) {
        return stockService.getStockInfo(code);
    }
}
