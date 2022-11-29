package top.yueshushu.learn.test;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.CollectionUtils;
import top.yueshushu.learn.domain.StockBigDealDo;
import top.yueshushu.learn.service.StockCrawlerService;

import javax.annotation.Resource;
import java.util.List;

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
}