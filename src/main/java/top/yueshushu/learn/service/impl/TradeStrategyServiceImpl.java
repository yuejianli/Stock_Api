package top.yueshushu.learn.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.yueshushu.learn.business.BuyBusiness;
import top.yueshushu.learn.business.RevokeBusiness;
import top.yueshushu.learn.business.SellBusiness;
import top.yueshushu.learn.domainservice.StockSelectedDomainService;
import top.yueshushu.learn.entity.Stock;
import top.yueshushu.learn.entity.TradeEntrust;
import top.yueshushu.learn.enumtype.ConfigCodeType;
import top.yueshushu.learn.enumtype.EntrustType;
import top.yueshushu.learn.mode.ro.BuyRo;
import top.yueshushu.learn.mode.ro.RevokeRo;
import top.yueshushu.learn.mode.ro.SellRo;
import top.yueshushu.learn.mode.vo.ConfigVo;
import top.yueshushu.learn.service.ConfigService;
import top.yueshushu.learn.service.TradeEntrustService;
import top.yueshushu.learn.service.TradeStrategyService;
import top.yueshushu.learn.service.cache.StockCacheService;
import top.yueshushu.learn.util.BigDecimalUtil;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * @ClassName:TradeStrategyServiceImpl
 * @Description TODO
 * @Author zk_yjl
 * @Date 2022/1/11 20:33
 * @Version 1.0
 * @Since 1.0
 **/
@Service
@Slf4j
public class TradeStrategyServiceImpl implements TradeStrategyService {
    @Resource
    private StockSelectedDomainService stockSelectedDomainService;
    @Resource
    private BuyBusiness buyBusiness;
    @Resource
    private SellBusiness sellBusiness;
    @Resource
    private StockCacheService stockCacheService;
    @Resource
    private ConfigService configService;
    @Resource
    private TradeEntrustService tradeEntrustService;
    @Resource
    private RevokeBusiness revokeBusiness;

    @Override
    public void mockEntrustXxlJob(BuyRo buyRo) {
        // 查询虚拟的买入差值价
        ConfigVo buyPriceVo = configService.getConfig(buyRo.getUserId(), ConfigCodeType.MOCK_BUY_SUB_PRICE);
        BigDecimal buySubPrice = BigDecimalUtil.toBigDecimal(buyPriceVo.getCodeValue());

        ConfigVo sellPriceVo = configService.getConfig(buyRo.getUserId(), ConfigCodeType.MOCK_SELL_SUB_PRICE);
        BigDecimal sellSubPrice = BigDecimalUtil.toBigDecimal(sellPriceVo.getCodeValue());


        List<String> codeList = stockSelectedDomainService.findCodeList(null);
        //查询该员工最开始的收盘价
        for(String code:codeList) {
            //获取昨天的价格
            BigDecimal lastBuyPrice = stockCacheService.getLastBuyCachePrice(code);
            BigDecimal lastSellPrice = stockCacheService.getLastSellCachePrice(code);
            //获取今天的价格
            BigDecimal currentPrice = stockCacheService.getNowCachePrice(code);
            //查询当前股票的名称
            Stock stock = stockCacheService.selectByCode(code);
            //+ 相差 2元，就    110  2    --->  108 106  2
            if (BigDecimalUtil.subBigDecimal(lastBuyPrice, currentPrice).compareTo(buySubPrice) > 0) {
                //可以买入
                BuyRo mockBuyRo = new BuyRo();
                mockBuyRo.setUserId(buyRo.getUserId());
                mockBuyRo.setMockType(buyRo.getMockType());
                mockBuyRo.setCode(code);
                mockBuyRo.setAmount(100);
                mockBuyRo.setName(stock.getName());
                mockBuyRo.setPrice(currentPrice);
                log.info(">>>可以买入股票{}", code);
                mockBuyRo.setEntrustType(EntrustType.AUTO.getCode());
                buyBusiness.buy(mockBuyRo);
                //立即修改当前买入的价格
                stockCacheService.setLastBuyCachePrice(code, currentPrice);
//
//                User user = userService.getById(buyRo.getUserId());
//                String message = MessageFormat.format(
//                        "委托买入提醒: 买入股票 {0},股票名称{1},买入{2}份，买入的价格是:{3}",
//                        mockBuyRo.getCode(), mockBuyRo.getName(),
//                        mockBuyRo.getAmount(), mockBuyRo.getPrice()
//                );
//				weChatService.sendTextMessage(user.getWxUserId(), message);
            }

            if (BigDecimalUtil.subBigDecimal(currentPrice, lastSellPrice).compareTo(sellSubPrice) > 0) {
                //开始买
                SellRo sellRo = new SellRo();
                sellRo.setUserId(buyRo.getUserId());
                sellRo.setMockType(buyRo.getMockType());
                sellRo.setCode(code);
                sellRo.setAmount(100);
                sellRo.setName(stock.getName());
                sellRo.setPrice(currentPrice);
                sellRo.setEntrustType(EntrustType.AUTO.getCode());
                log.info(">>>可以卖出股票{}", code);
                stockCacheService.setLastSellCachePrice(code, currentPrice);
                sellBusiness.sell(sellRo);

//                User user = userService.getById(buyRo.getUserId());
//                String message = MessageFormat.format(
//                        "委托卖出提醒: 卖出股票 {0},股票名称{1},卖出{2}份，卖出的价格是:{3}",
//                        sellRo.getCode(), sellRo.getName(),
//                        sellRo.getAmount(), sellRo.getPrice()
//                );
//				weChatService.sendTextMessage(user.getWxUserId(),
//						message);
            }
        }
    }

    @Override
    public void revokeEntrustJob(Integer userId, Integer mockType) {
        //获取当前所有的今日委托单信息，正在委托的.
        List<TradeEntrust> tradeEntrustDoList = tradeEntrustService.listNowRunEntrust(userId, mockType);
        if (CollectionUtils.isEmpty(tradeEntrustDoList)) {
            return;
        }
        //进行处理.
        for (TradeEntrust tradeEntrustDo : tradeEntrustDoList) {
            RevokeRo revokeRo = new RevokeRo();
            revokeRo.setUserId(userId);
            revokeRo.setMockType(mockType);
            revokeRo.setId(tradeEntrustDo.getId());
            revokeRo.setEntrustType(EntrustType.AUTO.getCode());
            revokeBusiness.revoke(revokeRo);
        }
    }
}
