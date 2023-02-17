package top.yueshushu.learn.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yueshushu.learn.annotation.AuthToken;
import top.yueshushu.learn.business.BKBusiness;
import top.yueshushu.learn.common.ResultCode;
import top.yueshushu.learn.crawler.entity.BKMoneyInfo;
import top.yueshushu.learn.mode.ro.StockBKMoneyStatRo;
import top.yueshushu.learn.mode.vo.StockBKVo;
import top.yueshushu.learn.response.OutputResult;

import javax.annotation.Resource;
import java.util.List;

/**
 * 版块历史
 *
 * @author yuejianli
 * @date 2023-02-07
 */
@RestController
@RequestMapping("/stockBk")
@ApiOperation("股票版块历史记录")
public class StockBKController {

    @Resource
    private BKBusiness bkBusiness;

    @ApiOperation("查询所有的版块列表")
    @PostMapping("/listBk")
    @AuthToken
    public OutputResult<List<StockBKVo>> listBk(@RequestBody StockBKMoneyStatRo stockBKMoneyStatRo) {
        return bkBusiness.listBk();
    }

    @ApiOperation("查询所有的概念列表")
    @PostMapping("/listGn")
    @AuthToken
    public OutputResult<List<StockBKVo>> listGn(@RequestBody StockBKMoneyStatRo stockBKMoneyStatRo) {
        return bkBusiness.listGn();
    }

    @ApiOperation("查询所有的地域列表")
    @PostMapping("/listDy")
    @AuthToken
    public OutputResult<List<StockBKVo>> listDy(@RequestBody StockBKMoneyStatRo stockBKMoneyStatRo) {
        return bkBusiness.listDy();
    }

    @ApiOperation("查询所有的版块资金类型")
    @PostMapping("/listMoneyType")
    @AuthToken
    public OutputResult<List<StockBKVo>> listMoneyType(@RequestBody StockBKMoneyStatRo stockBKMoneyStatRo) {
        return bkBusiness.listMoneyType();
    }

    @ApiOperation("查询所有的版块资金类型")
    @PostMapping("/asyncBkMoney")
    @AuthToken
    public OutputResult<List<BKMoneyInfo>> asyncBkMoney(@RequestBody StockBKMoneyStatRo stockBKMoneyStatRo) {
        if (!StringUtils.hasText(stockBKMoneyStatRo.getBkCode())) {
            return OutputResult.buildAlert(ResultCode.BK_CODE_IS_EMPTY);
        }
        if (!StringUtils.hasText(stockBKMoneyStatRo.getStartDate())) {
            return OutputResult.buildAlert(ResultCode.STOCK_ASYNC_NO_START_DATE);
        }
        return bkBusiness.getMoneyHistoryInfoByCode(stockBKMoneyStatRo);
    }


    @ApiOperation("查询某个版块历史记录列表")
    @PostMapping("/getCharStat")
    @AuthToken
    public OutputResult getCharStat(@RequestBody StockBKMoneyStatRo stockBKMoneyStatRo) {
        return bkBusiness.getCharStat(stockBKMoneyStatRo);
    }



}
