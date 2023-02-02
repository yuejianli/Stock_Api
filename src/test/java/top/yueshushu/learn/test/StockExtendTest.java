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
                "      \"sz002279\",\n" +
                "      \"sz002848\",\n" +
                "      \"sz002835\",\n" +
                "      \"sz002195\",\n" +
                "      \"sz003005\",\n" +
                "      \"sz002713\",\n" +
                "      \"sz002184\",\n" +
                "      \"sh603533\",\n" +
                "      \"sh688500\",\n" +
                "      \"sz002808\",\n" +
                "      \"sz002401\",\n" +
                "      \"sz001339\",\n" +
                "      \"sz002380\",\n" +
                "      \"sz002848\",\n" +
                "      \"sh600536\",\n" +
                "      \"sz002279\",\n" +
                "      \"sz002195\",\n" +
                "      \"sz002835\",\n" +
                "      \"sh601728\",\n" +
                "      \"sh603636\",\n" +
                "      \"sz300598\",\n" +
                "      \"sz000032\",\n" +
                "      \"sz002808\",\n" +
                "      \"sz002401\",\n" +
                "      \"sz001339\",\n" +
                "      \"sz002777\",\n" +
                "      \"sz002771\",\n" +
                "      \"sz002380\",\n" +
                "      \"sh600536\",\n" +
                "      \"sz002848\",\n" +
                "      \"sz002990\",\n" +
                "      \"sh603106\",\n" +
                "      \"sh688083\",\n" +
                "      \"sh688500\",\n" +
                "      \"sz002368\",\n" +
                "      \"sz002808\",\n" +
                "      \"sh688525\",\n" +
                "      \"sh600936\",\n" +
                "      \"sz002587\",\n" +
                "      \"sz002642\",\n" +
                "      \"sz002972\",\n" +
                "      \"sz002816\",\n" +
                "      \"sz003005\",\n" +
                "      \"sz002401\",\n" +
                "      \"sz002777\",\n" +
                "      \"sz003029\",\n" +
                "      \"sz000815\",\n" +
                "      \"sz000925\",\n" +
                "      \"sz002808\",\n" +
                "      \"sh600898\",\n" +
                "      \"sh688525\"\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"汽车产业链\": [\n" +
                "      \"sz002576\",\n" +
                "      \"sz002708\",\n" +
                "      \"sh603917\",\n" +
                "      \"sz002536\",\n" +
                "      \"sz002863\",\n" +
                "      \"sh605333\",\n" +
                "      \"sz000678\",\n" +
                "      \"sh603200\",\n" +
                "      \"sz002190\",\n" +
                "      \"sz002997\",\n" +
                "      \"sz002824\",\n" +
                "      \"sz002953\",\n" +
                "      \"bj873152\",\n" +
                "      \"sz300217\",\n" +
                "      \"sh688518\",\n" +
                "      \"sh603331\",\n" +
                "      \"sh603179\",\n" +
                "      \"sz003027\",\n" +
                "      \"sz002576\",\n" +
                "      \"sz002363\",\n" +
                "      \"sz002144\",\n" +
                "      \"sz002882\",\n" +
                "      \"sz002686\"\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"人工智能\": [\n" +
                "      \"sz002362\",\n" +
                "      \"sh688787\",\n" +
                "      \"sh688327\",\n" +
                "      \"sz000681\",\n" +
                "      \"sz002253\",\n" +
                "      \"sz300044\",\n" +
                "      \"sz002689\",\n" +
                "      \"sz300229\",\n" +
                "      \"sz002230\",\n" +
                "      \"sz002877\",\n" +
                "      \"sz002654\",\n" +
                "      \"sz002229\",\n" +
                "      \"sh688207\",\n" +
                "      \"sz300624\",\n" +
                "      \"sz300364\",\n" +
                "      \"sh688365\"\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"消费\": [\n" +
                "      \"sz002772\",\n" +
                "      \"sh603697\",\n" +
                "      \"sh600172\",\n" +
                "      \"sh605337\",\n" +
                "      \"sz000716\",\n" +
                "      \"sz002345\",\n" +
                "      \"sh600702\",\n" +
                "      \"sz000026\",\n" +
                "      \"sz002403\",\n" +
                "      \"sz002209\",\n" +
                "      \"sz002343\",\n" +
                "      \"sz000026\",\n" +
                "      \"sz000721\",\n" +
                "      \"sz000610\",\n" +
                "      \"sz000417\"\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"特斯拉产业链\": [\n" +
                "      \"sh605133\",\n" +
                "      \"sh601689\",\n" +
                "      \"sh603305\",\n" +
                "      \"sz002976\",\n" +
                "      \"sz002101\",\n" +
                "      \"sh603088\",\n" +
                "      \"sz002058\",\n" +
                "      \"sz001319\",\n" +
                "      \"sh603348\",\n" +
                "      \"sh600933\",\n" +
                "      \"sz002850\",\n" +
                "      \"sz002384\",\n" +
                "      \"sz300926\",\n" +
                "      \"sz002441\"\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"航天航空\": [\n" +
                "      \"sh600705\",\n" +
                "      \"sz000561\",\n" +
                "      \"sh603261\",\n" +
                "      \"sz301233\",\n" +
                "      \"sz300227\",\n" +
                "      \"sh688685\",\n" +
                "      \"sh688272\",\n" +
                "      \"sh600855\",\n" +
                "      \"sh600501\"\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"机器人\": [\n" +
                "      \"sz002209\",\n" +
                "      \"sz002403\",\n" +
                "      \"sz002380\",\n" +
                "      \"sz002209\",\n" +
                "      \"sz002403\",\n" +
                "      \"sz301112\",\n" +
                "      \"sz301023\",\n" +
                "      \"sh603666\"\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"多胎\": [\n" +
                "      \"sz002678\",\n" +
                "      \"sz002621\",\n" +
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
}
