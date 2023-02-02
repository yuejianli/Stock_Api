package top.yueshushu.learn.domainservice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.yueshushu.learn.domain.TradeRuleStockDo;
import top.yueshushu.learn.domainservice.TradeRuleStockDomainService;
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
                .eq(TradeRuleStockDo::getRuleId, ruleId)
                .orderByDesc(TradeRuleStockDo::getStockCode)
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
                userId, mockType, removeCodeList
        );
    }

    @Override
    public List<TradeRuleStockDo> listByCodeList(List<String> codeList) {
        return this.lambdaQuery()
                .in(!CollectionUtils.isEmpty(codeList), TradeRuleStockDo::getStockCode, codeList)
                .orderByDesc(TradeRuleStockDo::getStockCode)
                .list();
    }
}
