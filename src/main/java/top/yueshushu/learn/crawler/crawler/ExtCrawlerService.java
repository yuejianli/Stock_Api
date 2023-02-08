package top.yueshushu.learn.crawler.crawler;

import top.yueshushu.learn.crawler.entity.BKInfo;
import top.yueshushu.learn.crawler.entity.BKMoneyInfo;

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
    List<BKMoneyInfo> findTodayBKMoneyList();


    /**
     * 查询概念列表
     */
    List<BKInfo> findAllGnList();

    /**
     * 查询概念今日版块注入集合
     */
    List<BKMoneyInfo> findTodayGnMoneyList();
}
