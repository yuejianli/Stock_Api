package top.yueshushu.learn.test;

import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import top.yueshushu.learn.enumtype.ConditionType;
import top.yueshushu.learn.enumtype.RuleValueType;
import top.yueshushu.learn.strategy.bs.baseimpl.LastBuyPriceBuyStrategyHandler;
import top.yueshushu.learn.strategy.bs.model.TradeStockRuleDto;

import java.math.BigDecimal;

/**
 * 策略测试
 *
 * @author yuejianli
 * @date 2023-02-02
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class StrategyTest {

    @Test
    public void buyStrategyTest() {
        LastBuyPriceBuyStrategyHandler lastBuyPriceBuyHandler = SpringUtil.getBean("lastBuyPriceBuyHandler", LastBuyPriceBuyStrategyHandler.class);
        BigDecimal originPrice = new BigDecimal("65.23");
        // 处理一下规则。
        TradeStockRuleDto tradeStockRuleDto = new TradeStockRuleDto();
        tradeStockRuleDto.setRuleType(1);
        tradeStockRuleDto.setConditionType(ConditionType.LT.getCode());
        tradeStockRuleDto.setRuleValueType(RuleValueType.MONEY.getCode());
        tradeStockRuleDto.setRuleValue(new BigDecimal("1"));
        tradeStockRuleDto.setTradeValueType(RuleValueType.MONEY.getCode());
        tradeStockRuleDto.setTradePrice(new BigDecimal("0"));
        BigDecimal price = null;
        price = lastBuyPriceBuyHandler.getPrice(tradeStockRuleDto, originPrice);
        log.info(">>> 金额 ，为 1,0 时 新值是: {}", price);

        tradeStockRuleDto.setTradePrice(new BigDecimal("1"));
        price = lastBuyPriceBuyHandler.getPrice(tradeStockRuleDto, originPrice);
        log.info(">>> 金额 ，为 1,1 时 新值是: {}", price);

        tradeStockRuleDto.setRuleValueType(RuleValueType.PROPORTION.getCode());
        tradeStockRuleDto.setRuleValue(new BigDecimal("2"));
        tradeStockRuleDto.setTradePrice(new BigDecimal("3"));
        price = lastBuyPriceBuyHandler.getPrice(tradeStockRuleDto, originPrice);
        log.info(">>> 比例 ，为 1,1 时 新值是: {}", price);


        tradeStockRuleDto.setRuleValueType(RuleValueType.PROPORTION.getCode());
        tradeStockRuleDto.setRuleValue(new BigDecimal("2"));
        tradeStockRuleDto.setTradePrice(new BigDecimal("2"));
        price = lastBuyPriceBuyHandler.getPrice(tradeStockRuleDto, originPrice);
        log.info(">>> 比例 ，为 1,0 时 新值是: {}", price);
    }

    @Test
    public void sellStrategyTest() {
        LastBuyPriceBuyStrategyHandler lastBuyPriceBuyHandler = SpringUtil.getBean("lastBuyPriceBuyHandler", LastBuyPriceBuyStrategyHandler.class);
        BigDecimal originPrice = new BigDecimal("65.23");
        // 处理一下规则。
        TradeStockRuleDto tradeStockRuleDto = new TradeStockRuleDto();
        tradeStockRuleDto.setRuleType(2);
        tradeStockRuleDto.setConditionType(ConditionType.GT.getCode());
        tradeStockRuleDto.setRuleValueType(RuleValueType.MONEY.getCode());
        tradeStockRuleDto.setRuleValue(new BigDecimal("1"));
        tradeStockRuleDto.setTradeValueType(RuleValueType.MONEY.getCode());
        tradeStockRuleDto.setTradePrice(new BigDecimal("0"));
        BigDecimal price = null;
        price = lastBuyPriceBuyHandler.getPrice(tradeStockRuleDto, originPrice);
        log.info(">>> 金额 ，为 1,0 时 新值是: {}", price);

        tradeStockRuleDto.setTradePrice(new BigDecimal("1"));
        price = lastBuyPriceBuyHandler.getPrice(tradeStockRuleDto, originPrice);
        log.info(">>> 金额 ，为 1,1 时 新值是: {}", price);

        tradeStockRuleDto.setRuleValueType(RuleValueType.PROPORTION.getCode());
        tradeStockRuleDto.setRuleValue(new BigDecimal("2"));
        tradeStockRuleDto.setTradePrice(new BigDecimal("3"));
        price = lastBuyPriceBuyHandler.getPrice(tradeStockRuleDto, originPrice);
        log.info(">>> 比例 ，为 1,1 时 新值是: {}", price);


        tradeStockRuleDto.setRuleValueType(RuleValueType.PROPORTION.getCode());
        tradeStockRuleDto.setRuleValue(new BigDecimal("2"));
        tradeStockRuleDto.setTradePrice(new BigDecimal("2"));
        price = lastBuyPriceBuyHandler.getPrice(tradeStockRuleDto, originPrice);
        log.info(">>> 比例 ，为 1,0 时 新值是: {}", price);
    }
}
