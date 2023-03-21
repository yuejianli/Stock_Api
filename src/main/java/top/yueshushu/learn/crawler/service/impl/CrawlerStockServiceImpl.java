package top.yueshushu.learn.crawler.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.yueshushu.learn.assembler.StockAssembler;
import top.yueshushu.learn.common.ResultCode;
import top.yueshushu.learn.crawler.crawler.CrawlerService;
import top.yueshushu.learn.crawler.entity.DownloadStockInfo;
import top.yueshushu.learn.crawler.entity.StockBigDealInfo;
import top.yueshushu.learn.crawler.service.CrawlerStockService;
import top.yueshushu.learn.domain.StockDo;
import top.yueshushu.learn.domainservice.StockDomainService;
import top.yueshushu.learn.entity.Stock;
import top.yueshushu.learn.enumtype.DataFlagType;
import top.yueshushu.learn.enumtype.KType;
import top.yueshushu.learn.mode.info.StockShowInfo;
import top.yueshushu.learn.mode.ro.StockRo;
import top.yueshushu.learn.response.OutputResult;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName:StockBaseServiceImpl
 * @Description TODO
 * @Author 岳建立
 * @Date 2021/11/14 0:04
 * @Version 1.0
 **/
@Service
@Slf4j
public class CrawlerStockServiceImpl implements CrawlerStockService {
    @Resource
    private StockDomainService stockDomainService;
    @Resource
    private StockAssembler stockAssembler;

    @Resource
    private CrawlerService crawlerService;

    @Override
    public OutputResult getCrawlerStockInfoByCode(String stockCode) {
        Stock stock = stockAssembler.doToEntity(
                stockDomainService.getByCode(stockCode)
        );
        if (stock == null){
            return OutputResult.buildFail(
                    ResultCode.STOCK_CODE_ERROR
            );
        }
        StockShowInfo nowInfo = crawlerService.getNowInfo(stock.getFullCode());
        nowInfo.setCode(stock.getCode());
        nowInfo.setFullCode(
                stock.getFullCode()
        );
        nowInfo.setExchange(stock.getExchange());
        log.info("获取当前的股票{} 对象信息是:{}",stockCode,nowInfo);
        return OutputResult.buildSucc(
            nowInfo
        );
    }

    @Override
    public OutputResult getCrawlerLine(String code, Integer type) {
        //获取类型
        KType kType = Optional.ofNullable(KType.getKType(type)).orElse(
                KType.MIN
        );
        Stock stock = stockAssembler.doToEntity(
                stockDomainService.getByFullCode(code)
        );
        if(null ==stock){
            return OutputResult.buildAlert(ResultCode.STOCK_CODE_ERROR);
        }
        String result="";
        switch (kType){
            case MIN:{
                result=crawlerService.getMinUrl(stock.getFullCode());
                break;
            }
            case DAY:{
                result=crawlerService.getDayUrl(stock.getFullCode());
                break;
            }
            case WEEK:{
                result=crawlerService.getWeekUrl(stock.getFullCode());
                break;
            }
            case MONTH:{
                result=crawlerService.getMonthUrl(stock.getFullCode());
                break;
            }
            default:{
                break;
            }
        }
        return OutputResult.buildSucc(result);
    }

    @Override
    @Async
    public OutputResult stockAsync(StockRo stockRo) {
        //时间计数器
        TimeInterval timer = DateUtil.timer();
        timer.start();
        List<DownloadStockInfo> downloadStockInfoList = crawlerService.getStockList();
        if(CollectionUtils.isEmpty(downloadStockInfoList)){
            log.error("同步时未获取到股票列表信息");
            return OutputResult.buildFail(ResultCode.STOCK_ASYNC_FAIL);
        }
        log.info(">>获取网络股票信息并转换使用时间:{}",timer.intervalMs());
        //获取到当前的股票列表信息
        List<String> allStockCodeList = stockDomainService.listAllCode();
        //进行批量保存
        List<StockDo> stockList=new ArrayList<>();
        Date now = DateUtil.date();
        downloadStockInfoList.stream().forEach(
                n->{
                    if(!allStockCodeList.contains(n.getCode())){
                        StockDo stockDo = stockAssembler.downInfoToDO(n);
                        stockDo.setCreateUser("async");
                        stockDo.setCreateTime(now);
                        stockDo.setFlag(DataFlagType.NORMAL.getCode());
                        stockList.add(stockDo);
                    }
                }
        );
        if (CollectionUtils.isEmpty(stockList)) {
            return OutputResult.buildSucc(ResultCode.STOCK_ASYNC_NO_CHANGE);
        }
        log.info("本次同步时增加的股票编码依次为:{}",
                stockList.stream().map(
                        StockDo::getCode
                ).collect(
                        Collectors.toList()
                ));
        boolean saveBatch = stockDomainService.saveBatch(stockList, 100);
        if (!saveBatch) {
            log.info("同步数据失败 {}");
        }
        log.info("同步信息到数据库共用时 {}", timer.intervalMs());
        return OutputResult.buildSucc();
    }

    @Override
    public OutputResult getCrawlerPrice(String fullCode) {
        Map<String, BigDecimal> valueMap = crawlerService.sinaGetPrice(Collections.singletonList(fullCode));
        if (CollectionUtils.isEmpty(valueMap)) {
            return OutputResult.buildSucc(BigDecimal.ZERO);
        }
        BigDecimal[] data = valueMap.values().toArray(new BigDecimal[valueMap.size()]);
        return OutputResult.buildSucc(data[0]);
    }

    @Override
    public OutputResult<List<StockBigDealInfo>> getBigDealList(String fullCode, Integer minVolume, String day) {
        return OutputResult.buildSucc(
                crawlerService.parseBigDealByCode(fullCode, minVolume, day)
        );
    }
}
