package top.yueshushu.learn.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.yueshushu.learn.assembler.StockPoolHistoryAssembler;
import top.yueshushu.learn.crawler.entity.StockPoolInfo;
import top.yueshushu.learn.domain.StockPoolHistoryDo;
import top.yueshushu.learn.domainservice.StockPoolHistoryDomainService;
import top.yueshushu.learn.service.StockPoolHistoryService;

import javax.annotation.Resource;
import java.util.List;

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
}
