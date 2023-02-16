package top.yueshushu.learn.service;

import top.yueshushu.learn.crawler.entity.StockPoolInfo;

import java.util.List;

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

}
