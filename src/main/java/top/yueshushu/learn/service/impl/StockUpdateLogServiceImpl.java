package top.yueshushu.learn.service.impl;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.yueshushu.learn.assembler.StockUpdateLogAssembler;
import top.yueshushu.learn.common.Const;
import top.yueshushu.learn.domain.StockUpdateLogDo;
import top.yueshushu.learn.domainservice.StockUpdateLogDomainService;
import top.yueshushu.learn.mode.ro.StockUpdateLogRo;
import top.yueshushu.learn.mode.vo.StockUpdateLogVo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.response.PageResponse;
import top.yueshushu.learn.service.StockUpdateLogService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description 股票更新历史
 * @Author yuejianli
 * @Date 2022/6/4 10:11
 **/
@Service
public class StockUpdateLogServiceImpl implements StockUpdateLogService {
    @Resource
    private StockUpdateLogDomainService stockUpdateLogDomainService;
    @Resource
    private StockUpdateLogAssembler stockUpdateLogAssembler;
    @Override
    public OutputResult<PageResponse<StockUpdateLogVo>> pageLastMonth(StockUpdateLogRo stockUpdateLogRo) {
        Page<StockUpdateLogDo> pageGithubResult = PageHelper.startPage(stockUpdateLogRo.getPageNum(), stockUpdateLogRo.getPageSize());
        //查询当前的时间和最开始一个月的时间
        List<StockUpdateLogDo> stockUpdateLogDoList = stockUpdateLogDomainService.listStockLogAndDate(
                stockUpdateLogRo.getCode()
                , DateUtil.parse(
                        stockUpdateLogRo.getStartDate(), Const.SIMPLE_DATE_FORMAT
                ),
                DateUtil.parse(
                        stockUpdateLogRo.getEndDate(), Const.SIMPLE_DATE_FORMAT
                ));

        if (CollectionUtils.isEmpty(stockUpdateLogDoList)){
            return OutputResult.buildSucc(
                    PageResponse.emptyPageResponse()
            );
        }
        List<StockUpdateLogVo> pageResultList = new ArrayList<>(stockUpdateLogDoList.size());
        stockUpdateLogDoList.forEach(
                n->{
                    pageResultList.add(
                            stockUpdateLogAssembler.entityToVo(stockUpdateLogAssembler.doToEntity(n))
                    );
                }
        );
        return OutputResult.buildSucc(new PageResponse<>(pageGithubResult.getTotal(), pageResultList));
    }
}
