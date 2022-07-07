package top.yueshushu.learn.business;

import top.yueshushu.learn.mode.ro.TradeMoneyRo;
import top.yueshushu.learn.response.OutputResult;

/**
 * <p>
 * 查询金额记录历史表 自定义的
 * </p>
 *
 * @author 岳建立
 * @since 2022-01-02
 */
public interface TradeMoneyHistoryBusiness {
	/**
	 * 查询金额记录历史表
	 *
	 * @param tradeMoneyRo 金额记录对象
	 * @return 金额记录对象
	 */
	OutputResult listHistory(TradeMoneyRo tradeMoneyRo);
}
