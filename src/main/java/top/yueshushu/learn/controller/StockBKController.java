package top.yueshushu.learn.controller;

import cn.hutool.core.date.DateUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yueshushu.learn.annotation.AuthToken;
import top.yueshushu.learn.business.BKBusiness;
import top.yueshushu.learn.common.Const;
import top.yueshushu.learn.common.ResultCode;
import top.yueshushu.learn.crawler.entity.BKMoneyInfo;
import top.yueshushu.learn.enumtype.DBStockType;
import top.yueshushu.learn.mode.ro.StockBKMoneyStatRo;
import top.yueshushu.learn.mode.vo.StockBKVo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.response.PageResponse;

import javax.annotation.Resource;
import java.util.Date;
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


    @ApiOperation("查询某个版块历史记录列表,形成图表形式")
    @PostMapping("/getCharStat")
    @AuthToken
    public OutputResult getCharStat(@RequestBody StockBKMoneyStatRo stockBKMoneyStatRo) {
        handlerDate(stockBKMoneyStatRo);
        return bkBusiness.getCharStat(stockBKMoneyStatRo);
    }


    @ApiOperation("同步所有的股票对应的概念版块等信息")
    @PostMapping("/asyncStockBk")
    @AuthToken
    public OutputResult asyncStockBk(@RequestBody StockBKMoneyStatRo stockBKMoneyStatRo) {

        DBStockType stockType = DBStockType.getStockType(stockBKMoneyStatRo.getDbStockType());
        if (null == stockType) {
            stockType = DBStockType.SH_SZ;
        }
        return bkBusiness.asyncStockBk(stockType);
    }

    @ApiOperation("查询某个版块历史记录列表")
    @PostMapping("/findHistory")
    @AuthToken
    public OutputResult findHistory(@RequestBody StockBKMoneyStatRo stockBKMoneyStatRo) {
        if (!StringUtils.hasText(stockBKMoneyStatRo.getBkCode())) {
            return OutputResult.buildSucc(PageResponse.emptyPageResponse());
        }

        handlerDate(stockBKMoneyStatRo);
        return bkBusiness.findHistory(stockBKMoneyStatRo);
    }


    @ApiOperation("展示某个股票的版块信息")
    @PostMapping("/listCodeBkInfo")
    @AuthToken
    public OutputResult listCodeBkInfo(@RequestBody StockBKMoneyStatRo stockBKMoneyStatRo) {
        return bkBusiness.listCodeBkInfo(stockBKMoneyStatRo);
    }

    /**
     * 处理日期
     *
     * @param stockBKMoneyStatRo 历史记录 日期对象调整
     */
    private void handlerDate(StockBKMoneyStatRo stockBKMoneyStatRo) {
        Date now = DateUtil.date();
        if (!StringUtils.hasText(stockBKMoneyStatRo.getEndDate())) {
            Date endDate = DateUtil.offsetDay(now, -1);
            stockBKMoneyStatRo.setEndDate(DateUtil.format(endDate, Const.SIMPLE_DATE_FORMAT));
        }

        if (!StringUtils.hasText(stockBKMoneyStatRo.getStartDate())) {
            Date startDate = DateUtil.offsetDay(now, -15);
            stockBKMoneyStatRo.setStartDate(DateUtil.format(startDate, Const.SIMPLE_DATE_FORMAT));
        }
    }


}
