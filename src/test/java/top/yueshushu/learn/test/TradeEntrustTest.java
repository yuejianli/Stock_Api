package top.yueshushu.learn.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import top.yueshushu.learn.mode.ro.TradeEntrustRo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.service.TradeEntrustService;

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
public class TradeEntrustTest {
    @Autowired
    private TradeEntrustService tradeEntrustService;
    @Test
    public void listTest(){
        TradeEntrustRo tradeEntrustRo = new TradeEntrustRo();
        tradeEntrustRo.setUserId(1);
        tradeEntrustRo.setPageNum(1);
        tradeEntrustRo.setPageSize(10);
        tradeEntrustRo.setMockType(1);

        OutputResult outputResult = tradeEntrustService.mockList(tradeEntrustRo);
        log.info("输出结果:{}",outputResult);
    }
}
