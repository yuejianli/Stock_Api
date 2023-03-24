package top.yueshushu.learn.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import top.yueshushu.learn.exception.TradeUserException;
import top.yueshushu.learn.mode.ro.TradePositionRo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.service.TradePositionService;

/**
 * @ClassName:StockUtilTest
 * @Description TODO
 * @Author 岳建立
 * @Date 2022/1/8 10:00
 * @Version 1.0
 **/
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class TradePositionTest {
    @Autowired
    private TradePositionService tradePositionService;
    @Test
    public void listTest() {
        TradePositionRo tradePositionRo = new TradePositionRo();
        tradePositionRo.setUserId(1);
        tradePositionRo.setPageNum(1);
        tradePositionRo.setPageSize(10);
        tradePositionRo.setMockType(1);

        OutputResult outputResult = null;
        try {
            outputResult = tradePositionService.realList(tradePositionRo);
        } catch (TradeUserException e) {
            e.printStackTrace();
        }
        log.info("输出结果:{}", outputResult);
    }
}
