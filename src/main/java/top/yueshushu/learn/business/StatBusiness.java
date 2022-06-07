package top.yueshushu.learn.business;

import top.yueshushu.learn.mode.ro.StatTen10Ro;
import top.yueshushu.learn.mode.ro.StockStatRo;
import top.yueshushu.learn.response.OutputResult;

import java.util.List;

/**
 * @Description 统计的编排层处理
 * @Author 岳建立
 * @Date 2022/5/20 22:33
 **/
public interface StatBusiness {
    /**
     * 周统计信息
     * @param stockStatRo 股票统计对象
     * @return 周统计信息
     */
    OutputResult getWeekStat(StockStatRo stockStatRo);

    /**
     * 图表统计信息
     *
     * @param stockStatRo 股票统计对象
     * @return 图表统计信息
     */
    OutputResult getCharStat(StockStatRo stockStatRo);

    /**
     * 获取最近十天的交易日信息
     *
     * @return 获取最近十天的交易日信息
     */
    OutputResult<List<String>> getTenTradeDay();

    /**
     * 获取最近十天的交易日自选表统计信息
     *
     * @return 获取最近十天的交易日自选表统计信息
     */
    OutputResult getTenTradeData(StatTen10Ro statTen10Ro);
}