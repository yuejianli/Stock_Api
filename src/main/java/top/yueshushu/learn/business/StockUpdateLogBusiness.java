package top.yueshushu.learn.business;

import org.springframework.stereotype.Service;
import top.yueshushu.learn.entity.Stock;
import top.yueshushu.learn.mode.ro.StockDayStatRo;
import top.yueshushu.learn.mode.ro.StockRo;
import top.yueshushu.learn.mode.ro.StockUpdateLogRo;
import top.yueshushu.learn.response.OutputResult;

import javax.annotation.Resource;

/**
 * @Description StockUpdateLogBusiness 股票更新日志
 * @Author 岳建立
 * @Date 2022/5/20 22:33
 **/

public interface StockUpdateLogBusiness {
    /**
     * 查询股票近一个月的更新记录信息
     * @param stockUpdateLogRo 股票更新日志
     * @return 查询股票近一个月的更新记录信息
     */
    OutputResult list(StockUpdateLogRo stockUpdateLogRo);
}
