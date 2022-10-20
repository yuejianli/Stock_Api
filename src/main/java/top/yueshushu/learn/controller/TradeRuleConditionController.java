package top.yueshushu.learn.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yueshushu.learn.annotation.AuthToken;
import top.yueshushu.learn.business.TradeRuleConditionBusiness;
import top.yueshushu.learn.common.ResultCode;
import top.yueshushu.learn.mode.ro.TradeRuleConditionRo;
import top.yueshushu.learn.response.OutputResult;

import javax.annotation.Resource;

/**
 * <p>
 * 交易规则可使用的条件表 我是自定义的
 * </p>
 *
 * @author 两个蝴蝶飞
 * @date 2022-01-26
 */
@RestController
@RequestMapping("/tradeRuleCondition")
@Api("交易规则使用的关键字表")
public class TradeRuleConditionController extends BaseController{
    @Resource
    private TradeRuleConditionBusiness tradeRuleConditionBusiness;

    @PostMapping("/list")
    @ApiOperation("查询规则使用的关键字信息")
    @AuthToken
    public OutputResult list(){
        return tradeRuleConditionBusiness.list();
    }

    @PostMapping("/update")
    @ApiOperation("更新规则关键字信息")
    @AuthToken
    public OutputResult update(@RequestBody TradeRuleConditionRo tradeRuleConditionRo){
        if(tradeRuleConditionRo.getId()==null){
            return OutputResult.buildAlert(ResultCode.ID_IS_EMPTY);
        }
        return tradeRuleConditionBusiness.updateCondition(tradeRuleConditionRo);
    }
}
