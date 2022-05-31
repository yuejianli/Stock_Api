package top.yueshushu.learn.assembler;

import org.mapstruct.Mapper;
import top.yueshushu.learn.domain.TradeEntrustDo;
import top.yueshushu.learn.entity.TradeEntrust;
import top.yueshushu.learn.mode.vo.TradeEntrustVo;

/**
 * @Description 委托转换器
 * @Author yuejianli
 * @Date 2022/5/20 23:01
 **/
@Mapper(componentModel = "spring" )
public interface TradeEntrustAssembler {
    /**
     * 委托 domain 转换成实体entity
     * @param tradeEntrustDo 委托Do
     * @return 委托 domain 转换成实体entity
     */
    TradeEntrust doToEntity(TradeEntrustDo tradeEntrustDo);

    /**
     * 委托 entity 转换成 domain
     * @param tradeEntrust 委托
     * @return 委托 entity 转换成 domain
     */
    TradeEntrustDo entityToDo(TradeEntrust tradeEntrust);

    /**
     * 委托 entity 转换成 vo
     * @param tradeEntrust 委托
     * @return 委托 entity 转换成 vo
     */
    TradeEntrustVo entityToVo(TradeEntrust tradeEntrust);
    /**
     * 委托  vo 转换成  entity
     * @param TradeEntrustVo 委托vo
     * @return 委托  vo 转换成  entity
     */
    TradeEntrust voToEntity(TradeEntrustVo TradeEntrustVo);
}
