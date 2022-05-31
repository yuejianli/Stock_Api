package top.yueshushu.learn.business.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.yueshushu.learn.business.TradeRuleBusiness;
import top.yueshushu.learn.business.TradeRuleConditionBusiness;
import top.yueshushu.learn.mode.ro.TradeRuleConditionRo;
import top.yueshushu.learn.mode.ro.TradeRuleRo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.service.TradeRuleConditionService;
import top.yueshushu.learn.service.TradeRuleService;

import javax.annotation.Resource;

/**
 * @Description 交易规则条件的信息实现
 * @Author 岳建立
 * @Date 2022/5/20 22:33
 **/
@Service
@Slf4j
public class TradeRuleBusinessImpl implements TradeRuleBusiness {
    @Resource
    private TradeRuleService tradeRuleService;

    @Override
    public OutputResult listRule(TradeRuleRo tradeRuleRo) {
        return tradeRuleService.listRule(tradeRuleRo);

    }

    @Override
    public OutputResult addRule(TradeRuleRo tradeRuleRo) {
        return tradeRuleService.addRule(tradeRuleRo);
    }

    @Override
    public OutputResult updateRule(TradeRuleRo tradeRuleRo) {
        return tradeRuleService.updateRule(tradeRuleRo);
    }

    @Override
    public OutputResult deleteRule(TradeRuleRo tradeRuleRo) {
        return tradeRuleService.deleteRule(tradeRuleRo);
    }

    @Override
    public OutputResult enableRule(TradeRuleRo tradeRuleRo) {
        return tradeRuleService.enableRule(tradeRuleRo);
    }

    @Override
    public OutputResult disableRule(TradeRuleRo tradeRuleRo) {
        return tradeRuleService.disableRule(tradeRuleRo);
    }
}
