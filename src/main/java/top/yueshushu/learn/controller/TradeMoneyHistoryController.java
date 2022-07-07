package top.yueshushu.learn.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import top.yueshushu.learn.business.TradeMoneyHistoryBusiness;
import top.yueshushu.learn.mode.ro.TradeMoneyRo;
import top.yueshushu.learn.response.OutputResult;

/**
 * <p>
 * 股票的金额交易记录表 我是自定义的
 * </p>
 *
 * @author 岳建立
 * @date 2022-01-02
 */
@RestController
@RequestMapping("/tradeMoneyHistory")
@Api("查询我的金额历史记录信息")
public class TradeMoneyHistoryController extends BaseController {
    @Resource
    private TradeMoneyHistoryBusiness tradeMoneyHistoryBusiness;
    
    @ApiOperation("查询金额历史记录信息")
    @PostMapping("/history")
    public OutputResult history(@RequestBody TradeMoneyRo TradeMoneyRo) {
        TradeMoneyRo.setUserId(getUserId());
        return tradeMoneyHistoryBusiness.listHistory(TradeMoneyRo);
    }
}
