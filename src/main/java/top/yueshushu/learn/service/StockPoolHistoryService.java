package top.yueshushu.learn.service;

import top.yueshushu.learn.crawler.entity.StockPoolInfo;
import top.yueshushu.learn.domain.StockPoolHistoryDo;
import top.yueshushu.learn.mode.dto.StockPoolQueryDto;

import java.util.List;
import java.util.Map;

/**
 * @author Yue Jianli
 * @date 2023-02-15
 */

public interface StockPoolHistoryService {
    /**
     * 保存历史数据
     *
     * @param poolList 历史数据
     */
    void savePoolHistory(List<StockPoolInfo> poolList);

    Map<String, List<String>> listStockCodeByCondition(StockPoolQueryDto stockPoolQueryDto);


    List<StockPoolHistoryDo> listByCondition(StockPoolQueryDto stockPoolQueryDto);
}
