package top.yueshushu.learn.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import top.yueshushu.learn.entity.Stock;
import top.yueshushu.learn.service.StockBigDealService;
import top.yueshushu.learn.service.cache.StockCacheService;

import javax.annotation.Resource;
import java.math.BigDecimal;

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
public class StockCacheTest {
    @Autowired
    private StockCacheService stockCacheService;
    @Resource
    private StockBigDealService stockBigDealService;

    @Test
    public void stockInfoCacheTest() {
        // 设置缓存
        Stock stock = stockCacheService.selectByCode("002415");

        stockCacheService.selectByCode("002248");
        log.info(">>>> stock: {}", stock);
    }

    @Test
    public void clearStockInfoTest() {
        // 设置缓存
        stockCacheService.clearStockInfo();
        log.info(">>>> 清理缓存");
    }

    @Test
    public void minVolumeTest() {
        BigDecimal totalMoney = new BigDecimal(300000);

        log.info("股票 {} 大宗交易手数是:{}", "001322", stockBigDealService.getMinVolume(totalMoney, "001322"));
        log.info("股票 {} 大宗交易手数是:{}", "001317", stockBigDealService.getMinVolume(totalMoney, "001317"));
        log.info("股票 {} 大宗交易手数是:{}", "002241", stockBigDealService.getMinVolume(totalMoney, "002241"));
        log.info("股票 {} 大宗交易手数是:{}", "002271", stockBigDealService.getMinVolume(totalMoney, "002271"));
        log.info("股票 {} 大宗交易手数是:{}", "002506", stockBigDealService.getMinVolume(totalMoney, "002506"));
        log.info("股票 {} 大宗交易手数是:{}", "002791", stockBigDealService.getMinVolume(totalMoney, "002791"));
    }
}
