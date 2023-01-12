package top.yueshushu.learn.service;

import top.yueshushu.learn.entity.TradeRuleCondition;
import top.yueshushu.learn.mode.ro.TradeRuleConditionRo;
import top.yueshushu.learn.mode.vo.TradeRuleConditionVo;
import top.yueshushu.learn.response.OutputResult;

import java.util.List;

/**
 * <p>
 * 交易规则可使用的条件表 自定义的
 * </p>
 *
 * @author 两个蝴蝶飞
 * @since 2022-01-26
 */
public interface TradeRuleConditionService{
    /**
     * 全部查询 条件表信息
     *
     * @return top.yueshushu.learn.response.OutputResult
     * @date 2022/1/26 14:30
     * @author zk_yjl
     */
    OutputResult<List<TradeRuleConditionVo>> listCondition();

    /**
     * 更新信息
     *
     * @param tradeRuleConditionRo 条件规则关键字对象
     * @return top.yueshushu.learn.response.OutputResult
     * @date 2022/1/26 14:30
     * @author zk_yjl
     */
    OutputResult updateCondition(TradeRuleConditionRo tradeRuleConditionRo);

    /**
     * 查询所有的规则条件信息
     *
     * @return java.util.List<top.yueshushu.learn.pojo.TradeRuleCondition>
     * @date 2022/1/28 9:48
     * @author zk_yjl
     */
    List<TradeRuleCondition> listAll();

    /**
     * 添加规则条件信息
     *
     * @param tradeRuleConditionRo 条件规则关键字对象
     * @return top.yueshushu.learn.response.OutputResult
     * @date 2022/1/26 14:30
     * @author zk_yjl
     */
    OutputResult addCondition(TradeRuleConditionRo tradeRuleConditionRo);

    /**
     * 根据规则条件id 删除规则条件
     *
     * @param id 规则条件Id
     */
    OutputResult deleteCondition(Integer id);
}
