package top.yueshushu.learn.domainservice;

import com.baomidou.mybatisplus.extension.service.IService;
import top.yueshushu.learn.domain.TradeRuleConditionDo;
import top.yueshushu.learn.domain.TradeRuleStockDo;
import top.yueshushu.learn.entity.TradeRuleStock;
import top.yueshushu.learn.mode.dto.TradeRuleStockQueryDto;

import java.util.List;

/**
 * @Description 交易的规则
 * @Author yuejianli
 * @Date 2022/5/20 23:23
 **/
public interface TradeRuleStockDomainService extends IService<TradeRuleStockDo> {
    /**
     * 根据规则的id 查询配置的股票对象
     * @param ruleId 规则id
     * @return 根据规则的id,查询配置的股票对象
     */
    List<TradeRuleStockDo> listByRuleId(Integer ruleId);
    /**
     * 根据规则id 查询不在这个规则内的对应的配置股票对象
     * @param tradeRuleStockQueryDto 股票规则id
     * @return 根据规则id 查询对应的配置股票对象
     */
    List<TradeRuleStockDo> listNotInRuleId(TradeRuleStockQueryDto tradeRuleStockQueryDto);

    /**
     * 删除其它的股票信息
     * @param userId 用户编号
     * @param mockType 类型
     * @param removeCodeList 要删除的股票编码
     */
    void removeOtherStock(Integer userId, Integer mockType, List<String> removeCodeList);
}
