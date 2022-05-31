package top.yueshushu.learn.business;

import top.yueshushu.learn.mode.ro.TradePositionRo;
import top.yueshushu.learn.response.OutputResult;

/**
 * <p>
 * 持仓记录表
 * </p>
 *
 * @author 岳建立
 * @since 2022-01-02
 */
public interface TradePositionBusiness {
    /**
     * 查询虚拟的股票当前的持仓记录信息
     * @param tradePositionRo 股票持仓对象
     * @return 查询虚拟的股票当前的持仓记录信息
     */
    OutputResult mockList(TradePositionRo tradePositionRo);
    /**
     * 查询真实的股票当前的持仓记录信息
     * @param tradePositionRo 股票持仓对象
     * @return 查询真实的股票当前的持仓记录信息
     */
    OutputResult realList(TradePositionRo tradePositionRo);
}
