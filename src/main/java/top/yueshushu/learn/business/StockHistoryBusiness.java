package top.yueshushu.learn.business;

import top.yueshushu.learn.mode.ro.StockDayStatRo;
import top.yueshushu.learn.mode.ro.StockRo;
import top.yueshushu.learn.response.OutputResult;

/**
 * @Description stock 股票历史 的编排层处理
 * @Author 岳建立
 * @Date 2022/5/20 22:33
 **/
public interface StockHistoryBusiness {
    /**
     * 查询股票的历史记录信息
     * @param stockRo 股票历史的搜索对象
     * @return 查询股票的历史记录信息
     */
    OutputResult listHistory(StockRo stockRo);
    /**
     * 查询股票的天范围的历史记录信息
     * @param stockDayStatRo 股票历史的搜索对象
     * @return 查询股票的历史记录信息
     */
    OutputResult listDayRange(StockDayStatRo stockDayStatRo);
}
