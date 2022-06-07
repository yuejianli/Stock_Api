package top.yueshushu.learn.crawler.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.yueshushu.learn.assembler.StockHistoryAssembler;
import top.yueshushu.learn.common.ResultCode;
import top.yueshushu.learn.crawler.crawler.CrawlerService;
import top.yueshushu.learn.crawler.entity.StockHistoryCsvInfo;
import top.yueshushu.learn.crawler.service.CrawlerStockHistoryService;
import top.yueshushu.learn.domain.StockHistoryDo;
import top.yueshushu.learn.domainservice.StockHistoryDomainService;
import top.yueshushu.learn.helper.DateHelper;
import top.yueshushu.learn.mode.ro.StockRo;
import top.yueshushu.learn.response.OutputResult;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName:StockHistoryServiceImpl
 * @Description TODO
 * @Author 岳建立
 * @Date 2021/11/14 11:24
 * @Version 1.0
 **/
@Service
@Slf4j
public class CrawlerStockHistoryServiceImpl implements CrawlerStockHistoryService {

    @Resource
    private CrawlerService crawlerService;
    @Resource
    private StockHistoryDomainService stockHistoryDomainService;
    @Resource
    private StockHistoryAssembler stockHistoryAssembler;
    @Resource
    private DateHelper dateHelper;

    @Override
    public OutputResult stockCrawlerHistoryAsync(StockRo stockRo) {
       try{
           //时间计数器
           TimeInterval timer = DateUtil.timer();
           timer.start();
           //处理读取数据
           List<StockHistoryCsvInfo> stockHistoryCsvInfoList = crawlerService.parseStockHistoryList(
                   "1"+stockRo.getCode(),
                   stockRo.getStartDate(), stockRo.getEndDate());
           if(CollectionUtils.isEmpty(stockHistoryCsvInfoList)){
               log.error("未获取到股票{} 的在 {} 和{} 之间的历史数据",
                       stockRo.getCode(),stockRo.getStartDate(),stockRo.getEndDate());
               return OutputResult.buildFail(ResultCode.STOCK_HIS_ASYNC_FAIL);
           }
           log.info(">>获取{}历史信息并转换使用时间:{},转换了{}条",
                   stockRo.getCode(),
                   timer.intervalMs(),
                   stockHistoryCsvInfoList.size());
           //将这期间的数据全部删除
           // stockHistoryDomainService.deleteAsyncRangeDateData(stockRo.getCode(),
           //        stockRo.getStartDate(),
           //       stockRo.getEndDate());
           // log.info("删除了股票{} 的在 {} 和{} 之间的历史数据",
           //         stockRo.getCode(),stockRo.getStartDate(),stockRo.getEndDate());

           //查询一下，这同步期间内的股票信息记录，主要查日期列表
           List<String> existDateList = stockHistoryDomainService.listDate(
                   stockRo.getCode(), stockRo.getStartDate(), stockRo.getEndDate()
           );
           log.info("股票{} 的在 {} 和{} 之间的历史数据,共有{}条",
                   stockRo.getCode(),stockRo.getStartDate(),stockRo.getEndDate(),existDateList.size());
           //进行批量保存
           List<StockHistoryDo> stockHistoryDoList=new ArrayList<>();
           stockHistoryCsvInfoList.forEach(
                   n->{
                       if (!existDateList.contains(n.getCurrDate())){
                           StockHistoryDo stockHistoryDo = stockHistoryAssembler.csvInfoToDo(
                                   n
                           );
                           stockHistoryDo.setCurrDate(
                                   DateUtil.parseDate(
                                           n.getCurrDate()
                                   ).toLocalDateTime()
                           );
                           stockHistoryDoList.add(stockHistoryDo);
                       }
                   }
           );
           log.info("股票{} 的在 {} 和{} 之间的历史数据,共需要同步有{}条",
                   stockRo.getCode(),stockRo.getStartDate(),stockRo.getEndDate(),stockHistoryDoList.size());
           stockHistoryDomainService.saveBatch(stockHistoryDoList,500);
           log.info("同步股票{} 的历史数据信息到数据库共用时 {}",stockRo.getCode(),timer.intervalMs());
           return OutputResult.buildSucc(ResultCode.STOCK_HIS_ASYNC_SUCCESS);
       }catch (Exception e){
           return OutputResult.buildFail(e.getMessage());
       }
    }

    @Override
    public OutputResult easyMoneyYesStockHistory(List<String> codeList) {
       try{
           Date yesDay = DateUtil.offsetDay(DateUtil.date(),-1);
           DateTime beforeLastWorking = dateHelper.getBeforeLastWorking(yesDay);

           //处理读取数据
           List<StockHistoryCsvInfo> stockHistoryCsvInfoList = crawlerService.parseEasyMoneyYesHistory(codeList,beforeLastWorking);
           // 上一个交易日的数据查询出来了。 进行同步
           List<StockHistoryDo> stockHistoryDoList=new ArrayList<>();
           stockHistoryCsvInfoList.forEach(
                   n->{
                       StockHistoryDo stockHistoryDo = stockHistoryAssembler.csvInfoToDo(
                               n
                       );
                       stockHistoryDo.setCurrDate(
                               DateUtil.parseDate(
                                       n.getCurrDate()
                               ).toLocalDateTime()
                       );
                       stockHistoryDoList.add(stockHistoryDo);
                   }
           );
           log.info("股票集合{}在东方财富网站同步最近的股票记录,共需要同步有{}条",codeList,stockHistoryCsvInfoList.size());
           stockHistoryDomainService.saveBatch(stockHistoryDoList,50);
           return OutputResult.buildSucc(ResultCode.STOCK_HIS_ASYNC_SUCCESS);
       }catch (Exception e){
           return OutputResult.buildFail(e.getMessage());
       }
    }
}