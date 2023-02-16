package top.yueshushu.learn.test;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.CollectionUtils;
import top.yueshushu.learn.business.StockPoolBusiness;
import top.yueshushu.learn.crawler.crawler.CrawlerService;
import top.yueshushu.learn.crawler.crawler.ExtCrawlerService;
import top.yueshushu.learn.crawler.entity.*;
import top.yueshushu.learn.enumtype.DBStockType;
import top.yueshushu.learn.enumtype.StockCodeType;
import top.yueshushu.learn.enumtype.StockPoolType;
import top.yueshushu.learn.helper.DateHelper;
import top.yueshushu.learn.service.StockPoolHistoryService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName:StockCacheTest
 * @Description Stock 缓存处理
 * @Author 岳建立
 * @Date 2022/1/8 10:00
 * @Version 1.0
 **/
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class StockExtendTest {

    @Resource
    private CrawlerService crawlerService;
    @Resource
    private ExtCrawlerService extCrawlerService;
    @Resource
    private StockPoolBusiness stockPoolBusiness;
    @Resource
    private StockPoolHistoryService stockPoolHistoryService;
    @Resource
    private DateHelper dateHelper;

    @Test
    public void hotMapTest() {
        // 设置缓存
        Date beforeDate = DateUtil.offsetDay(DateUtil.date(), -1);
        HotStockInfo hotStockInfo = crawlerService.hotMapList(beforeDate);
        if (hotStockInfo == null) {
            log.info(">>>> 未获取到热点数据");
            return;
        }
        log.info(">>> 获取到热点数据 :{}", hotStockInfo);
    }

    @Test
    public void parseContentTest() {
        String content = "[\n" +
                "  {\n" +
                "    \"数字经济\": [\n" +
                "      \"sz002808\",\n" +
                "      \"sz002401\",\n" +
                "      \"sh600898\",\n" +
                "      \"sh688525\"\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"汽车产业链\": [\n" +
                "      \"sz002576\",\n" +
                "      \"sz002686\"\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"人工智能\": [\n" +
                "      \"sz002362\",\n" +
                "      \"sh688787\",\n" +
                "      \"sh688365\"\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"消费\": [\n" +
                "      \"sz002772\",\n" +
                "      \"sz000417\"\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"特斯拉产业链\": [\n" +
                "      \"sh605133\",\n" +
                "      \"sz002441\"\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"航天航空\": [\n" +
                "      \"sh600705\",\n" +
                "      \"sh600501\"\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"机器人\": [\n" +
                "      \"sz002209\",\n" +
                "      \"sh603666\"\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"多胎\": [\n" +
                "      \"sz002678\",\n" +
                "      \"sz000526\"\n" +
                "    ]\n" +
                "  }\n" +
                "]";


        JSONArray arrays = JSON.parseArray(content);
        List<HotStockInfo> allList = new ArrayList<HotStockInfo>();
        for (int i = 0; i < arrays.size(); i++) {
            //用toJavaObject toJavaObject

            Map<String, List<String>> dataMap = JSONObject.parseObject(arrays.getJSONObject(i).toJSONString(), new TypeReference<Map<String, List<String>>>() {
            });

            log.info(">>> 获取值: {}", dataMap);
            HotStockInfo hotStockInfo = new HotStockInfo(dataMap);
            allList.add(hotStockInfo);
        }
        System.out.println(allList);
    }

    @Test
    public void bkListTest() {
        List<BKInfo> allBkList = extCrawlerService.findAllBkList();
        log.info(">>> 获取所有的版本列表: {}", allBkList);
    }

    @Test
    public void bkTodayMoneyListTest() {
        List<BKMoneyInfo> allBkList = extCrawlerService.findTodayBkMoneyList();
        log.info(">>> 获取今日版块注入资金列表: {}", allBkList.get(0));
    }


    @Test
    public void gnListTest() {
        List<BKInfo> allBkList = extCrawlerService.findAllGnList();
        log.info(">>> 获取所有的 概念列表: {}", allBkList);
    }

    @Test
    public void gnTodayMoneyListTest() {
        List<BKMoneyInfo> allBkList = extCrawlerService.findTodayGnMoneyList();
        log.info(">>> 获取概念块注入资金列表: {}", allBkList.get(0));
    }

    @Test
    public void dyListTest() {
        List<BKInfo> allBkList = extCrawlerService.findAllDyList();
        log.info(">>> 获取所有的 地域列表: {}", allBkList);
    }

    @Test
    public void dyTodayMoneyListTest() {
        List<BKMoneyInfo> allBkList = extCrawlerService.findTodayDyMoneyList();
        log.info(">>> 获取 地域块注入资金列表: {}", allBkList.get(0));
    }


    @Test
    public void stockBkStockTest() {
        List<StockBKStockInfo> allBkList = extCrawlerService.findRelationBkListByCode("002415");
        log.info(">>> 获取 版块信息: {}", allBkList);
    }


    @Test
    public void dbListTest() {

        for (DBStockType dbStockType : DBStockType.values()) {
            if (DBStockType.BJ.equals(dbStockType)) {
                continue;
            }
            List<DBStockInfo> allBkList = extCrawlerService.findDbStock(dbStockType);
            List<DBStockInfo> dbStockInfos = allBkList.subList(0, 10);
            log.info(">>> 获取所有的 {} 列表: {}", dbStockType.getDesc(), dbStockInfos);
        }
    }

    @Test
    public void willDbListTest() {
        List<DBStockInfo> allBkList = extCrawlerService.findWillDbStockList(DBStockType.SH_SZ);
        log.info(">>> 获取 将要打板列表: {}", allBkList);
    }

    @Test
    public void findPoolByTypeTest() {

        for (StockPoolType stockPoolType : StockPoolType.values()) {
            List<StockPoolInfo> poolTypeList = extCrawlerService.findPoolByType(stockPoolType, DateUtil.date());

            List<StockPoolInfo> stockPoolInfos = poolTypeList.subList(0, 2);
            log.info(">>> 获取 {} 列表: {}", stockPoolType.getDesc(), stockPoolInfos);
        }
    }

    @Test
    public void poolTest() {
        stockPoolBusiness.handlerPool(DateUtil.date());
    }

    @Test
    public void poolTypeTest() {
        List<StockPoolInfo> poolList = extCrawlerService.findPoolByType(StockPoolType.QS, DateUtil.date());
        log.info(" 类型信息: {}", poolList.get(0));
    }

    @Test
    public void poolTypeSaveTest() {
        // 对日期进行处理.
        Date startDate = DateUtil.parse("2023-01-01 21:40:00");
        Date endDate = DateUtil.parse("2023-02-16");

        // 对日期进行处理.
        for (int i = 0; i <= 100; i++) {
            DateTime dateTime = DateUtil.offsetDay(startDate, i);
            if (dateTime.after(endDate)) {
                continue;
            }
            if (!dateHelper.isWorkingDay(dateTime)) {
                continue;
            }
            // 获取数据
            for (StockPoolType stockPoolType : StockPoolType.values()) {
                if (StockPoolType.YES_ZT.equals(stockPoolType)) {
                    continue;
                }
                // 查询出数据
                List<StockPoolInfo> poolDtList = extCrawlerService.findPoolByType(stockPoolType, dateTime);
                if (!CollectionUtils.isEmpty(poolDtList)) {
                    poolDtList = filterStockList(poolDtList, DBStockType.SH_SZ, null);
                    stockPoolHistoryService.savePoolHistory(poolDtList);
                }
            }
        }
    }

    /**
     * 过滤股票信息
     */
    private List<StockPoolInfo> filterStockList(List<StockPoolInfo> poolList, DBStockType dbStockType,
                                                StockPoolType stockPoolType) {
        if (null == dbStockType) {
            return poolList;
        }
        List<StockPoolInfo> result = new ArrayList<>();
        for (StockPoolInfo stockPoolInfo : poolList) {
            StockCodeType typeByStockCode = StockCodeType.getTypeByStockCode(stockPoolInfo.getCode());
            if (null == typeByStockCode) {
                continue;
            }
            if (!dbStockType.contains(typeByStockCode)) {
                continue;
            }
            // 放置进去
            result.add(stockPoolInfo);
        }
        return result;
    }

}
