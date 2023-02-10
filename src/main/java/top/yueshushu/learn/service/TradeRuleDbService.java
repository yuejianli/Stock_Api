package top.yueshushu.learn.service;

import top.yueshushu.learn.entity.TradeRuleDb;
import top.yueshushu.learn.mode.ro.TradeRuleDbRo;
import top.yueshushu.learn.mode.vo.TradeRuleDbVo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.response.PageResponse;

/**
 * <p>
 * 交易规则表 自定义的
 * </p>
 *
 * @author 两个蝴蝶飞
 * @since 2022-01-26
 */
public interface TradeRuleDbService {
    /**
     * 查询交易的规则
     *
     * @param tradeRuleDbRo 规则对象
     * @return top.yueshushu.learn.response.OutputResult
     * @date 2022/1/27 10:01
     * @author zk_yjl
     */
    OutputResult<PageResponse<TradeRuleDbVo>> listRule(TradeRuleDbRo tradeRuleDbRo);

    /**
     * 添加交易规则
     *
     * @param tradeRuleDbRo 规则对象
     * @return top.yueshushu.learn.response.OutputResult
     * @date 2022/1/26 15:50
     * @author zk_yjl
     */
    OutputResult addRule(TradeRuleDbRo tradeRuleDbRo);

    /**
     * 修改交易规则
     *
     * @param tradeRuleDbRo 规则对象
     * @return top.yueshushu.learn.response.OutputResult
     * @date 2022/1/27 9:56
     * @author zk_yjl
     */
    OutputResult updateRule(TradeRuleDbRo tradeRuleDbRo);

    /**
     * 删除交易规则
     *
     * @param tradeRuleDbRo 规则对象
     * @return top.yueshushu.learn.response.OutputResult
     * @date 2022/1/27 20:06
     * @author zk_yjl
     */
    OutputResult deleteRule(TradeRuleDbRo tradeRuleDbRo);

    /**
     * 根据规则编号 id 查询相应的规则信息
     *
     * @param id id编号
     * @return 根据规则编号 id 查询相应的规则信息
     */
    TradeRuleDb getById(Integer id);
}
