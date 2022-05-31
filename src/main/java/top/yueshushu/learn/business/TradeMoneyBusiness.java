package top.yueshushu.learn.business;

import top.yueshushu.learn.mode.ro.TradeMoneyRo;
import top.yueshushu.learn.response.OutputResult;

/**
 * <p>
 * 持仓金额记录表
 * </p>
 *
 * @author 岳建立
 * @since 2022-01-02
 */
public interface TradeMoneyBusiness {
    /**
     * 查询虚拟的股票当前的持仓金额记录信息
     * @param tradeMoneyRo 股票持仓金额对象
     * @return 查询虚拟的股票当前的持仓金额记录信息
     */
    OutputResult mockInfo(TradeMoneyRo tradeMoneyRo);
    /**
     * 查询真实的股票当前的持仓金额记录信息
     * @param tradeMoneyRo 股票持仓金额对象
     * @return 查询真实的股票当前的持仓金额记录信息
     */
    OutputResult realInfo(TradeMoneyRo tradeMoneyRo);
}
