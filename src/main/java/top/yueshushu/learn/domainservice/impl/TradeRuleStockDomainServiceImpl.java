package top.yueshushu.learn.domainservice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.yueshushu.learn.domain.TradeRuleDo;
import top.yueshushu.learn.domain.TradeRuleStockDo;
import top.yueshushu.learn.domainservice.TradeRuleDomainService;
import top.yueshushu.learn.domainservice.TradeRuleStockDomainService;
import top.yueshushu.learn.entity.TradeRuleStock;
import top.yueshushu.learn.mapper.TradeRuleDoMapper;
import top.yueshushu.learn.mapper.TradeRuleStockDoMapper;
import top.yueshushu.learn.mode.dto.TradeRuleStockQueryDto;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description 交易规则配置
 * @Author yuejianli
 * @Date 2022/5/20 23:23
 **/
@Service
@Slf4j
public class TradeRuleStockDomainServiceImpl extends ServiceImpl<TradeRuleStockDoMapper, TradeRuleStockDo>
        implements TradeRuleStockDomainService {
    @Resource
    private TradeRuleStockDoMapper tradeRuleStockDoMapper;

    @Override
    public List<TradeRuleStockDo> listByRuleId(Integer ruleId) {
        return this.lambdaQuery()
                .eq(TradeRuleStockDo::getRuleId,ruleId)
                .orderByDesc(TradeRuleStockDo::getId)
                .list();
    }

    @Override
    public List<TradeRuleStockDo> listNotInRuleId(TradeRuleStockQueryDto tradeRuleStockQueryDto) {
        return tradeRuleStockDoMapper.listNotInRuleId(
                tradeRuleStockQueryDto
        );
    }

    @Override
    public void removeOtherStock(Integer userId, Integer mockType, List<String> removeCodeList) {
         tradeRuleStockDoMapper.removeOtherStock(
                userId,mockType,removeCodeList
        );
    }
}
