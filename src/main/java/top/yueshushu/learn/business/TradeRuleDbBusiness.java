package top.yueshushu.learn.business;

import top.yueshushu.learn.mode.ro.TradeRuleDbRo;
import top.yueshushu.learn.response.OutputResult;

/**
 * @Description 交易规则编排层
 * @Author 岳建立
 * @Date 2022/5/20 22:33
 **/
public interface TradeRuleDbBusiness {
    /**
     * 查询交易规则配置信息
     *
     * @param TradeRuleDbRo 交易规则对象
     * @return 查询交易规则配置信息
     */
    OutputResult listRule(TradeRuleDbRo TradeRuleDbRo);

    /**
     * 添加交易规则配置信息
     *
     * @param TradeRuleDbRo 交易规则对象
     * @return 添加交易规则配置信息
     */
    OutputResult addRule(TradeRuleDbRo TradeRuleDbRo);

    /**
     * 更新交易规则配置信息
     *
     * @param TradeRuleDbRo 交易规则对象
     * @return 更新交易规则配置信息
     */
    OutputResult updateRule(TradeRuleDbRo TradeRuleDbRo);

    /**
     * 删除交易规则配置信息
     *
     * @param TradeRuleDbRo 交易规则对象
     * @return 删除交易规则配置信息
     */
    OutputResult deleteRule(TradeRuleDbRo TradeRuleDbRo);
}
