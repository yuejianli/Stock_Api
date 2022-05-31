package top.yueshushu.learn.test;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import top.yueshushu.learn.mode.ro.*;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.service.*;

import java.math.BigDecimal;

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
public class BuyTest {
    @Autowired
    private BuyService buyService;
    @Autowired
    private SellService sellService;
    @Autowired
    private RevokeService revokeService;
    @Autowired
    private DealService dealService;
    @Test
    public void buyTest(){
        BuyRo buyRo = new BuyRo();
        buyRo.setUserId(1);
        buyRo.setCode("603986");
        buyRo.setName("兆易创新");
        buyRo.setAmount(100);
        buyRo.setMockType(1);
        buyRo.setPrice(new BigDecimal(200));
        OutputResult outputResult = buyService.buy(buyRo);
        log.info("输出结果:{}",outputResult);
    }

    @Test
    public void sellTest(){
        SellRo sellRo = new SellRo();
        sellRo.setUserId(1);
        sellRo.setCode("603986");
        sellRo.setName("兆易创新");
        sellRo.setAmount(100);
        sellRo.setMockType(1);
        sellRo.setPrice(new BigDecimal(150));
        OutputResult outputResult = sellService.sell(sellRo);
        log.info("输出结果:{}",outputResult);
    }
    @Test
    public void revokeTest(){
        RevokeRo revokeRo = new RevokeRo();
        revokeRo.setUserId(1);
        revokeRo.setMockType(1);
        revokeRo.setId(14);
        OutputResult outputResult = revokeService.revoke(revokeRo);
        log.info("输出结果:{}",outputResult);
    }
    @Test
    public void dealTest(){
        DealRo dealRo = new DealRo();
        dealRo.setUserId(1);
        dealRo.setMockType(1);
        dealRo.setId(20);
        OutputResult outputResult = dealService.deal(dealRo);
        log.info("输出结果:{}",outputResult);
    }
}
