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
                "000972", "000973", "000975",
                "000976", "000977", "000978",
                "000979", "000980", "000981",
                "000982", "000983", "000985",
                "000987",
                "000988", "000989", "000990",
                "000993", "000995", "000996",
                "000997"
        );
        List<String> resultCodeList = codeList.stream().map(n -> String.valueOf(n)).collect(Collectors.toList());
        crawlerStockHistoryService.txMoneyTodayStockHistory(resultCodeList, stockService.listFullCode(resultCodeList));
    }

    @Test
    public void txStock2Test() {
        List<Object> codeList = Arrays.asList(
                "000995", "000996", "000997"
        );
        List<String> resultCodeList = codeList.stream().map(n -> String.valueOf(n)).collect(Collectors.toList());
        crawlerStockHistoryService.txMoneyTodayStockHistory(resultCodeList, stockService.listFullCode(resultCodeList));
    }
}
