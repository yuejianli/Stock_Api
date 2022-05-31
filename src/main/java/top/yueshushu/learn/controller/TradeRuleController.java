package top.yueshushu.learn.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import top.yueshushu.learn.business.TradeRuleBusiness;
import top.yueshushu.learn.mode.ro.TradeRuleRo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.service.TradeRuleService;

import javax.annotation.Resource;

/**
 * <p>
 * 交易规则表 我是自定义的
 * </p>
 *
 * @author 两个蝴蝶飞
 * @date 2022-01-26
 */
@RestController
@RequestMapping("/tradeRule")
@Api("交易规则处理")
public class TradeRuleController extends BaseController{

    @Resource
    private TradeRuleBusiness tradeRuleBusiness;

    @PostMapping("/list")
    @ApiOperation("查询交易规则")
    public OutputResult list(@RequestBody TradeRuleRo tradeRuleRo){
        tradeRuleRo.setUserId(getUserId());
        return tradeRuleBusiness.listRule(tradeRuleRo);
    }

    @PostMapping("/add")
    @ApiOperation("添加交易规则")
    public OutputResult add(@RequestBody TradeRuleRo tradeRuleRo){
        tradeRuleRo.setUserId(getUserId());
        return tradeRuleBusiness.addRule(tradeRuleRo);
    }
    @PostMapping("/update")
    @ApiOperation("修改交易规则")
    public OutputResult update(@RequestBody TradeRuleRo tradeRuleRo){
        tradeRuleRo.setUserId(getUserId());
        return tradeRuleBusiness.updateRule(tradeRuleRo);
    }

    @PostMapping("/delete")
    @ApiOperation("删除交易规则")
    public OutputResult delete(@RequestBody TradeRuleRo tradeRuleRo){
        tradeRuleRo.setUserId(getUserId());
        return tradeRuleBusiness.deleteRule(tradeRuleRo);
    }

    @PostMapping("/enable")
    @ApiOperation("启用交易规则")
    public OutputResult enable(@RequestBody TradeRuleRo tradeRuleRo){
        tradeRuleRo.setUserId(getUserId());
        return tradeRuleBusiness.enableRule(tradeRuleRo);
    }

    @PostMapping("/disable")
    @ApiOperation("禁用交易规则")
    public OutputResult disable(@RequestBody TradeRuleRo tradeRuleRo){
        tradeRuleRo.setUserId(getUserId());
        return tradeRuleBusiness.disableRule(tradeRuleRo);
    }
}
