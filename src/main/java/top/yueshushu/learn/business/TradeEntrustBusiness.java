package top.yueshushu.learn.business;

import top.yueshushu.learn.mode.ro.TradeEntrustRo;
import top.yueshushu.learn.response.OutputResult;

/**
 * <p>
 * 委托信息
 * </p>
 *
 * @author 岳建立
 * @since 2022-01-02
 */
public interface TradeEntrustBusiness {
    /**
     * 查询虚拟的今日委托信息
     * @param tradeEntrustRo 委托对象
     * @return 查询虚拟的今日委托信息
     */
    OutputResult mockList(TradeEntrustRo tradeEntrustRo);

    /**
     * 查询真实的今日委托信息
     * @param tradeEntrustRo 委托对象
     * @return 查询真实的今日委托信息
     */
    OutputResult realList(TradeEntrustRo tradeEntrustRo);
    /**
     * 查询虚拟的历史委托信息
     * @param tradeEntrustRo 委托对象
     * @return 查询虚拟的历史委托信息
     */
    OutputResult mockHistoryList(TradeEntrustRo tradeEntrustRo);
    /**
     * 查询真实的历史委托信息
     * @param tradeEntrustRo 委托对象
     * @return 查询真实的历史委托信息
     */
    OutputResult realHistoryList(TradeEntrustRo tradeEntrustRo);
}
