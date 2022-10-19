package top.yueshushu.learn.service.cache.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import top.yueshushu.learn.assembler.TradePositionHistoryAssembler;
import top.yueshushu.learn.common.Const;
import top.yueshushu.learn.domain.TradePositionHistoryDo;
import top.yueshushu.learn.domainservice.TradePositionHistoryDomainService;
import top.yueshushu.learn.entity.TradePositionHistoryCache;
import top.yueshushu.learn.service.cache.StockCacheService;
import top.yueshushu.learn.util.RedisUtil;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Optional;

/**
 * @Description 股票相关的缓存实现 @Author yuejianli @Date 2022/5/20 23:38
 */
@Service
@Slf4j
public class StockCacheServiceImpl implements StockCacheService {
    private static final BigDecimal DEFAULT_PRICE = BigDecimal.ZERO;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private TradePositionHistoryDomainService tradePositionHistoryDomainService;
    @Resource
    private TradePositionHistoryAssembler tradePositionHistoryAssembler;

    @Override
    public void setNowCachePrice(String code, BigDecimal price) {
        redisUtil.set(Const.STOCK_PRICE + code, price);
    }

    @Override
    public void deleteNowCachePrice(String code) {
        redisUtil.delByKey(Const.STOCK_PRICE + code);
    }

    @Override
    public BigDecimal getNowCachePrice(String code) {
        Object o = redisUtil.get(Const.STOCK_PRICE + code);
        if (ObjectUtils.isEmpty(o)) {
            return DEFAULT_PRICE;
        }
        return (BigDecimal) o;
    }

    @Override
    public void setYesterdayCloseCachePrice(String code, BigDecimal price) {
        redisUtil.set(Const.STOCK_YES_PRICE + code, price);
    }

    @Override
    public void deleteYesterdayCloseCachePrice(String code) {
        redisUtil.delByKey(Const.STOCK_YES_PRICE + code);
    }

    @Override
    public BigDecimal getYesterdayCloseCachePrice(String code) {
        Object o = redisUtil.get(Const.STOCK_YES_PRICE + code);
        if (ObjectUtils.isEmpty(o)) {
            return DEFAULT_PRICE;
        }
        return (BigDecimal) o;
    }

    @Override
    public void setLastBuyCachePrice(String code, BigDecimal price) {
        redisUtil.set(Const.STOCK_BUY_PRICE + code, price);
    }

    @Override
    public void deleteLastBuyCachePrice(String code) {
        redisUtil.delByKey(Const.STOCK_BUY_PRICE + code);
    }

    @Override
    public BigDecimal getLastBuyCachePrice(String code) {
        Object o = redisUtil.get(Const.STOCK_BUY_PRICE + code);
        if (ObjectUtils.isEmpty(o)) {
            return DEFAULT_PRICE;
        }
        return (BigDecimal) o;
    }

    @Override
    public void setLastSellCachePrice(String code, BigDecimal price) {
        redisUtil.set(Const.STOCK_SELL_PRICE + code, price);
    }

    @Override
    public void deleteLastSellCachePrice(String code) {
        redisUtil.delByKey(Const.STOCK_SELL_PRICE + code);
    }

    @Override
    public BigDecimal getLastSellCachePrice(String code) {
        Object o = redisUtil.get(Const.STOCK_SELL_PRICE + code);
        if (ObjectUtils.isEmpty(o)) {
            return DEFAULT_PRICE;
        }
        return (BigDecimal) o;
    }

    @Override
    public TradePositionHistoryCache getLastTradePositionByCode(Integer userId, Integer mockType, String code) {
        String key = Const.POSITION_HISTORY + userId + "_" + mockType + ":" + code;
        TradePositionHistoryCache positionHistoryDo = redisUtil.get(key);
        if (!ObjectUtils.isEmpty(positionHistoryDo)) {
            return positionHistoryDo;
        }
        // 查询一下
        TradePositionHistoryDo lastHistoryDo = tradePositionHistoryDomainService.getLastRecordByCode(userId, mockType, code);

        TradePositionHistoryCache tradePositionHistoryCache = tradePositionHistoryAssembler.doToCache(lastHistoryDo);
        redisUtil.set(key, Optional.ofNullable(tradePositionHistoryCache).orElse(new TradePositionHistoryCache()));

        return tradePositionHistoryCache;
    }

    @Override
    public void cleanLastTradePositionHistory() {
        redisUtil.deleteByPrefix(Const.POSITION_HISTORY);
    }

}
