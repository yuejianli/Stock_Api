package top.yueshushu.learn.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import org.springframework.stereotype.Service;
import top.yueshushu.learn.common.Const;
import top.yueshushu.learn.crawler.business.CrawlerStockBusiness;
import top.yueshushu.learn.crawler.business.CrawlerStockHistoryBusiness;
import top.yueshushu.learn.enumtype.SyncStockHistoryType;
import top.yueshushu.learn.mode.info.StockShowInfo;
import top.yueshushu.learn.mode.ro.StockRo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.service.StockCrawlerService;
import top.yueshushu.learn.service.cache.StockCacheService;
import top.yueshushu.learn.util.BigDecimalUtil;
import top.yueshushu.learn.util.StockUtil;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName:StockCrawlerServiceImpl
 * @Description 股票爬虫时使用
 * @Author 岳建立
 * @Date 2021/11/12 23:07
 * @Version 1.0
 **/
@Service
public class StockCrawlerServiceImpl implements StockCrawlerService {
    @Resource
    private StockCacheService  stockCacheService;
    @Resource
    private CrawlerStockBusiness crawlerStockBusiness;
    @Resource
    private CrawlerStockHistoryBusiness crawlerStockHistoryBusiness;


    @Override
    public OutputResult<StockShowInfo> getStockInfo(StockRo stockRo) {
        return crawlerStockBusiness.getStockInfo(stockRo.getCode());
    }

    @Override
    public OutputResult<String> getStockKline(StockRo stockRo) {
        return crawlerStockBusiness.getStockKline(stockRo);
    }

    @Override
    public OutputResult<String> stockAsync(StockRo stockRo) {
        return crawlerStockBusiness.stockAsync(stockRo);
    }

    @Override
    public OutputResult stockHistoryAsync(StockRo stockRo) {
        //处理日期信息
        OutputResult handlerResult=handlerDate(stockRo);
        if(null!=handlerResult){
            return handlerResult;
        }
        return crawlerStockHistoryBusiness.stockHistoryAsync(stockRo);
    }
    /**
     * 历史交易信息同步时，处理日期.
     * @param stockRo
     */
    private OutputResult handlerDate(StockRo stockRo) {
        SyncStockHistoryType syncRangeType = SyncStockHistoryType.getSyncRangeType(stockRo.getType());
        if(syncRangeType==null){
            return OutputResult.buildAlert("不支持的同步交易范围");
        }
        String Date_formatter = Const.STOCK_DATE_FORMAT;
        Date now=DateUtil.date();
        String startDate=DateUtil.format(
                now,Date_formatter
        );
        String endDate=DateUtil.format(
                now,Date_formatter
        );;
        switch (syncRangeType){
            case SELF:{
                startDate= DateUtil.format(
                        DateUtil.parse(
                                stockRo.getStartDate(),
                                Const.DATE_FORMAT
                        )
                        ,Date_formatter);
                endDate= DateUtil.format(
                        DateUtil.parse(
                                stockRo.getEndDate(),
                                Const.DATE_FORMAT
                        )
                        ,Date_formatter);
                break;
            }
            case WEEK:{
                DateTime dateTime = DateUtil.offsetWeek(now, -1);
                startDate=DateUtil.format(dateTime,Date_formatter);
                break;
            }
            case MONTH:{
                DateTime dateTime = DateUtil.offsetMonth(now, -1);
                startDate=DateUtil.format(dateTime,Date_formatter);
                break;
            }
            case YEAR:{
                DateTime dateTime = DateUtil.offsetMonth(now, -1*12);
                startDate=DateUtil.format(dateTime,Date_formatter);
                break;
            }
            case THREE_YEAR:{
                DateTime dateTime = DateUtil.offsetMonth(now, -1*12*3);
                startDate=DateUtil.format(dateTime,Date_formatter);
                break;
            }
            case FIVE_YEAR:{
                DateTime dateTime = DateUtil.offsetMonth(now, -1*12*5);
                startDate=DateUtil.format(dateTime,Date_formatter);
                break;
            }
            case TEN_YEAR:{
                DateTime dateTime = DateUtil.offsetMonth(now, -1*12*10);
                startDate=DateUtil.format(dateTime,Date_formatter);
                break;
            }
            case ALL:{
                // 1984年11月18日 中国第一个股票交易
                startDate="19841118";
                break;
            }
        }
        stockRo.setStartDate(startDate);
        stockRo.setEndDate(endDate);
        return null;
    }

    @Override
    public void updateCodePrice(String code) {
        //生成真实数据。
        generateRealPriceData(code);
    }
    /**
     * 生成虚拟的股票信息  P(t) = P(t-1) + random(0,1)*1 - 0.5
     * @param code 股票编码
     * @return 返回生成的虚拟的股票价格
     */
    private void generateMockPriceData(String code) {
        //获取当前的价格
        BigDecimal nowCachePrice = stockCacheService.getNowCachePrice(code);
        BigDecimal randPrice = BigDecimalUtil.toBigDecimal(Math.random()*1-0.5);
        BigDecimal newPrice = BigDecimalUtil.addBigDecimal(nowCachePrice,randPrice);
        stockCacheService.setNowCachePrice(
                code,newPrice
         );
    }

    private void generateRealPriceData(String code){
         StockRo stockRo = new StockRo();
         //获取当前的股票
         String fullCode = StockUtil.getFullCode(code);
         stockRo.setCode(fullCode);
         //获取当前的价格
        OutputResult outputResult= crawlerStockBusiness.getStockPrice(fullCode);
         //获取信息
        String priceReturn = (String) (outputResult.getData());
        //将这个信息进行转换，转换成对应的 BigDecimal
         BigDecimal price = BigDecimalUtil.toBigDecimal(priceReturn);

         stockCacheService.setNowCachePrice(
                 code,price
         );

    }
}
