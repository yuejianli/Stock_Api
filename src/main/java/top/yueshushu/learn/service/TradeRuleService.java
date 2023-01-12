package top.yueshushu.learn.service;

import top.yueshushu.learn.entity.TradeRule;
import top.yueshushu.learn.mode.dto.StockRuleDto;
import top.yueshushu.learn.mode.dto.TradeRuleStockQueryDto;
import top.yueshushu.learn.mode.ro.TradeRuleRo;
import top.yueshushu.learn.mode.vo.TradeRuleVo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.response.PageResponse;

import java.util.List;

/**
 * <p>
 * 交易规则表 自定义的
 * </p>
 *
 * @author 两个蝴蝶飞
 * @since 2022-01-26
 */
public interface TradeRuleService  {
    /**
     * 查询交易的规则
     *
     * @param tradeRuleRo 规则对象
     * @return top.yueshushu.learn.response.OutputResult
     * @date 2022/1/27 10:01
     * @author zk_yjl
     */
    OutputResult<PageResponse<TradeRuleVo>> listRule(TradeRuleRo tradeRuleRo);

    /**
     * 添加交易规则
     *
     * @param tradeRuleRo 规则对象
     * @return top.yueshushu.learn.response.OutputResult
     * @date 2022/1/26 15:50
     * @author zk_yjl
     */
    OutputResult addRule(TradeRuleRo tradeRuleRo);
    /**
     * 修改交易规则
     * @date 2022/1/27 9:56
     * @author zk_yjl
     * @param tradeRuleRo 规则对象
     * @return top.yueshushu.learn.response.OutputResult
     */
    OutputResult updateRule(TradeRuleRo tradeRuleRo);
    /**
     * 启用交易规则
     * @date 2022/1/27 10:13
     * @author zk_yjl
     * @param tradeRuleRo 规则对象
     * @return top.yueshushu.learn.response.OutputResult
     */
    OutputResult enableRule(TradeRuleRo tradeRuleRo);
    /**
     * 禁用交易规则
     * @date 2022/1/27 10:13
     * @author zk_yjl
     * @param tradeRuleRo 规则对象
     * @return top.yueshushu.learn.response.OutputResult
     */
    OutputResult disableRule(TradeRuleRo tradeRuleRo);
    /**
     * 删除交易规则
     * @date 2022/1/27 20:06
     * @author zk_yjl
     * @param tradeRuleRo 规则对象
     * @return top.yueshushu.learn.response.OutputResult
     */
    OutputResult deleteRule(TradeRuleRo tradeRuleRo);
    /**
     * 查询对应的规则信息
     * @date 2022/1/28 15:41
     * @author zk_yjl
     * @param tradeRuleStockQueryDto 规则对象
     * @return java.util.List<top.yueshushu.learn.mode.dto.StockRuleDto>
     */
    List<StockRuleDto> getRuleByQuery(TradeRuleStockQueryDto tradeRuleStockQueryDto);

    /**
     * 根据规则编号 id 查询相应的规则信息
     * @param id id编号
     * @return 根据规则编号 id 查询相应的规则信息
     */
    TradeRule getById(Integer id);
}
