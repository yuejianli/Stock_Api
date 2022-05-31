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
     * @param TradeRuleDo 交易规则 Do
     * @return 交易规则 domain 转换成实体entity
     */
    TradeRule doToEntity(TradeRuleDo TradeRuleDo);

    /**
     * 交易规则 entity 转换成 domain
     * @param TradeRule 交易规则
     * @return 交易规则 entity 转换成 domain
     */
    TradeRuleDo entityToDo(TradeRule TradeRule);

    /**
     * 交易规则 entity 转换成 vo
     * @param TradeRule 交易规则
     * @return 交易规则 entity 转换成 vo
     */
    TradeRuleVo entityToVo(TradeRule TradeRule);

    /**
     * 交易规则 ro 转换成实体entity
     * @param tradeRuleRo 交易规则 ro
     * @return 交易规则 ro 转换成实体entity
     */
    TradeRule roToEntity(TradeRuleRo tradeRuleRo);

}
