package top.yueshushu.learn.assembler;

import org.mapstruct.Mapper;
import top.yueshushu.learn.domain.TradePositionHistoryDo;
import top.yueshushu.learn.entity.TradePositionHistory;
import top.yueshushu.learn.entity.TradePositionHistoryCache;
import top.yueshushu.learn.mode.vo.TradePositionHistoryVo;

/**
 * @Description 历史记录持仓转换器
 * @Author yuejianli
 * @Date 2022/5/20 23:01
 **/
@Mapper(componentModel = "spring" )
public interface TradePositionHistoryAssembler {
    /**
     * 历史持仓记录 domain 转换成实体entity
     * @param tradePositionHistoryDo 历史持仓记录Do
     * @return 历史持仓记录 domain 转换成实体entity
     */
    TradePositionHistory doToEntity(TradePositionHistoryDo tradePositionHistoryDo);

    /**
     * 历史持仓记录 entity 转换成 domain
     * @param tradePositionHistory 历史持仓记录
     * @return 历史持仓记录 entity 转换成 domain
     */
    TradePositionHistoryDo entityToDo(TradePositionHistory tradePositionHistory);

    /**
     * 历史持仓记录 entity 转换成 vo
     *
     * @param tradePositionHistory 历史持仓记录
     * @return 历史持仓记录 entity 转换成 vo
     */
    TradePositionHistoryVo entityToVo(TradePositionHistory tradePositionHistory);

    /**
     * 历史持仓记录 domain 转换成实体entity
     *
     * @param tradePositionHistoryDo 历史持仓记录Do
     * @return 历史持仓记录 domain 转换成实体entity
     */
    TradePositionHistoryCache doToCache(TradePositionHistoryDo tradePositionHistoryDo);
}
