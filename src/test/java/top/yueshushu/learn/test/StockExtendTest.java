package top.yueshushu.learn.test;

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
import top.yueshushu.learn.crawler.crawler.CrawlerService;
import top.yueshushu.learn.crawler.crawler.ExtCrawlerService;
import top.yueshushu.learn.crawler.entity.BKInfo;
import top.yueshushu.learn.crawler.entity.BKMoneyInfo;
import top.yueshushu.learn.crawler.entity.HotStockInfo;

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
        List<BKMoneyInfo> allBkList = extCrawlerService.findTodayBKMoneyList();
        log.info(">>> 获取今日版块注入资金列表: {}", allBkList.get(0));
    }


    @Test
    public void gnListTest() {
        List<BKInfo> allBkList = extCrawlerService.findAllGnList();
        log.info(">>> 获取所有的 概念列表: {}", allBkList);
    }
}
