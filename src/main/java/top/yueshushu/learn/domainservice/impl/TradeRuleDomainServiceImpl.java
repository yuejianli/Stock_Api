package top.yueshushu.learn.domainservice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.yueshushu.learn.domain.TradeRuleDo;
import top.yueshushu.learn.domainservice.TradeRuleDomainService;
import top.yueshushu.learn.mapper.TradeRuleDoMapper;
import top.yueshushu.learn.mode.dto.StockRuleDto;
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
public class TradeRuleDomainServiceImpl extends ServiceImpl<TradeRuleDoMapper, TradeRuleDo>
        implements TradeRuleDomainService {
    @Resource
    private TradeRuleDoMapper tradeRuleDoMapper;

    @Override
    public List<StockRuleDto> listStockRuleByQuery(TradeRuleStockQueryDto tradeRuleStockQueryDto) {
        return tradeRuleDoMapper.listStockRuleByQuery(tradeRuleStockQueryDto);
    }

    @Override
    public List<TradeRuleDo> listByQuery(TradeRuleStockQueryDto tradeRuleStockQueryDto) {
        return this.lambdaQuery()
                .eq(tradeRuleStockQueryDto.getUserId() != null, TradeRuleDo::getUserId, tradeRuleStockQueryDto.getUserId())
                .eq(tradeRuleStockQueryDto.getMockType() != null, TradeRuleDo::getMockType, tradeRuleStockQueryDto.getMockType())
                .eq(tradeRuleStockQueryDto.getRuleType() != null, TradeRuleDo::getRuleType, tradeRuleStockQueryDto.getRuleType())
                .eq(tradeRuleStockQueryDto.getRuleId() != null, TradeRuleDo::getId, tradeRuleStockQueryDto.getRuleId())
                .eq(tradeRuleStockQueryDto.getStatus() != null, TradeRuleDo::getStatus, tradeRuleStockQueryDto.getStatus())
                .in(!CollectionUtils.isEmpty(tradeRuleStockQueryDto.getRuleIdList()), TradeRuleDo::getId, tradeRuleStockQueryDto.getRuleIdList())
                .eq(tradeRuleStockQueryDto.getRuleConditionId() != null, TradeRuleDo::getConditionId, tradeRuleStockQueryDto.getRuleConditionId())
                .orderByDesc(TradeRuleDo::getUpdateTime).list();
    }
}
