package top.yueshushu.learn.domainservice;

import com.baomidou.mybatisplus.extension.service.IService;
import top.yueshushu.learn.domain.TradeRuleConditionDo;
import top.yueshushu.learn.domain.TradeRuleDo;
import top.yueshushu.learn.mode.dto.StockRuleDto;
import top.yueshushu.learn.mode.dto.TradeRuleStockQueryDto;

import java.util.List;

/**
 * @Description 交易的规则
 * @Author yuejianli
 * @Date 2022/5/20 23:23
 **/
public interface TradeRuleDomainService extends IService<TradeRuleDo> {
    /**
     * 通过查询条件,获取相应的规则集合
     * @param tradeRuleStockQueryDto 查询条件
     * @return 通过查询条件,获取相应的规则集合
     */
    List<StockRuleDto> getRuleByQuery(TradeRuleStockQueryDto tradeRuleStockQueryDto);

    /**
     * 根据条件,查询对应的规则集合
     * @param userId 用户编号
     * @param mockType 交易类型
     * @param ruleType 规则类型
     * @return 根据条件,查询对应的规则集合
     */
    List<TradeRuleDo> listByQuery(Integer userId, Integer mockType, Integer ruleType);
}
