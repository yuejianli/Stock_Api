package top.yueshushu.learn.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yueshushu.learn.annotation.AuthToken;
import top.yueshushu.learn.business.StockBusiness;
import top.yueshushu.learn.common.ResultCode;
import top.yueshushu.learn.crawler.entity.DBStockInfo;
import top.yueshushu.learn.enumtype.DBStockType;
import top.yueshushu.learn.mode.info.StockInfo;
import top.yueshushu.learn.mode.ro.StockRo;
import top.yueshushu.learn.mode.vo.StockVo;
import top.yueshushu.learn.response.OutputResult;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 股票信息基本表 我是自定义的
 * </p>
 *
 * @author 岳建立
 * @date 2022-01-02
 */
@RestController
@RequestMapping("/stock")
@Api("股票信息")
public class StockController {
    @Resource
    private StockBusiness stockBusiness;
    @PostMapping("/list")
    @ApiOperation("查询所有的股票信息")
    public OutputResult<StockInfo> list(@RequestBody StockRo stockRo){
        return stockBusiness.listStock(stockRo);
    }

    @PostMapping("/stockInfo")
    @ApiOperation("根据股票编码,获取股票的相关信息")
    public OutputResult<StockVo> getStockInfo(@RequestBody StockRo stockRo) {
        if (!StringUtils.hasText(stockRo.getCode())) {
            return OutputResult.buildAlert(
                    ResultCode.STOCK_CODE_IS_EMPTY
            );
        }
        return stockBusiness.getStockInfo(stockRo.getCode());
    }

    @ApiOperation("查询所有的打版股票信息")
    @PostMapping("/dbList")
    @AuthToken
    public OutputResult<List<DBStockInfo>> dbList(@RequestBody StockRo stockRo) {
        DBStockType dbStockType = DBStockType.getStockType(stockRo.getType());
        if (null == dbStockType) {
            return OutputResult.buildAlert(ResultCode.STOCK_TYPE_IS_EMPTY);
        }
        return stockBusiness.dbList(dbStockType);
    }

    @ApiOperation("查询所有的将要打版股票信息  大于9.5% ")
    @PostMapping("/willDbList")
    @AuthToken
    public OutputResult<List<DBStockInfo>> willDbList(@RequestBody StockRo stockRo) {
        DBStockType dbStockType = DBStockType.getStockType(stockRo.getType());
        if (null == dbStockType) {
            return OutputResult.buildAlert(ResultCode.STOCK_TYPE_IS_EMPTY);
        }
        return stockBusiness.willDbList(dbStockType);
    }
}
