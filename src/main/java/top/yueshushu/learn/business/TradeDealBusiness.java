package top.yueshushu.learn.business;

import top.yueshushu.learn.mode.ro.TradeDealRo;
import top.yueshushu.learn.mode.vo.TradeDealVo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.response.PageResponse;

import java.util.List;

/**
 * <p>
 * 成交信息
 * </p>
 *
 * @author 岳建立
 * @since 2022-01-02
 */
public interface TradeDealBusiness {
    /**
     * 查询虚拟的今日成交信息
     *
     * @param tradeDealRo 成交对象
     * @return 查询虚拟的今日成交信息
     */
    OutputResult<PageResponse<TradeDealVo>> mockList(TradeDealRo tradeDealRo);

    /**
     * 查询真实的今日成交信息
     *
     * @param tradeDealRo 成交对象
     * @return 查询真实的今日成交信息
     */
    OutputResult<List<TradeDealVo>> realList(TradeDealRo tradeDealRo);

    /**
     * 查询虚拟的历史成交信息
     *
     * @param tradeDealRo 成交对象
     * @return 查询虚拟的历史成交信息
     */
    OutputResult<PageResponse<TradeDealVo>> mockHistoryList(TradeDealRo tradeDealRo);

    /**
     * 查询真实的历史成交信息
     *
     * @param tradeDealRo 成交对象
     * @return 查询真实的历史成交信息
     */
    OutputResult<PageResponse<TradeDealVo>> realHistoryList(TradeDealRo tradeDealRo);
}
