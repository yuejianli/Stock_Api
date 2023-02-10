package top.yueshushu.learn.business.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.yueshushu.learn.business.TradeRuleDbBusiness;
import top.yueshushu.learn.mode.ro.TradeRuleDbRo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.service.TradeRuleDbService;

import javax.annotation.Resource;

/**
 * @Description 交易规则条件的信息实现
 * @Author 岳建立
 * @Date 2022/5/20 22:33
 **/
@Service
@Slf4j
public class TradeRuleDbBusinessImpl implements TradeRuleDbBusiness {
    @Resource
    private TradeRuleDbService tradeRuleDbService;

    @Override
    public OutputResult listRule(TradeRuleDbRo TradeRuleDbRo) {
        return tradeRuleDbService.listRule(TradeRuleDbRo);

    }

    @Override
    public OutputResult addRule(TradeRuleDbRo TradeRuleDbRo) {
        return tradeRuleDbService.addRule(TradeRuleDbRo);
    }

    @Override
    public OutputResult updateRule(TradeRuleDbRo TradeRuleDbRo) {
        return tradeRuleDbService.updateRule(TradeRuleDbRo);
    }

    @Override
    public OutputResult deleteRule(TradeRuleDbRo TradeRuleDbRo) {
        return tradeRuleDbService.deleteRule(TradeRuleDbRo);
    }
}
