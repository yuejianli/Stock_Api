package top.yueshushu.learn.business.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import top.yueshushu.learn.business.StockSelectedBusiness;
import top.yueshushu.learn.common.Const;
import top.yueshushu.learn.common.ResultCode;
import top.yueshushu.learn.entity.Stock;
import top.yueshushu.learn.enumtype.SyncStockHistoryType;
import top.yueshushu.learn.helper.DateHelper;
import top.yueshushu.learn.mode.ro.IdRo;
import top.yueshushu.learn.mode.ro.StockRo;
import top.yueshushu.learn.mode.ro.StockSelectedRo;
import top.yueshushu.learn.mode.vo.StockHistoryVo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.response.PageResponse;
import top.yueshushu.learn.service.*;
import top.yueshushu.learn.service.cache.StockCacheService;
import top.yueshushu.learn.util.PageUtil;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * @Description 自选股票实现编排层
 * @Author yuejianli
 * @Date 2022/5/20 23:54
 **/
@Service
@Slf4j
public class StockSelectedBusinessImpl implements StockSelectedBusiness {
    @Resource
    private StockSelectedService stockSelectedService;
    @Resource
    private ConfigService configService;
    @Resource
    private StockService stockService;
    @Resource
    private StockHistoryService stockHistoryService;
    @Resource
    private DateHelper dateHelper;
    @Resource
    private StockCrawlerService stockCrawlerService;
    @Resource
    private StockCacheService stockCacheService;

    @Override
    public OutputResult listSelected(StockSelectedRo stockSelectedRo) {
        return stockSelectedService.pageSelected(
                stockSelectedRo
        );
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public OutputResult add(StockSelectedRo stockSelectedRo) {
        //1. 验证股票是否已经存在
        int maxSelectedNum = configService.getMaxSelectedNumByUserId(
                stockSelectedRo.getUserId()
        );
        // 根据股票的编码，获取相应的股票记录信息
        Stock stock = stockService.selectByCode(stockSelectedRo.getStockCode());
        if (null == stock) {
            return OutputResult.buildAlert(
                    ResultCode.STOCK_CODE_NO_EXIST
            );
        }
        // 验证添加用户
        OutputResult addValidateResult = stockSelectedService
                .validateAdd(stockSelectedRo, maxSelectedNum);
        if (!addValidateResult.getSuccess()) {
            return addValidateResult;
        }
        //获取到股票的名称，进行添加到股票自选对象里面.
        stockSelectedService.add(
                stockSelectedRo, stock.getName()
        );
        syncCodeInfo(stockSelectedRo.getStockCode());
        // 处理缓存信息
        return OutputResult.buildSucc(
                ResultCode.SUCCESS
        );
    }

    /**
     * 添加股票到自选后，同步股票的信息记录
     * @param stockCode 股票编码
     */
    @Async
    public void syncCodeInfo(String stockCode) {
        // 设置当前的价格信息
        stockCrawlerService.updateCodePrice(stockCode);
        // 获取价格信息.
        BigDecimal nowCachePrice = stockCacheService.getNowCachePrice(stockCode);
        stockCacheService.setLastBuyCachePrice(stockCode, nowCachePrice);
        stockCacheService.setLastSellCachePrice(stockCode, nowCachePrice);
        stockCacheService.setYesterdayCloseCachePrice(stockCode, nowCachePrice);

        // 同步股票的历史交易记录。 同步最近一个月的.
        StockRo stockRo = new StockRo();
        stockRo.setCode(stockCode);
        stockRo.setType(SyncStockHistoryType.MONTH.getCode());
        stockCrawlerService.stockHistoryAsync(stockRo);
    }

    @Override
    public OutputResult delete(IdRo idRo, int userId) {
        // 删除相关的记录
       OutputResult deleteResult = stockSelectedService.delete(
                idRo,userId
        );
        if (!deleteResult.getSuccess()){
            return deleteResult;
        }
        return OutputResult.buildSucc();
    }

    @Override
    public OutputResult deleteByCode(StockSelectedRo stockSelectedRo) {
        // 删除相关的记录
        OutputResult deleteResult = stockSelectedService.deleteByCode(
                stockSelectedRo
        );
        if (!deleteResult.getSuccess()){
            return deleteResult;
        }
        return OutputResult.buildSucc();
    }

    @Override
    public OutputResult yesHistory(StockSelectedRo stockSelectedRo) {
        //1. 查询出当前用户下所有正常可用的股票列表
        Map<String,String> stockMap = stockSelectedService.listStockCodeByUserId(
                stockSelectedRo.getUserId()
        );
        List<String> stockList = new ArrayList<>(stockMap.keySet());
        if (CollectionUtils.isEmpty(stockList)){
            return OutputResult.buildSucc(
                    PageResponse.emptyPageResponse()
            );
        }
        //不为空的话，进行拆分.
        List<String> list = PageUtil.startPage(
                stockList,
                stockSelectedRo.getPageNum(),
                stockSelectedRo.getPageSize()
        );
        //如果这个时候为空的话，进行处理
        if (CollectionUtils.isEmpty(stockList)){
            return OutputResult.buildSucc(
                    new PageResponse((long) stockList.size(),
                            Collections.emptyList())
            );
        }
        //查询最近的一天，非周末，非节假日
        Date yestaryDay = DateUtil.yesterday();
        DateTime beforeLastWorking = dateHelper.getBeforeLastWorking(yestaryDay);

        List<StockHistoryVo> stockHistoryVoList = new ArrayList<>(list.size());
        for (String code: list){
            StockHistoryVo stockHistoryVo = stockHistoryService.getVoByCodeAndCurrDate(
                    code, beforeLastWorking
            );
            if (null == stockHistoryVo){
                stockHistoryVo = new StockHistoryVo();
                stockHistoryVo.setCode(
                        code
                );
                stockHistoryVo.setName(
                        stockMap.get(
                                code
                        )
                );
                stockHistoryVo.setCurrDate(
                        beforeLastWorking.toLocalDateTime()
                );
            }
            stockHistoryVoList.add(
                    stockHistoryVo
            );
        }
        return OutputResult.buildSucc(
                new PageResponse((long) stockList.size(),
                        stockHistoryVoList)
        );
    }

    @Override
    public OutputResult editNotes(StockSelectedRo stockSelectedRo) {
        return stockSelectedService.editNotes(
                stockSelectedRo
        );
    }
}
