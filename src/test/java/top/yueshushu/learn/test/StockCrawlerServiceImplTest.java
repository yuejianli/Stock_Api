package top.yueshushu.learn.test;

import cn.hutool.core.date.DateUtil;
import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.CollectionUtils;
import top.yueshushu.learn.crawler.service.RealTimePriceService;
import top.yueshushu.learn.domain.StockBigDealDo;
import top.yueshushu.learn.service.StockCrawlerService;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>
 *
 * </p>
 *
 * @author yuejianli
 * @since 2022-11-29 17:57
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
class StockCrawlerServiceImplTest {

    @Resource
    private StockCrawlerService stockCrawlerService;

    @Test
    void getStockInfo() {
    }

    @Test
    void getStockKline() {
    }

    @Test
    void stockAsync() {
    }

    @Test
    void stockHistoryAsync() {
    }

    @Test
    void updateCodePrice() {
    }

    @Test
    void updateAllStock() {
    }

    @Test
    void handleBigDeal() {

        List<StockBigDealDo> result = stockCrawlerService.handleBigDeal("sz001322", 400000, DateUtil.date()).getData();

        if (CollectionUtils.isEmpty(result)) {
            log.info(">>> 没有获取到数据");
        } else {
            result.forEach(
                    n -> {
                        log.info(">>> 信息:{}", n);
                    }
            );
        }
    }

    @Test
    public void randGetNowPrice() {
        String stockCode = "002415";
        String fullCode = "sz002415";

        AtomicInteger priceCounter = new AtomicInteger();
        priceCounter.set(0);

        // int length = 4;

        int length = SpringUtil.getBeansOfType(RealTimePriceService.class).size();

        log.info(">>> 长度:{}", length);
        for (int i = 0; i < 20; i++) {
            int newCount = priceCounter.incrementAndGet();

            int useCount = newCount % length;

            // 获取 Service
//            RealTimePriceService realTimePriceService = SpringUtil.getBean("realTimePriceService"+useCount, RealTimePriceService.class);
//            BigDecimal nowPrice = realTimePriceService.getNowPrice(stockCode, fullCode);
//            log.info(">>>> 获取现在的价格: {}", nowPrice);

            log.info(">>>> 获取现在的价格: {}", useCount);

        }
    }

    @Test
    public void xueQiuTest() {
        String stockCode = "002415";
        String fullCode = "sz002415";
        RealTimePriceService realTimePriceService = SpringUtil.getBean("realTimePriceService2", RealTimePriceService.class);
        BigDecimal nowPrice = realTimePriceService.getNowPrice(stockCode, fullCode, 1);
        log.info(">>> {}", nowPrice);
    }

    @Test
    public void WangyiTest() throws Exception {
        String stockCode = "002415";
        String fullCode = "sz002415";
        RealTimePriceService realTimePriceService = SpringUtil.getBean("realTimePriceService4", RealTimePriceService.class);
        BigDecimal nowPrice = realTimePriceService.getNowPrice(stockCode, fullCode, 1);
        log.info(">>> {}", nowPrice);

//        String content = "日期,股票代码,名称,收盘价,最高价,最低价,开盘价,前收盘,涨跌额,涨跌幅,换手率,成交量,成交金额,总市值,流通市值,成交笔数\n" +
//                "2023-01-12,'002415,海康威视,32.66,33.15,32.51,32.89,32.89,-0.23,-0.6993,0.3014,28030851,918373568.81,3.0801386758e+11,3.03744080018e+11,52927";
//
//        BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(content.getBytes(Charset.forName("utf8"))), Charset.forName("utf8")));
//        br.readLine();
//        String line = br.readLine();
//        log.info(">>> 输出下一行:{}",line);
//
//        String nowPriceStr = line.split("\\,")[3];
//
//        log.info(">>>> 当前价格:{}",nowPriceStr);


    }
}