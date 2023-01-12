package top.yueshushu.learn.business;

import top.yueshushu.learn.mode.ro.TradeRuleConditionRo;
import top.yueshushu.learn.mode.vo.TradeRuleConditionVo;
import top.yueshushu.learn.response.OutputResult;

import java.util.List;

/**
 * @Description 交易规则条件编排层
 * @Author 岳建立
 * @Date 2022/5/20 22:33
 **/
public interface TradeRuleConditionBusiness {
    /**
     * 查询规则的条件列表
     *
     * @return 查询规则的条件列表
     */
    OutputResult<List<TradeRuleConditionVo>> list();

    /**
     * 更新规则信息
     *
     * @param tradeRuleConditionRo 更新规则条件对象
     * @return 更新规则信息
     */
    OutputResult updateCondition(TradeRuleConditionRo tradeRuleConditionRo);

    /**
     * 添加规则信息
     *
     * @param tradeRuleConditionRo 添加规则条件对象
     */
    OutputResult addCondition(TradeRuleConditionRo tradeRuleConditionRo);

    /**
     * 删除规则条件
     *
     * @param id 规则条件id
     */
    OutputResult deleteCondition(Integer id);

}
