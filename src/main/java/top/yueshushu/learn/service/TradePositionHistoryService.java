package top.yueshushu.learn.service;

import top.yueshushu.learn.entity.User;
import top.yueshushu.learn.mode.ro.TradePositionRo;
import top.yueshushu.learn.mode.ro.UserRo;
import top.yueshushu.learn.response.OutputResult;

/**
 * <p>
 * 持仓历史记录表 自定义的
 * </p>
 *
 * @author 岳建立
 * @since 2022-01-02
 */
public interface TradePositionHistoryService {
    /**
     * 查询我的持仓历史记录
     * @param tradePositionRo 持仓历史记录对象
     * @return 返回我的持仓历史记录表
     */
    OutputResult pageHistory(TradePositionRo tradePositionRo);
}
