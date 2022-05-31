package top.yueshushu.learn.business;

import top.yueshushu.learn.mode.ro.TradePositionRo;
import top.yueshushu.learn.response.OutputResult;

/**
 * <p>
 * 持仓历史记录表 自定义的
 * </p>
 *
 * @author 岳建立
 * @since 2022-01-02
 */
public interface TradePositionHistoryBusiness {
    /**
     * 查询持仓记录历史表
     * @param tradePositionRo 持仓记录对象
     * @return 查询持仓记录历史表
     */
    OutputResult listHistory(TradePositionRo tradePositionRo);
}
