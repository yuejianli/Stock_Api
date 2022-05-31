package top.yueshushu.learn.assembler;

import org.mapstruct.Mapper;
import top.yueshushu.learn.domain.TradeMoneyDo;
import top.yueshushu.learn.entity.TradeMoney;
import top.yueshushu.learn.mode.vo.TradeMoneyVo;

/**
 * @Description 金额转换器
 * @Author yuejianli
 * @Date 2022/5/20 23:01
 **/
@Mapper(componentModel = "spring" )
public interface TradeMoneyAssembler {
    /**
     * 金额 domain 转换成实体entity
     * @param tradeMoneyDo 金额Do
     * @return 金额 domain 转换成实体entity
     */
    TradeMoney doToEntity(TradeMoneyDo tradeMoneyDo);

    /**
     * 金额 entity 转换成 domain
     * @param tradeMoney 金额
     * @return 金额 entity 转换成 domain
     */
    TradeMoneyDo entityToDo(TradeMoney tradeMoney);

    /**
     * 金额 entity 转换成 vo
     * @param tradeMoney 金额
     * @return 金额 entity 转换成 vo
     */
    TradeMoneyVo entityToVo(TradeMoney tradeMoney);
    /**
     * 金额  vo 转换成  entity
     * @param TradeMoneyVo 金额vo
     * @return 金额  vo 转换成  entity
     */
    TradeMoney voToEntity(TradeMoneyVo TradeMoneyVo);
}
