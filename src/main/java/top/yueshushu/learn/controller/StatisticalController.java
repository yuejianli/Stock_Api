package top.yueshushu.learn.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yueshushu.learn.annotation.AuthToken;
import top.yueshushu.learn.business.StatBusiness;
import top.yueshushu.learn.common.ResultCode;
import top.yueshushu.learn.mode.ro.StatTen10Ro;
import top.yueshushu.learn.mode.ro.StockStatRo;
import top.yueshushu.learn.response.OutputResult;

import javax.annotation.Resource;

/**
 * @Description 统计股票的情况
 * @Author yuejianli
 * @Date 2022/5/21 10:14
 **/
@RestController
@RequestMapping("/stat")
@ApiOperation("股票爬虫程序")
public class StatisticalController extends BaseController {

    @Resource
    private StatBusiness statBusiness;

    @ApiOperation("股票周统计信息")
    @PostMapping("/getWeekStat")
    @AuthToken
    public OutputResult getWeekStat(@RequestBody StockStatRo stockStatRo) {
        if (!StringUtils.hasText(stockStatRo.getCode())) {
            return OutputResult.buildAlert(ResultCode.STOCK_CODE_IS_EMPTY);
        }
        return statBusiness.getWeekStat(stockStatRo);
    }

    @ApiOperation("股票图形统计信息")
    @PostMapping("/getCharStat")
    @AuthToken
    public OutputResult getCharStat(@RequestBody StockStatRo stockStatRo) {
        if (!StringUtils.hasText(stockStatRo.getCode())) {
            return OutputResult.buildAlert(ResultCode.STOCK_CODE_IS_EMPTY);
        }
        return statBusiness.getCharStat(stockStatRo);
    }

    @ApiOperation("查询最近十天的交易日信息")
    @PostMapping("/getTenTradeDay")
    @AuthToken
    public OutputResult getTenTradeDay() {
        return statBusiness.getTenTradeDay();
    }

    @ApiOperation("查询最近几天的交易日信息")
    @PostMapping("/getTradeDay")
    @AuthToken
    public OutputResult getTradeDay(@RequestBody StockStatRo stockStatRo) {
        return statBusiness.getTradeDay(stockStatRo);
    }

    @ApiOperation("查询当前用户自选表里面最近十天的交易信息")
    @PostMapping("/getTenTradeData")
    @AuthToken
    public OutputResult getTenTrade(@RequestBody StatTen10Ro statTen10Ro) {
        statTen10Ro.setUserId(getUserId());
        return statBusiness.getTenTradeData(statTen10Ro);
    }
}
