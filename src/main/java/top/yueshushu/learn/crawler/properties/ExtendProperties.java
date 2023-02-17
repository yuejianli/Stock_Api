package top.yueshushu.learn.crawler.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @ClassName:EasyMoneyProperties
 * @Description 扩展的配置信息
 * @Author 岳建立
 * @Date 2021/11/7 10:35
 * @Version 1.0
 **/
@Data
@Component
public class ExtendProperties implements Serializable {
    /**
     * 热点图 hotmap
     */
    @Value("${extend.hotmap}")
    private String hotMapUrl;

    /**
     * 查询版块列表
     */
    @Value("${extend.bkList}")
    private String bkListUrl;

    /**
     * 版块主力资金今日注入
     */
    @Value("${extend.todayPlatMoney}")
    private String todayPlatMoneyUrl;


    /**
     * 查询版块列表
     */
    @Value("${extend.gnList}")
    private String gnListUrl;

    /**
     * 概念主力资金今日注入
     */
    @Value("${extend.todayGnPlatMoney}")
    private String todayGnPlatMoneyUrl;


    /**
     * 查询地域列表
     */
    @Value("${extend.dyList}")
    private String dyListUrl;

    /**
     * 地域主力资金今日注入
     */
    @Value("${extend.todayDyPlatMoney}")
    private String todayDyPlatMoneyUrl;

    /**
     * 版块历史资金注入同步
     */
    @Value("${extend.asyncBkMoney}")
    private String asyncBkMoneyUrl;

    /**
     * 股票与股票版块同步概念
     */
    @Value("${extend.stockBkStock}")
    private String stockBkStockUrl;


    /**
     * 打版的股票url
     */
    @Value("${extend.db_stock_url}")
    private String dbStockUrl;


    /**
     * 涨停的池子股票
     */
    @Value("${extend.ztTopic}")
    private String ztTopicUrl;

    /**
     * 跌停的池子股票
     */
    @Value("${extend.dtTopic}")
    private String dtTopicUrl;

    /**
     * 昨日涨停的池子股票
     */
    @Value("${extend.yesZtTopic}")
    private String yesZtTopicUrl;


    /**
     * 强势的池子股票
     */
    @Value("${extend.qsTopic}")
    private String qsTopicUrl;


    /**
     * 次新的池子股票
     */
    @Value("${extend.cxTopic}")
    private String cxTopicUrl;

    /**
     * 炸板的池子股票
     */
    @Value("${extend.zbTopic}")
    private String zbTopicUrl;
}
