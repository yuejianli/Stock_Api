package top.yueshushu.learn.assembler;

import org.mapstruct.Mapper;
import top.yueshushu.learn.domain.TradeRuleConditionDo;
import top.yueshushu.learn.entity.TradeRuleCondition;
import top.yueshushu.learn.mode.vo.TradeRuleConditionVo;

import java.util.List;

/**
 * @Description 交易规则条件转换器
 * @Author yuejianli
 * @Date 2022/5/20 23:01
 **/
@Mapper(componentModel = "spring" )
public interface TradeRuleConditionAssembler {
    /**
     * 交易规则条件 domain 转换成实体entity
     * @param TradeRuleConditionDo 交易规则条件Do
     * @return 交易规则条件 domain 转换成实体entity
     */
    TradeRuleCondition doToEntity(TradeRuleConditionDo TradeRuleConditionDo);

    /**
     * 交易规则条件 entity 转换成 domain
     *
     * @param TradeRuleCondition 交易规则条件
     * @return 交易规则条件 entity 转换成 domain
     */
    TradeRuleConditionDo entityToDo(TradeRuleCondition TradeRuleCondition);

    TradeRuleConditionVo entityToVo(TradeRuleCondition TradeRuleCondition);

    List<TradeRuleConditionVo> entityToVoList(List<TradeRuleCondition> tradeRuleConditionList);
}
