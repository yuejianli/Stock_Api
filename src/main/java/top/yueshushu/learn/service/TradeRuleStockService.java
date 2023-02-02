package top.yueshushu.learn.service;

import top.yueshushu.learn.entity.TradeRuleStock;
import top.yueshushu.learn.mode.dto.TradeRuleStockQueryDto;
import top.yueshushu.learn.mode.vo.StockSelectedVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 规则股票对应信息表 自定义的
 * </p>
 *
 * @author 两个蝴蝶飞
 * @since 2022-01-26
 */
public interface TradeRuleStockService {
    /**
     * 根据规则id 查询对应的配置股票对象
     * @param ruleId 股票规则id
     * @return 根据规则id 查询对应的配置股票对象
     */
    List<TradeRuleStock> listByRuleId(Integer ruleId);

    /**
     * 根据规则id 查询不在这个规则内的对应的配置股票对象
     * @param tradeRuleStockQueryDto 股票规则id
     * @return 根据规则id 查询对应的配置股票对象
     */
    List<TradeRuleStock> listNotInRuleId(TradeRuleStockQueryDto tradeRuleStockQueryDto);

    /**
     * 将股票规则信息进行转换,添加股票的名称填充
     *
     * @param stockNameMap  股票的名称 map
     * @param ruleStockList 规则配置股票集合
     * @return 将股票规则信息进行转换, 添加股票的名称填充
     */
    List<StockSelectedVo> ruleStockToStockVo(Map<String, String> stockNameMap, List<TradeRuleStock> ruleStockList);

    /**
     * 根据股票编码列表获取对应的规则信息   key 为股票编码   value 为对应的规则信息。
     *
     * @param codeList 股票编码列表
     */
    Map<String, List<Integer>> listRuleIdByCode(List<String> codeList);
}
