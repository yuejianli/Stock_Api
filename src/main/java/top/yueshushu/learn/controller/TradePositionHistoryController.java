package top.yueshushu.learn.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yueshushu.learn.annotation.AuthToken;
import top.yueshushu.learn.business.TradePositionHistoryBusiness;
import top.yueshushu.learn.mode.ro.TradePositionRo;
import top.yueshushu.learn.response.OutputResult;

import javax.annotation.Resource;

/**
 * <p>
 * 股票的历史交易记录表 我是自定义的
 * </p>
 *
 * @author 岳建立
 * @date 2022-01-02
 */
@RestController
@RequestMapping("/tradePositionHistory")
@Api("查询我的持仓历史记录信息")
public class TradePositionHistoryController extends BaseController {
    @Resource
    private TradePositionHistoryBusiness tradePositionHistoryBusiness;

    @ApiOperation("查询持仓历史记录信息")
    @PostMapping("/history")
    @AuthToken
    public OutputResult history(@RequestBody TradePositionRo tradePositionRo) {
        tradePositionRo.setUserId(getUserId());
        return tradePositionHistoryBusiness.listHistory(tradePositionRo);
    }

    @ApiOperation("清理最近的持仓历史缓存信息")
    @PostMapping("/clearPhCache")
    @AuthToken
    public OutputResult clearPhCache() {
        return tradePositionHistoryBusiness.clearPhCache();
    }
}
