package top.yueshushu.learn.business;

import top.yueshushu.learn.mode.ro.TradeRuleRo;
import top.yueshushu.learn.mode.ro.TradeRuleStockRo;
import top.yueshushu.learn.response.OutputResult;

/**
 * @Description 交易规则编排层
 * @Author 岳建立
 * @Date 2022/5/20 22:33
 **/
public interface TradeRuleStockBusiness {
    /**
     * 查询规则配置的股票列表信息
     * @param tradeRuleStockRo 规则配置股票对象
     * @return 查询规则配置的股票列表信息
     */
    OutputResult applyList(TradeRuleStockRo tradeRuleStockRo);
    /**
     * 配置规则配置的股票列表信息
     * @param tradeRuleStockRo 规则配置股票对象
     * @return 配置规则配置的股票列表信息
     */
    OutputResult apply(TradeRuleStockRo tradeRuleStockRo);
    /**
     * 查询配股票配置的规则列表信息
     * @param tradeRuleStockRo 规则配置股票对象
     * @return 查询配股票配置的规则列表信息
     */
    OutputResult stockRuleList(TradeRuleStockRo tradeRuleStockRo);
}
