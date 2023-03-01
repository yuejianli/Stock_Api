package top.yueshushu.learn.service.impl;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.yueshushu.learn.assembler.StockHistoryAssembler;
import top.yueshushu.learn.common.Const;
import top.yueshushu.learn.domain.StockHistoryDo;
import top.yueshushu.learn.domainservice.StockHistoryDomainService;
import top.yueshushu.learn.entity.StockHistory;
import top.yueshushu.learn.helper.DateHelper;
import top.yueshushu.learn.mode.dto.StockHistoryQueryDto;
import top.yueshushu.learn.mode.dto.StockPriceCacheDto;
import top.yueshushu.learn.mode.ro.StockDayStatRo;
import top.yueshushu.learn.mode.ro.StockRo;
import top.yueshushu.learn.mode.vo.StockHistoryVo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.response.PageResponse;
import top.yueshushu.learn.service.StockHistoryService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 股票的历史交易记录表 自定义的
 * </p>
 *
 * @author 岳建立
 * @since 2022-01-02
 */
@Service
public class StockHistoryServiceImpl  implements StockHistoryService {
    @Resource
    private StockHistoryDomainService stockHistoryDomainService;
    @Resource
    private StockHistoryAssembler stockHistoryAssembler;
    @Resource
    private DateHelper dateHelper;
    @Override
    public OutputResult pageHistory(StockRo stockRo) {
        Page<Object> pageGithubResult = PageHelper.startPage(stockRo.getPageNum(), stockRo.getPageSize());

        List<StockHistoryDo> stockHistoryDoList= stockHistoryDomainService.listStockHistoryAndDate(stockRo.getCode()
                , DateUtil.parse(
                        stockRo.getStartDate(), Const.SIMPLE_DATE_FORMAT
                ),
                DateUtil.parse(
                        stockRo.getEndDate(),Const.SIMPLE_DATE_FORMAT
                ));

        if (CollectionUtils.isEmpty(stockHistoryDoList)){
            return OutputResult.buildSucc(
                    PageResponse.emptyPageResponse()
            );
        }
        List<StockHistoryVo> pageResultList = new ArrayList<>(stockHistoryDoList.size());
        stockHistoryDoList.forEach(
                n->{
                    pageResultList.add(
                            stockHistoryAssembler.entityToVo(
                                    stockHistoryAssembler.doToEntity(n)
                            )
                    );
                }
        );
        return OutputResult.buildSucc(new PageResponse<>(
                pageGithubResult.getTotal(), pageResultList
        ));
    }

    @Override
    public List<StockPriceCacheDto> listClosePrice(List<String> codeList) {
        //查询距离当前最近的一个工作日
        Date lastWorkingDate = dateHelper.getBeforeLastWorking(DateUtil.offsetDay(DateUtil.date(), -1));
        return stockHistoryDomainService.listYesterdayClosePrice(codeList, lastWorkingDate);
    }

    @Override
    public StockHistory getLastHistory(String stockCode) {
        //查询距离当前最近的一个工作日
        Date lastWorkingDate = dateHelper.getBeforeLastWorking(DateUtil.offsetDay(DateUtil.date(), -1));
        return stockHistoryAssembler.doToEntity(
                stockHistoryDomainService.getByCodeAndCurrDate(
                        stockCode, lastWorkingDate
                )
        );
    }

    @Override
    public StockHistoryVo getVoByCodeAndCurrDate(String code, Date currDate) {
        return stockHistoryAssembler.entityToVo(
                stockHistoryAssembler.doToEntity(
                        stockHistoryDomainService.getByCodeAndCurrDate(
                                code, currDate
                        )
                )
        );
    }

    @Override
    public List<StockHistory> limit10Desc(String stockCode, Date lastDay) {
        List<StockHistoryDo> stockHistoryDoList = stockHistoryDomainService.limit10Desc(stockCode, lastDay);
        if (CollectionUtils.isEmpty(stockHistoryDoList)) {
            return Collections.EMPTY_LIST;
        }
        List<StockHistory> stockHistoryList = new ArrayList<>(stockHistoryDoList.size());
        stockHistoryDoList.forEach(
                n -> {
                    stockHistoryList.add(stockHistoryAssembler.doToEntity(n));
                }
        );
        return stockHistoryList;
    }

    @Override
    public StockHistoryVo getRecentyHistoryBeforeDate(String code, Date endDate) {
        //将日期变成这一天的最后时刻
        endDate = DateUtil.endOfDay(endDate);
        return stockHistoryAssembler.entityToVo(
                stockHistoryAssembler.doToEntity(
                        stockHistoryDomainService.getRecentyHistoryBeforeDate(
                                code, endDate
                        )
                )
        );
    }

    @Override
    public List<StockHistoryVo> getStockHistoryByCodeAndRangeDate(String code, Date startDate, Date endDate) {
        //将开始日期变成 上一天的最后时刻
        startDate = DateUtil.endOfDay(
                DateUtil.offsetDay(
                        startDate, -1
                )
        );
        //将结束日期变成这一天的最后时刻
        endDate = DateUtil.endOfDay(endDate);
        List<StockHistoryDo> stockHistoryDoList = stockHistoryDomainService.listStockHistoryAndDateAsc(
                code, startDate, endDate);

        if (CollectionUtils.isEmpty(stockHistoryDoList)){
           return Collections.emptyList();
        }
        List<StockHistoryVo> resultList = new ArrayList<>(stockHistoryDoList.size());
        stockHistoryDoList.forEach(
                n->{
                    resultList.add(
                            stockHistoryAssembler.entityToVo(
                                    stockHistoryAssembler.doToEntity(n)
                            )
                    );
                }
        );
        return resultList;
    }

    @Override
    public OutputResult pageDayRange(StockDayStatRo stockDayStatRo) {
        StockHistoryQueryDto stockHistoryQueryDto = new StockHistoryQueryDto();
        stockHistoryQueryDto.setCode(stockDayStatRo.getCode());
        stockHistoryQueryDto.setStartDate(
               DateUtil.offsetDay(
                       DateUtil.parse(
                               stockDayStatRo.getStartDate(), Const.SIMPLE_DATE_FORMAT
                       ),
                       -1
               )
        );
        stockHistoryQueryDto.setEndDate(
                DateUtil.parse(
                        stockDayStatRo.getEndDate(), Const.SIMPLE_DATE_FORMAT
                )
        );
        stockHistoryQueryDto.setStartDayNum(
                stockDayStatRo.getStartDayNum()
        );
        stockHistoryQueryDto.setEndDayNum(
                stockDayStatRo.getEndDayNum()
        );
        stockHistoryQueryDto.setMonth(stockDayStatRo.getMonth());
    
        Page<Object> pageGithubResult = PageHelper.startPage(stockDayStatRo.getPageNum(), stockDayStatRo.getPageSize());
    
        List<StockHistoryDo> stockHistoryDoList = stockHistoryDomainService.listDayRange(stockHistoryQueryDto);
    
        if (CollectionUtils.isEmpty(stockHistoryDoList)) {
            return OutputResult.buildSucc(
                    PageResponse.emptyPageResponse()
            );
        }
        List<StockHistoryVo> pageResultList = new ArrayList<>(stockHistoryDoList.size());
        stockHistoryDoList.forEach(
                n->{
                    pageResultList.add(
                            stockHistoryAssembler.entityToVo(
                                    stockHistoryAssembler.doToEntity(n)
                            )
                    );
                }
        );

        PageInfo pageInfo = new PageInfo<>(pageResultList);
        return OutputResult.buildSucc(new PageResponse<>(
                pageGithubResult.getTotal(), pageInfo.getList()
        ));
    }

    @Override
    public Date getMaxCurrentDate() {
        return stockHistoryDomainService.getMaxCurrentDate();
    }
}
