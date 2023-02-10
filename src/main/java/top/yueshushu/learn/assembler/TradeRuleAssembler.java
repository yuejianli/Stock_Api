package top.yueshushu.learn.assembler;

import org.mapstruct.Mapper;
import top.yueshushu.learn.domain.TradeRuleDo;
import top.yueshushu.learn.entity.TradeRule;
import top.yueshushu.learn.mode.ro.TradeRuleRo;
import top.yueshushu.learn.mode.vo.TradeRuleVo;

/**
 * @Description 交易规则转换器
 * @Author yuejianli
 * @Date 2022/5/20 23:01
 **/
@Mapper(componentModel = "spring" )
public interface TradeRuleAssembler {
    /**
     * 交易规则 domain 转换成实体entity
     *
     * @param tradeRuleDo 交易规则 Do
     * @return 交易规则 domain 转换成实体entity
     */
    TradeRule doToEntity(TradeRuleDo tradeRuleDo);

    /**
     * 交易规则 entity 转换成 domain
     *
     * @param tradeRule 交易规则
     * @return 交易规则 entity 转换成 domain
     */
    TradeRuleDo entityToDo(TradeRule tradeRule);

    /**
     * 交易规则 entity 转换成 vo
     *
     * @param tradeRule 交易规则
     * @return 交易规则 entity 转换成 vo
     */
    TradeRuleVo entityToVo(TradeRule tradeRule);

    /**
     * 交易规则 ro 转换成实体entity
     * @param tradeRuleRo 交易规则 ro
     * @return 交易规则 ro 转换成实体entity
     */
    TradeRule roToEntity(TradeRuleRo tradeRuleRo);

}
