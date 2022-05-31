package top.yueshushu.learn.service;

import top.yueshushu.learn.entity.TradeRuleCondition;
import top.yueshushu.learn.mode.ro.TradeRuleConditionRo;
import top.yueshushu.learn.domain.TradeRuleConditionDo;
import com.baomidou.mybatisplus.extension.service.IService;
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
     * @date 2022/1/26 14:30
     * @author zk_yjl
     * @return top.yueshushu.learn.response.OutputResult
     */
    OutputResult listCondition();
    /**
     * 更新信息
     * @date 2022/1/26 14:30
     * @author zk_yjl
     * @param tradeRuleConditionRo 条件规则关键字对象
     * @return top.yueshushu.learn.response.OutputResult
     */
    OutputResult updateCondition(TradeRuleConditionRo tradeRuleConditionRo);
    /**
     * 查询所有的规则条件信息
     * @date 2022/1/28 9:48
     * @author zk_yjl
     * @return java.util.List<top.yueshushu.learn.pojo.TradeRuleCondition>
     */
    List<TradeRuleCondition> listAll();
}
