package top.yueshushu.learn.service;

import top.yueshushu.learn.mode.ro.TradeMoneyRo;
import top.yueshushu.learn.response.OutputResult;

/**
 * <p>
 * 持仓历史记录表 自定义的
 * </p>
 *
 * @author 岳建立
 * @since 2022-01-02
 */
public interface TradeMoneyHistoryService {
	/**
	 * 查询我的金额历史记录
	 *
	 * @param tradeMoneyRo 查询我的金额历史记录
	 * @return 查询我的金额历史记录
	 */
	OutputResult pageHistory(TradeMoneyRo tradeMoneyRo);
}
