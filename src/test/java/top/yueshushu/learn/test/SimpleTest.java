package top.yueshushu.learn.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

/**
 * 简单的方法测试
 *
 * @author yuejianli
 * @date 2022-11-16
 */
@Slf4j
public class SimpleTest {

    private LocalTime STOCK_PRICE_START_TIME = LocalTime.parse("14:59:00");
    private LocalTime STOCK_PRICE_END_TIME = LocalTime.parse("15:01:00");

    @Test
    public void timeTest() throws Exception {
        // 进行延迟， 如果时间在 14:59 之后，则睡眠 1分钟。
        log.info(">>>>>执行开始");
        LocalTime now = LocalTime.now();
        if (now.isAfter(STOCK_PRICE_START_TIME) && now.isBefore(STOCK_PRICE_END_TIME)) {
            // 进行休眠一分钟
            TimeUnit.SECONDS.sleep(5);
        }
        log.info(">>>>>执行结束");
    }
}
