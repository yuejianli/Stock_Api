package top.yueshushu.learn.assembler;

import org.mapstruct.Mapper;

import top.yueshushu.learn.domain.TradeMoneyDo;
import top.yueshushu.learn.domain.TradeMoneyHistoryDo;
import top.yueshushu.learn.entity.TradeMoneyHistory;
import top.yueshushu.learn.mode.vo.TradeMoneyHistoryVo;

/**
 * @Description 历史金额记录表
 * @Author yuejianli
 * @Date 2022/5/20 23:01
 **/
@Mapper(componentModel = "spring")
public interface TradeMoneyHistoryAssembler {
	/**
	 * 历史持仓金额 domain 转换成实体entity
	 *
	 * @param tradeMoneyHistoryDo 历史持仓金额Do
	 * @return 历史持仓金额 domain 转换成实体entity
	 */
	TradeMoneyHistory doToEntity(TradeMoneyHistoryDo tradeMoneyHistoryDo);
	
	/**
	 * 历史持仓金额 entity 转换成 domain
	 *
	 * @param tradeMoneyHistory 历史持仓金额
	 * @return 历史持仓金额 entity 转换成 domain
	 */
	TradeMoneyHistoryDo entityToDo(TradeMoneyHistory tradeMoneyHistory);
	
	/**
	 * 历史持仓金额 entity 转换成 vo
	 *
	 * @param tradeMoneyHistory 历史持仓金额
	 * @return 历史持仓金额 entity 转换成 vo
	 */
	TradeMoneyHistoryVo entityToVo(TradeMoneyHistory tradeMoneyHistory);
	
	/**
	 * 转换成历史
	 *
	 * @param tradeMoneyDo 今日持仓记录
	 */
	TradeMoneyHistoryDo doToHis(TradeMoneyDo tradeMoneyDo);
}
