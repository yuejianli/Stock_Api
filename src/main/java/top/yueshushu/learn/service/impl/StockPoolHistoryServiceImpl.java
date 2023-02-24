package top.yueshushu.learn.service.impl;

import cn.hutool.core.date.DateUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.yueshushu.learn.assembler.StockPoolHistoryAssembler;
import top.yueshushu.learn.common.Const;
import top.yueshushu.learn.crawler.entity.StockPoolInfo;
import top.yueshushu.learn.domain.StockPoolHistoryDo;
import top.yueshushu.learn.domainservice.StockPoolHistoryDomainService;
import top.yueshushu.learn.mode.dto.StockPoolQueryDto;
import top.yueshushu.learn.service.StockPoolHistoryService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 用途描述
 *
 * @author yuejianli
 * @date 2023-02-15
 */
@Service
public class StockPoolHistoryServiceImpl implements StockPoolHistoryService {
    @Resource
    private StockPoolHistoryDomainService stockPoolHistoryDomainService;
    @Resource
    private StockPoolHistoryAssembler stockPoolHistoryAssembler;

    @Override
    public void savePoolHistory(List<StockPoolInfo> poolList) {
        List<StockPoolHistoryDo> stockPoolHistoryDoList = stockPoolHistoryAssembler.toDoList(poolList);
        if (CollectionUtils.isEmpty(stockPoolHistoryDoList)) {
            return;
        }
        stockPoolHistoryDomainService.saveBatch(stockPoolHistoryDoList);
    }

    @Override
    public Map<String, List<String>> listStockCodeByCondition(StockPoolQueryDto stockPoolQueryDto) {
        // 按照 code 进行 分组
        Map<String, List<StockPoolHistoryDo>> groupCodeMap = listByCondition(stockPoolQueryDto).stream().collect(Collectors.groupingBy(
                StockPoolHistoryDo::getCode
        ));
        Map<String, List<String>> result = new HashMap<>();
        groupCodeMap.forEach((key, value) -> {
                    List<String> dateList = value.stream().map(
                            n -> DateUtil.format(n.getCurrDate(), Const.SIMPLE_DATE_FORMAT)
                    ).collect(Collectors.toList());
                    result.put(key, dateList);
                }
        );
        return result;
    }

    @Override
    public List<StockPoolHistoryDo> listByCondition(StockPoolQueryDto stockPoolQueryDto) {
        if (stockPoolQueryDto.getStartDate() != null) {
            stockPoolQueryDto.setStartDate(DateUtil.beginOfDay(stockPoolQueryDto.getStartDate()));
        }
        if (stockPoolQueryDto.getEndDate() != null) {
            stockPoolQueryDto.setEndDate(DateUtil.endOfDay(stockPoolQueryDto.getEndDate()));
        }
        return stockPoolHistoryDomainService.listByCondition(stockPoolQueryDto);
    }
}
