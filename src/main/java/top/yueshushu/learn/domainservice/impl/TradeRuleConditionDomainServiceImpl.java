package top.yueshushu.learn.domainservice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.yueshushu.learn.domain.TradeRuleConditionDo;
import top.yueshushu.learn.domainservice.TradeRuleConditionDomainService;
import top.yueshushu.learn.mapper.TradeRuleConditionDoMapper;

import javax.annotation.Resource;

/**
 * @Description 交易规则配置
 * @Author yuejianli
 * @Date 2022/5/20 23:23
 **/
@Service
@Slf4j
public class TradeRuleConditionDomainServiceImpl extends ServiceImpl<TradeRuleConditionDoMapper, TradeRuleConditionDo>
        implements TradeRuleConditionDomainService {
    @Resource
    private TradeRuleConditionDoMapper tradeRuleConditionDoMapper;

    @Override
    public TradeRuleConditionDo getByCode(String code) {
        return this.lambdaQuery()
                .eq(TradeRuleConditionDo::getCode, code)
                .one();
    }
}
