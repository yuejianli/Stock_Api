package top.yueshushu.learn.strategy.bs.base;

import cn.hutool.core.date.DateUtil;
import cn.hutool.extra.spring.SpringUtil;
import org.springframework.util.ObjectUtils;
import top.yueshushu.learn.business.BuyBusiness;
import top.yueshushu.learn.business.SellBusiness;
import top.yueshushu.learn.common.Const;
import top.yueshushu.learn.enumtype.ConditionType;
import top.yueshushu.learn.enumtype.DealType;
import top.yueshushu.learn.enumtype.EntrustType;
import top.yueshushu.learn.enumtype.RuleValueType;
import top.yueshushu.learn.message.weixin.service.WeChatService;
import top.yueshushu.learn.mode.ro.BuyRo;
import top.yueshushu.learn.mode.ro.SellRo;
import top.yueshushu.learn.service.cache.StockCacheService;
import top.yueshushu.learn.strategy.bs.model.BaseStrategyInput;
import top.yueshushu.learn.strategy.bs.model.BaseStrategyOutput;
import top.yueshushu.learn.strategy.bs.model.TradeStockRuleDto;
import top.yueshushu.learn.util.BigDecimalUtil;

import java.math.BigDecimal;
import java.text.MessageFormat;

public abstract class BaseStrategyHandler<I extends BaseStrategyInput, R extends BaseStrategyOutput>
        implements StrategyHandler {

    /**
     * 获取原始的价格
     *
     * @param tradeStockRuleDto 规则
     */
    public abstract BigDecimal getOriginPrice(TradeStockRuleDto tradeStockRuleDto);

    @Override
    public void handle(TradeStockRuleDto tradeStockRuleDto) {
        // 获取价格
        BigDecimal price = getPrice(tradeStockRuleDto, getOriginPrice(tradeStockRuleDto));
        if (null == price) {
            return;
        }
        if (DealType.BUY.getCode().equals(tradeStockRuleDto.getRuleType())) {
            // 处理买入
            BuyRo buyRo = buyHandler(tradeStockRuleDto, price);
            handlerBuyRoResult(buyRo);
        }
        if (DealType.SELL.getCode().equals(tradeStockRuleDto.getRuleType())) {
            // 处理卖出
            SellRo sellRo = sellHandler(tradeStockRuleDto, price);
            handlerSellRoResult(sellRo);
        }

    }


    public BuyRo buyHandler(TradeStockRuleDto tradeStockRuleDto, BigDecimal price) {
        // 获取到 StockCacheService
        StockCacheService stockCacheService = SpringUtil.getBean("stockCacheService", StockCacheService.class);
        if (stockCacheService.getTodayBuySurplusNum(tradeStockRuleDto.getUserId(), tradeStockRuleDto.getMockType(),
                tradeStockRuleDto.getStockCode()) <= 0) {
            return null;
        }
        String code = tradeStockRuleDto.getStockCode();
        //获取当前的价格
        BigDecimal currentPrice = stockCacheService.getNowCachePrice(code);
        if (ObjectUtils.isEmpty(currentPrice) || Const.DEFAULT_PRICE.equals(currentPrice)) {
            return null;
        }
        // 当前的价格 大于等于 理论买入的价格， 则不能买入。
        if (currentPrice.compareTo(price) >= 0) {
            return null;
        }

        //可以买入
        BuyRo mockBuyRo = buildBuyRo(tradeStockRuleDto, code, currentPrice);
        BuyBusiness buyBusiness = SpringUtil.getBean("buyBusiness", BuyBusiness.class);
        // 进行买入.
        buyBusiness.buy(mockBuyRo);
        return mockBuyRo;
    }


    public SellRo sellHandler(TradeStockRuleDto tradeStockRuleDto, BigDecimal price) {
        // 获取到 StockCacheService
        StockCacheService stockCacheService = SpringUtil.getBean("stockCacheService", StockCacheService.class);
        if (stockCacheService.getTodaySellSurplusNum(tradeStockRuleDto.getUserId(), tradeStockRuleDto.getMockType(),
                tradeStockRuleDto.getStockCode()) <= 0) {
            return null;
        }
        String code = tradeStockRuleDto.getStockCode();
        //获取当前的价格
        BigDecimal currentPrice = stockCacheService.getNowCachePrice(code);
        if (ObjectUtils.isEmpty(currentPrice) || Const.DEFAULT_PRICE.equals(currentPrice)) {
            return null;
        }
        // 当前的价格 小于等待 理论买入的价格， 则不能卖出。
        if (currentPrice.compareTo(price) <= 0) {
            return null;
        }

        //可以买入
        SellRo mockSellRo = buildSellRo(tradeStockRuleDto, code, currentPrice);
        SellBusiness sellBusiness = SpringUtil.getBean("sellBusiness", SellBusiness.class);
        // 进行买入.
        sellBusiness.sell(mockSellRo);
        return mockSellRo;
    }


    private void handlerBuyRoResult(BuyRo buyRo) {
        if (null == buyRo) {
            return;
        }
        handleExtractBuyCache(buyRo);
        sendExtractBuyMessage(buyRo);
    }


    private void handleExtractBuyCache(BuyRo buyRo) {
        // 获取到 StockCacheService
        StockCacheService stockCacheService = SpringUtil.getBean("stockCacheService", StockCacheService.class);
        //立即修改当前买入的价格
        stockCacheService.setLastBuyCachePrice(buyRo.getUserId(), buyRo.getMockType(), buyRo.getCode(), buyRo.getPrice());
        stockCacheService.setTodayBuyCachePrice(buyRo.getUserId(), buyRo.getMockType(), buyRo.getCode(), buyRo.getPrice());
        stockCacheService.reduceTodayBuySurplusNum(buyRo.getUserId(), buyRo.getMockType(), buyRo.getCode());
    }

    private void sendExtractBuyMessage(BuyRo buyRo) {
        String message = MessageFormat.format(
                "{0} 委托买入提醒: 代码: {1},名称{2},买入{3}股，委托价格是:{4}",
                DateUtil.now(),
                buyRo.getCode(), buyRo.getName(),
                buyRo.getAmount(), buyRo.getPrice()
        );
        // 获取到 StockCacheService
        WeChatService weChatService = SpringUtil.getBean("weChatService", WeChatService.class);
        weChatService.sendTextMessage(buyRo.getUserId(), message);
    }


    private void handlerSellRoResult(SellRo sellRo) {
        if (null == sellRo) {
            return;
        }
        handleExtractSellCache(sellRo);
        sendExtractSellMessage(sellRo);
    }


    private void handleExtractSellCache(SellRo sellRo) {
        // 获取到 StockCacheService
        StockCacheService stockCacheService = SpringUtil.getBean("stockCacheService", StockCacheService.class);
        //立即修改当前买入的价格
        stockCacheService.setLastSellCachePrice(sellRo.getUserId(), sellRo.getMockType(), sellRo.getCode(), sellRo.getPrice());
        stockCacheService.setTodaySellCachePrice(sellRo.getUserId(), sellRo.getMockType(), sellRo.getCode(), sellRo.getPrice());
        stockCacheService.reduceTodaySellSurplusNum(sellRo.getUserId(), sellRo.getMockType(), sellRo.getCode());
    }

    private void sendExtractSellMessage(SellRo sellRo) {
        String message = MessageFormat.format(
                "{0} 委托卖出提醒: 代码: {1},名称{2},买入{3}股，委托价格是:{4}",
                DateUtil.now(),
                sellRo.getCode(), sellRo.getName(),
                sellRo.getAmount(), sellRo.getPrice()
        );
        // 获取到 StockCacheService
        WeChatService weChatService = SpringUtil.getBean("weChatService", WeChatService.class);
        weChatService.sendTextMessage(sellRo.getUserId(), message);
    }


    private BuyRo buildBuyRo(TradeStockRuleDto tradeStockRuleDto, String code, BigDecimal currentPrice) {
        BuyRo mockBuyRo = new BuyRo();
        mockBuyRo.setUserId(tradeStockRuleDto.getUserId());
        mockBuyRo.setMockType(tradeStockRuleDto.getMockType());
        mockBuyRo.setCode(code);
        mockBuyRo.setAmount(tradeStockRuleDto.getTradeNum());
        mockBuyRo.setName(tradeStockRuleDto.getStockName());
        mockBuyRo.setPrice(currentPrice);
        mockBuyRo.setEntrustType(EntrustType.AUTO.getCode());
        return mockBuyRo;
    }


    private SellRo buildSellRo(TradeStockRuleDto tradeStockRuleDto, String code, BigDecimal currentPrice) {
        //开始买
        SellRo sellRo = new SellRo();
        sellRo.setUserId(tradeStockRuleDto.getUserId());
        sellRo.setMockType(tradeStockRuleDto.getMockType());
        sellRo.setCode(code);
        sellRo.setAmount(tradeStockRuleDto.getTradeNum());
        sellRo.setName(tradeStockRuleDto.getStockName());
        sellRo.setPrice(currentPrice);
        sellRo.setEntrustType(EntrustType.AUTO.getCode());
        return sellRo;
    }

    public BigDecimal getPrice(TradeStockRuleDto tradeStockRuleDto, BigDecimal originPrice) {
        if (ObjectUtils.isEmpty(originPrice) || BigDecimal.ZERO.equals(originPrice)) {
            return null;
        }

        BigDecimal result = null;
        // 获取之前的价格.
        // 算出 按照规则来，应该的价格。
        if (ConditionType.LT.getCode().equals(tradeStockRuleDto.getConditionType())) {
            if (RuleValueType.MONEY.getCode().equals(tradeStockRuleDto.getRuleValueType())) {
                // 是金额.
                result = BigDecimalUtil.subBigDecimal(originPrice, tradeStockRuleDto.getRuleValue());
            } else if (RuleValueType.PROPORTION.getCode().equals(tradeStockRuleDto.getRuleValueType())) {
                // 是比例.
                result = BigDecimalUtil.subBigDecimal(originPrice,
                        BigDecimalUtil.toBigDecimal(originPrice,
                                BigDecimalUtil.divByHundred(tradeStockRuleDto.getRuleValue())));
            }
        } else {
            if (RuleValueType.MONEY.getCode().equals(tradeStockRuleDto.getRuleValueType())) {
                // 是金额.
                result = BigDecimalUtil.addBigDecimal(originPrice, tradeStockRuleDto.getRuleValue());
            } else if (RuleValueType.PROPORTION.getCode().equals(tradeStockRuleDto.getRuleValueType())) {
                // 是金额.
                result = BigDecimalUtil.addBigDecimal(originPrice,
                        BigDecimalUtil.toBigDecimal(originPrice,
                                BigDecimalUtil.divByHundred(tradeStockRuleDto.getRuleValue())));
            }
        }

        // 再次计算相对差值。
        if (ConditionType.LT.getCode().equals(tradeStockRuleDto.getConditionType())) {
            if (RuleValueType.MONEY.getCode().equals(tradeStockRuleDto.getTradeValueType())) {
                // 是金额.
                result = BigDecimalUtil.subBigDecimal(result, tradeStockRuleDto.getTradePrice());
            } else if (RuleValueType.PROPORTION.getCode().equals(tradeStockRuleDto.getTradeValueType())) {
                // 是金额.
                result = BigDecimalUtil.subBigDecimal(result,
                        BigDecimalUtil.toBigDecimal(originPrice,
                                BigDecimalUtil.divByHundred(tradeStockRuleDto.getRuleValue())));
            }
        } else {
            if (RuleValueType.MONEY.getCode().equals(tradeStockRuleDto.getTradeValueType())) {
                // 是金额.
                result = BigDecimalUtil.addBigDecimal(result, tradeStockRuleDto.getTradePrice());
            } else if (RuleValueType.PROPORTION.getCode().equals(tradeStockRuleDto.getTradeValueType())) {
                // 是金额.
                result = BigDecimalUtil.addBigDecimal(result,
                        BigDecimalUtil.toBigDecimal(originPrice,
                                BigDecimalUtil.divByHundred(tradeStockRuleDto.getRuleValue())));
            }
        }
        // 将
        return result.setScale(2, BigDecimal.ROUND_HALF_UP);
    }
}
