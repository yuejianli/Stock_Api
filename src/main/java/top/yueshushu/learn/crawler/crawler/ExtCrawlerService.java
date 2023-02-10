package top.yueshushu.learn.crawler.crawler;

import top.yueshushu.learn.crawler.entity.*;
import top.yueshushu.learn.enumtype.DBStockType;
import top.yueshushu.learn.enumtype.StockPoolType;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 扩展的接口 Serivce
 *
 * @author Yue Jianli
 * @date 2023-02-02
 */

public interface ExtCrawlerService {
    /**
     * 查询版块列表
     */
    List<BKInfo> findAllBkList();

    /**
     * 查询今日版块注入集合
     */
    List<BKMoneyInfo> findTodayBkMoneyList();


    /**
     * 查询概念列表
     */
    List<BKInfo> findAllGnList();

    /**
     * 查询概念今日版块注入集合
     */
    List<BKMoneyInfo> findTodayGnMoneyList();

    /**
     * 查询地域列表
     */
    List<BKInfo> findAllDyList();

    /**
     * 查询地域今日版块注入集合
     */
    List<BKMoneyInfo> findTodayDyMoneyList();

    /**
     * 根据股票编码查询关联的概念信息
     *
     * @param code 股票编码
     */
    List<StockBKStockInfo> findRelationBkListByCode(String code);

    /**
     * 查询打版的股票集合
     *
     * @return 获取股票的全量列表信息
     */
    default List<DBStockInfo> findDbStock(DBStockType dbStockType) {
        return Collections.EMPTY_LIST;
    }

    default List<DBStockInfo> findWillDbStockList(DBStockType dbStockType) {
        return Collections.EMPTY_LIST;
    }


    default List<StockPoolInfo> findPoolByType(StockPoolType stockPoolType, Date currentDate) {
        return Collections.EMPTY_LIST;
    }
}
