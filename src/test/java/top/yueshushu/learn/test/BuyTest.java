package top.yueshushu.learn.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import top.yueshushu.learn.domainservice.StockSelectedDomainService;
import top.yueshushu.learn.enumtype.ConfigCodeType;
import top.yueshushu.learn.enumtype.MockType;
import top.yueshushu.learn.mode.ro.BuyRo;
import top.yueshushu.learn.mode.ro.DealRo;
import top.yueshushu.learn.mode.ro.RevokeRo;
import top.yueshushu.learn.mode.ro.SellRo;
import top.yueshushu.learn.mode.vo.ConfigVo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.service.*;
import top.yueshushu.learn.service.cache.StockCacheService;
import top.yueshushu.learn.util.BigDecimalUtil;

import javax.annotation.Resource;
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
    @Resource
    private StockCacheService stockCacheService;
    @Resource
    private ConfigService configService;
    @Resource
    private StockSelectedDomainService stockSelectedDomainService;

    @Test
    public void buyTest() {
        BuyRo buyRo = new BuyRo();
        buyRo.setUserId(1);
        buyRo.setCode("603986");
        buyRo.setName("兆易创新");
        buyRo.setAmount(100);
        buyRo.setMockType(1);
        buyRo.setPrice(new BigDecimal(200));
        OutputResult outputResult = buyService.buy(buyRo);
        log.info("输出结果:{}", outputResult);
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
    public void dealTest() {
        DealRo dealRo = new DealRo();
        dealRo.setUserId(1);
        dealRo.setMockType(1);
        dealRo.setId(20);
        OutputResult outputResult = dealService.deal(dealRo);
        log.info("输出结果:{}", outputResult);
    }

    @Test
    public void mockJobEntrustTest() {
        // 查询虚拟的买入差值价
        ConfigVo buyPriceVo = configService.getConfig(1, ConfigCodeType.MOCK_BUY_SUB_PRICE);
        BigDecimal buySubPrice = BigDecimalUtil.toBigDecimal(buyPriceVo.getCodeValue());

        ConfigVo sellPriceVo = configService.getConfig(1, ConfigCodeType.MOCK_SELL_SUB_PRICE);
        BigDecimal sellSubPrice = BigDecimalUtil.toBigDecimal(sellPriceVo.getCodeValue());


        String code = "001317";
        //获取昨天的价格
        BigDecimal lastBuyPrice = stockCacheService.getLastBuyCachePrice(1, MockType.MOCK.getCode(), code);
        BigDecimal lastSellPrice = stockCacheService.getLastSellCachePrice(1, MockType.MOCK.getCode(), code);
        //获取今天的价格
        BigDecimal currentPrice = stockCacheService.getNowCachePrice(code);
        //+ 相差 2元，就    110  2    --->  108 106  2
        if (BigDecimalUtil.subBigDecimal(lastBuyPrice, currentPrice).compareTo(buySubPrice) > 0) {
            log.info("买入");
            //立即修改当前买入的价格
            stockCacheService.setLastBuyCachePrice(1, MockType.MOCK.getCode(), code, currentPrice);
        }

        if (BigDecimalUtil.subBigDecimal(currentPrice, lastSellPrice).compareTo(sellSubPrice) > 0) {
            //开始买
            log.info("开始卖出");
            stockCacheService.setLastSellCachePrice(1, MockType.MOCK.getCode(), code, currentPrice);
        }
    }
}
