package top.yueshushu.learn.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yueshushu.learn.annotation.AuthToken;
import top.yueshushu.learn.business.StockCrawlerBusiness;
import top.yueshushu.learn.common.ResultCode;
import top.yueshushu.learn.mode.info.StockShowInfo;
import top.yueshushu.learn.mode.ro.StockRo;
import top.yueshushu.learn.response.OutputResult;

import javax.annotation.Resource;

/**
 * @Description: 股票爬虫的Controller
 * @Author 岳建立
 * @Date 2021/11/12 23:03
 **/
@RestController
@RequestMapping("/stockCrawler")
@ApiOperation("股票爬虫程序")
public class StockCrawlerController {
    @Resource
    private StockCrawlerBusiness stockCrawlerBusiness;

    @ApiOperation("查询股票的基本信息")
    @PostMapping("/getStockInfo")
    public OutputResult<StockShowInfo> getStockInfo(@RequestBody StockRo stockRo) {
        if (!StringUtils.hasText(stockRo.getCode())){
            return OutputResult.buildAlert(
                    ResultCode.STOCK_CODE_IS_EMPTY
            );
        }
        return stockCrawlerBusiness.getStockInfo(stockRo);
    }
    @ApiOperation("查询股票的K线图")
    @PostMapping("/getStockKline")
    public OutputResult<String> getStockKline(@RequestBody StockRo stockRo) {
        if (!StringUtils.hasText(stockRo.getCode())){
            return OutputResult.buildAlert(
                    ResultCode.STOCK_CODE_IS_EMPTY
            );
        }
        if (stockRo.getType() == null){
            return OutputResult.buildAlert(
                    ResultCode.STOCK_KLINE_IS_EMPTY
            );
        }
        return stockCrawlerBusiness.getStockKline(stockRo);
    }
    @ApiOperation("股票列表同步")
    @PostMapping("/stockAsync")
    @AuthToken
    public OutputResult<String> stockAsync(@RequestBody StockRo stockRo) {
        return stockCrawlerBusiness.stockAsync(stockRo);
    }
    /**
     * 关于历史记录的处理
     */
    @ApiOperation("同步股票的历史记录")
    @PostMapping("/stockHistoryAsync")
    @AuthToken
    public OutputResult<String> stockHistoryAsync(@RequestBody StockRo stockRo) {
        if (!StringUtils.hasText(stockRo.getCode())){
            return OutputResult.buildAlert(
                    ResultCode.STOCK_CODE_IS_EMPTY
            );
        }
        //设置 是股票同步
        stockRo.setExchange(1);
        return stockCrawlerBusiness.stockHistoryAsync(stockRo);
    }
}