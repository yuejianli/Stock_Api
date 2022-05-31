package top.yueshushu.learn.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import top.yueshushu.learn.util.StockRedisUtil;

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
public class StockUtilTest {
    @Autowired
    private StockRedisUtil stockUtil;
    @Test
    public void setPriceTest(){
        stockUtil.setPrice("603986",new BigDecimal("157.00"));
        stockUtil.setPrice("002466",new BigDecimal("92.17"));
        stockUtil.setPrice("002812",new BigDecimal("225.90"));
        stockUtil.setPrice("600763",new BigDecimal("176.00"));
        stockUtil.setPrice("603589",new BigDecimal("67.60"));
    }
    @Test
    public void getPriceTest(){
        BigDecimal price = stockUtil.getPrice("002415");
        System.out.println(price);
    }
}
