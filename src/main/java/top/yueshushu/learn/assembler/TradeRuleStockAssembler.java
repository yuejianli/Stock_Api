package top.yueshushu.learn.assembler;

import org.mapstruct.Mapper;
import top.yueshushu.learn.domain.TradeRuleStockDo;
import top.yueshushu.learn.entity.TradeRuleStock;

/**
 * @Description 交易规则配置股票转换器
 * @Author yuejianli
 * @Date 2022/5/20 23:01
 **/
@Mapper(componentModel = "spring" )
public interface TradeRuleStockAssembler {
    /**
     * 交易规则配置股票 domain 转换成实体entity
     * @param TradeRuleStockDo 交易规则配置股票 Do
     * @return 交易规则配置股票 domain 转换成实体entity
     */
    TradeRuleStock doToEntity(TradeRuleStockDo TradeRuleStockDo);

    /**
     * 交易规则配置股票 entity 转换成 domain
     * @param TradeRuleStock 交易规则配置股票
     * @return 交易规则配置股票 entity 转换成 domain
     */
    TradeRuleStockDo entityToDo(TradeRuleStock TradeRuleStock);

}
