package top.yueshushu.learn.domainservice;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.extension.service.IService;
import top.yueshushu.learn.domain.TradeMoneyHistoryDo;

import java.util.List;

/**
 * @Description 持仓金额历史的操作
 * @Author yuejianli
 * @Date 2022/5/20 23:23
 **/
public interface TradeMoneyHistoryDomainService extends IService<TradeMoneyHistoryDo> {
	/**
	 * 根据股票的编码和时间范围搜索对应的持仓金额历史记录
	 *
	 * @param userId    用户编号
	 * @param mockType  类型
	 * @param startDate 开始日期
	 * @param endDate   结束日期
	 * @return 根据股票的编码和时间范围搜索对应的持仓历史记录
	 */
	List<TradeMoneyHistoryDo> listMoneyHistory(Integer userId, Integer mockType, DateTime startDate, DateTime endDate);

	/**
	 * 删除当天的已经保存的历史金额记录信息
	 *
	 * @param userId   用户编号
	 * @param mockType 交易类型
	 * @param currDate 当前时间
	 */
	void deleteByUserIdAndMockTypeAndDate(Integer userId, Integer mockType, DateTime currDate);

	/**
	 * 获取今天之前的 已亏损金额信息
	 *
	 * @param userId   用户编号
	 * @param mockType 交易类型
	 * @param currDate 当前时间
	 * @return 获取今天之前的 已亏损金额信息
	 */
	TradeMoneyHistoryDo getLastRecordProfit(Integer userId, Integer mockType, DateTime currDate);
}
