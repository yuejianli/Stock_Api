package top.yueshushu.learn.domainservice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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
    public List<StockRuleDto> getRuleByQuery(TradeRuleStockQueryDto tradeRuleStockQueryDto) {
        return tradeRuleDoMapper.listRuleByQuery(tradeRuleStockQueryDto);
    }

    @Override
    public List<TradeRuleDo> listByQuery(Integer userId, Integer mockType, Integer ruleType) {
       return this.lambdaQuery()
                .eq(userId != null,TradeRuleDo::getUserId, userId)
                .eq(mockType != null,TradeRuleDo::getMockType, mockType)
                .eq(ruleType != null,TradeRuleDo::getRuleType, ruleType)
                .orderByDesc(
                        TradeRuleDo::getUpdateTime
                ).list();
    }
}
