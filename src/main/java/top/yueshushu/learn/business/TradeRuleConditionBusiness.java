package top.yueshushu.learn.business;

import top.yueshushu.learn.entity.TradeMethod;
import top.yueshushu.learn.enumtype.TradeMethodType;
import top.yueshushu.learn.mode.ro.TradeMethodRo;
import top.yueshushu.learn.mode.ro.TradeRuleConditionRo;
import top.yueshushu.learn.response.OutputResult;

/**
 * @Description 交易规则条件编排层
 * @Author 岳建立
 * @Date 2022/5/20 22:33
 **/
public interface TradeRuleConditionBusiness {
    /**
     * 查询规则的条件列表
     * @return  查询规则的条件列表
     */
    OutputResult list();

    /**
     * 更新规则信息
     * @param tradeRuleConditionRo 更新规则对象
     * @return 更新规则信息
     */
    OutputResult updateCondition(TradeRuleConditionRo tradeRuleConditionRo);
}
