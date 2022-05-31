package top.yueshushu.learn.business;

import top.yueshushu.learn.mode.ro.TradeRuleConditionRo;
import top.yueshushu.learn.mode.ro.TradeRuleRo;
import top.yueshushu.learn.response.OutputResult;

/**
 * @Description 交易规则编排层
 * @Author 岳建立
 * @Date 2022/5/20 22:33
 **/
public interface TradeRuleBusiness {
    /**
     * 查询交易规则配置信息
     * @param tradeRuleRo 交易规则对象
     * @return 查询交易规则配置信息
     */
    OutputResult listRule(TradeRuleRo tradeRuleRo);
    /**
     * 添加交易规则配置信息
     * @param tradeRuleRo 交易规则对象
     * @return 添加交易规则配置信息
     */
    OutputResult addRule(TradeRuleRo tradeRuleRo);
    /**
     * 更新交易规则配置信息
     * @param tradeRuleRo 交易规则对象
     * @return 更新交易规则配置信息
     */
    OutputResult updateRule(TradeRuleRo tradeRuleRo);
    /**
     * 删除交易规则配置信息
     * @param tradeRuleRo 交易规则对象
     * @return 删除交易规则配置信息
     */
    OutputResult deleteRule(TradeRuleRo tradeRuleRo);
    /**
     * 启用交易规则配置信息
     * @param tradeRuleRo 交易规则对象
     * @return 启用交易规则配置信息
     */
    OutputResult enableRule(TradeRuleRo tradeRuleRo);
    /**
     * 禁用交易规则配置信息
     * @param tradeRuleRo 交易规则对象
     * @return 禁用交易规则配置信息
     */
    OutputResult disableRule(TradeRuleRo tradeRuleRo);
}
