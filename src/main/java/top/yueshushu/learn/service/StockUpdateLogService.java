package top.yueshushu.learn.service;

import top.yueshushu.learn.mode.ro.StockUpdateLogRo;
import top.yueshushu.learn.response.OutputResult;

/**
 * @Description 股票更新日志历史表
 * @Author yuejianli
 * @Date 2022/6/4 10:10
 **/
public interface StockUpdateLogService {
    /**
     * 查询股票近一个月的更新记录信息
     * @param stockUpdateLogRo 股票更新日志
     * @return 查询股票近一个月的更新记录信息
     */
    OutputResult pageLastMonth(StockUpdateLogRo stockUpdateLogRo);
}
