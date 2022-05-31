package top.yueshushu.learn.assembler;

import org.mapstruct.Mapper;
import top.yueshushu.learn.domain.TradePositionDo;
import top.yueshushu.learn.domain.TradePositionHistoryDo;
import top.yueshushu.learn.entity.TradePosition;
import top.yueshushu.learn.mode.vo.TradePositionVo;

/**
 * @Description 持仓转换器
 * @Author yuejianli
 * @Date 2022/5/20 23:01
 **/
@Mapper(componentModel = "spring" )
public interface TradePositionAssembler {
    /**
     * 持仓 domain 转换成实体entity
     * @param tradePositionDo 持仓Do
     * @return 持仓 domain 转换成实体entity
     */
    TradePosition doToEntity(TradePositionDo tradePositionDo);

    /**
     * 持仓 domain 转换成历史 entity
     * @param tradePositionDo 持仓Do
     * @return 持仓 domain 转换成实体entity
     */
    TradePositionHistoryDo doToHisDo(TradePositionDo tradePositionDo);

    /**
     * 持仓 entity 转换成 domain
     * @param tradePosition 持仓
     * @return 持仓 entity 转换成 domain
     */
    TradePositionDo entityToDo(TradePosition tradePosition);

    /**
     * 持仓 entity 转换成 vo
     * @param tradePosition 持仓
     * @return 持仓 entity 转换成 vo
     */
    TradePositionVo entityToVo(TradePosition tradePosition);
    /**
     * 持仓  vo 转换成  entity
     * @param TradePositionVo 持仓vo
     * @return 持仓  vo 转换成  entity
     */
    TradePosition voToEntity(TradePositionVo TradePositionVo);
}
