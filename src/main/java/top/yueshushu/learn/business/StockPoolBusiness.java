package top.yueshushu.learn.business;

import top.yueshushu.learn.mode.ro.StockPoolRo;
import top.yueshushu.learn.mode.vo.DistVo;
import top.yueshushu.learn.mode.vo.ten10stat.StockRelationVo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.response.PageResponse;

import java.util.Date;
import java.util.List;

/**
 * 股票池处理
 *
 * @author Yue Jianli
 * @date 2023-02-10
 */

public interface StockPoolBusiness {
    /**
     * 处理股票池信息
     *
     * @param date 日期
     */
    void handlerPool(Date date);

    /**
     * 查询股票池类型
     */
    OutputResult<List<DistVo>> listPoolType();

    /**
     * 股票池统计信息
     *
     * @param stockPoolRo 股票池
     */
    OutputResult<PageResponse<StockRelationVo>> listPool(StockPoolRo stockPoolRo);
}
