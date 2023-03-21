package top.yueshushu.learn.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yueshushu.learn.annotation.AuthToken;
import top.yueshushu.learn.business.PriceImageBusiness;
import top.yueshushu.learn.business.StockCrawlerBusiness;
import top.yueshushu.learn.common.ResultCode;
import top.yueshushu.learn.crawler.entity.DBStockInfo;
import top.yueshushu.learn.crawler.entity.StockBKStockInfo;
import top.yueshushu.learn.enumtype.DBStockType;
import top.yueshushu.learn.mode.info.StockShowInfo;
import top.yueshushu.learn.mode.ro.StockRo;
import top.yueshushu.learn.response.OutputResult;

import javax.annotation.Resource;
import java.util.List;

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
    @Resource
    private PriceImageBusiness priceImageBusiness;

    @ApiOperation("查询股票的基本信息")
    @PostMapping("/getStockInfo")
    public OutputResult<StockShowInfo> getStockInfo(@RequestBody StockRo stockRo) {
        if (!StringUtils.hasText(stockRo.getCode())) {
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
        if (stockRo.getType() == null) {
            return OutputResult.buildAlert(
                    ResultCode.STOCK_KLINE_IS_EMPTY
            );
        }
        return stockCrawlerBusiness.getStockKline(stockRo);
    }

    @ApiOperation("同步股票的K线图")
    @PostMapping("/saveStockKline")
    public OutputResult saveStockKline(@RequestBody StockRo stockRo) {
        if (!CollectionUtils.isEmpty(stockRo.getCodes())) {
            return OutputResult.buildAlert(
                    ResultCode.STOCK_CODE_IS_EMPTY
            );
        }
        // 处理一下图片保存信息。
        priceImageBusiness.batchSavePriceImage(stockRo.getCodes(), true);
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
        if (!StringUtils.hasText(stockRo.getCode())) {
            return OutputResult.buildAlert(
                    ResultCode.STOCK_CODE_IS_EMPTY
            );
        }
        //设置 是股票同步
        stockRo.setExchange(1);
        return stockCrawlerBusiness.stockHistoryAsync(stockRo);
    }

    @ApiOperation("查询所有的打版股票信息")
    @PostMapping("/dbList")
    @AuthToken
    public OutputResult<List<DBStockInfo>> dbList(@RequestBody StockRo stockRo) {
        DBStockType dbStockType = DBStockType.getStockType(stockRo.getType());
        if (null == dbStockType) {
            return OutputResult.buildAlert(ResultCode.STOCK_TYPE_IS_EMPTY);
        }
        return stockCrawlerBusiness.dbList(dbStockType);
    }

    @ApiOperation("查询所有的将要打版股票信息  大于9.5% ")
    @PostMapping("/willDbList")
    @AuthToken
    public OutputResult<List<DBStockInfo>> willDbList(@RequestBody StockRo stockRo) {
        DBStockType dbStockType = DBStockType.getStockType(stockRo.getType());
        if (null == dbStockType) {
            return OutputResult.buildAlert(ResultCode.STOCK_TYPE_IS_EMPTY);
        }
        return stockCrawlerBusiness.willDbList(dbStockType);
    }

    @ApiOperation("查询股票关联的版块信息")
    @PostMapping("/listRelationBk")
    @AuthToken
    public OutputResult<List<StockBKStockInfo>> listRelationBk(@RequestBody StockRo stockRo) {
        if (!StringUtils.hasText(stockRo.getCode())) {
            return OutputResult.buildAlert(ResultCode.STOCK_CODE_IS_EMPTY);
        }
        return stockCrawlerBusiness.listRelationBk(stockRo.getCode());
    }
}