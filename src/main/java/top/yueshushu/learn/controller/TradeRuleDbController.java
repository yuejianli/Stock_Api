package top.yueshushu.learn.controller;


import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yueshushu.learn.annotation.AuthToken;
import top.yueshushu.learn.business.TradeRuleDbBusiness;
import top.yueshushu.learn.mode.ro.TradeRuleDbRo;
import top.yueshushu.learn.response.OutputResult;

import javax.annotation.Resource;

/**
 * <p>
 * 打板交易规则配置 前端控制器
 * </p>
 *
 * @author 岳建立
 * @since 2023-02-10
 */
@RestController
@RequestMapping("/tradeRuleDb")
public class TradeRuleDbController extends BaseController {

    @Resource
    private TradeRuleDbBusiness tradeRuleDbBusiness;

    @PostMapping("/list")
    @ApiOperation("查询交易规则")
    @AuthToken
    public OutputResult list(@RequestBody TradeRuleDbRo TradeRuleDbRo) {
        TradeRuleDbRo.setUserId(getUserId());
        return tradeRuleDbBusiness.listRule(TradeRuleDbRo);
    }

    @PostMapping("/add")
    @ApiOperation("添加交易规则")
    @AuthToken
    public OutputResult add(@RequestBody TradeRuleDbRo TradeRuleDbRo) {
        TradeRuleDbRo.setUserId(getUserId());
        return tradeRuleDbBusiness.addRule(TradeRuleDbRo);
    }

    @PostMapping("/update")
    @ApiOperation("修改交易规则")
    @AuthToken
    public OutputResult update(@RequestBody TradeRuleDbRo TradeRuleDbRo) {
        TradeRuleDbRo.setUserId(getUserId());
        return tradeRuleDbBusiness.updateRule(TradeRuleDbRo);
    }

    @PostMapping("/delete")
    @ApiOperation("删除交易规则")
    @AuthToken
    public OutputResult delete(@RequestBody TradeRuleDbRo TradeRuleDbRo) {
        TradeRuleDbRo.setUserId(getUserId());
        return tradeRuleDbBusiness.deleteRule(TradeRuleDbRo);
    }
}
