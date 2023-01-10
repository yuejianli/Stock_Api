package top.yueshushu.learn.business;

import top.yueshushu.learn.entity.User;
import top.yueshushu.learn.enumtype.MockType;
import top.yueshushu.learn.mode.ro.TradePositionRo;
import top.yueshushu.learn.mode.vo.AddPositionVo;
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
     *
     * @param tradePositionRo 股票持仓对象
     * @return 查询真实的股票当前的持仓记录信息
     */
    OutputResult realList(TradePositionRo tradePositionRo);

    /**
     * 记录用户今日的盈亏数
     *
     * @param userId   用户编号
     * @param mockType 类型
     */
    void callProfit(Integer userId, MockType mockType);
    /**
     * 要处理的今日持仓信息
     *
     * @param addPositionVo 要添加的持仓信息
     * @param user          当前用户
     */
    OutputResult addPosition(AddPositionVo addPositionVo, User user);
}
