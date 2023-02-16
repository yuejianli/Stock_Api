package top.yueshushu.learn.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import top.yueshushu.learn.crawler.service.CrawlerStockHistoryService;
import top.yueshushu.learn.service.StockService;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用途描述
 *
 * @author yuejianli
 * @date 2023-02-16
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class StockHistoryTest {
    @Resource
    private CrawlerStockHistoryService crawlerStockHistoryService;
    @Resource
    private StockService stockService;

    @Test
    public void txStockTest() {
        List<Object> codeList = Arrays.asList(
                600843, 600844, 600845, 600846, 600847, 600848, 600849, 600850, 600851, 600852, 600853, 600854, 600855, 600856, 600857, 600858, 600859, 600860, 600861, 600862
        );
        List<String> resultCodeList = codeList.stream().map(n -> String.valueOf(n)).collect(Collectors.toList());
        crawlerStockHistoryService.txMoneyTodayStockHistory(resultCodeList, stockService.listFullCode(resultCodeList));
    }
}
