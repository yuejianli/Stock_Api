package top.yueshushu.learn.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yueshushu.learn.annotation.AuthToken;
import top.yueshushu.learn.business.TradeRuleStockBusiness;
import top.yueshushu.learn.common.ResultCode;
import top.yueshushu.learn.mode.ro.TradeRuleStockRo;
import top.yueshushu.learn.mode.vo.TradeRuleStockVo;
import top.yueshushu.learn.response.OutputResult;

import javax.annotation.Resource;

/**
 * <p>
 * 规则股票对应信息表 我是自定义的
 * </p>
 *
 * @author 两个蝴蝶飞
 * @date 2022-01-26
 */
@RestController
@RequestMapping("/tradeRuleStock")
@Api("规则配置的股票信息")
public class TradeRuleStockController extends BaseController {
    @Resource
    private TradeRuleStockBusiness tradeRuleStockBusiness;

    @PostMapping("/applyList")
    @ApiOperation("查询该规则适用的股票信息")
    @AuthToken
    public OutputResult applyList(@RequestBody TradeRuleStockRo tradeRuleStockRo) {
        tradeRuleStockRo.setUserId(getUserId());
        //根据id 去查询
        if(tradeRuleStockRo.getId()==null){
            return OutputResult.buildSucc(new TradeRuleStockVo());
        }
        return tradeRuleStockBusiness.applyList(tradeRuleStockRo);
    }

    @PostMapping("/apply")
    @ApiOperation("规则配置股票信息")
    @AuthToken
    public OutputResult apply(@RequestBody TradeRuleStockRo tradeRuleStockRo) {
        tradeRuleStockRo.setUserId(getUserId());
        //根据id 去查询
        if(tradeRuleStockRo.getId()==null){
            return OutputResult.buildAlert(ResultCode.ID_IS_EMPTY);
        }
        return tradeRuleStockBusiness.apply(tradeRuleStockRo);
    }

    @PostMapping("/stockRuleList")
    @ApiOperation("查询股票配置的规则信息")
    @AuthToken
    public OutputResult stockRuleList(@RequestBody TradeRuleStockRo tradeRuleStockRo) {
        tradeRuleStockRo.setUserId(getUserId());
        return tradeRuleStockBusiness.stockRuleList(tradeRuleStockRo);
    }
}
