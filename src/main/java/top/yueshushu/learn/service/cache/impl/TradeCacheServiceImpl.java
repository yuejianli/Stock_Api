package top.yueshushu.learn.service.cache.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import top.yueshushu.learn.common.Const;
import top.yueshushu.learn.enumtype.TradeRealValueType;
import top.yueshushu.learn.service.cache.TradeCacheService;
import top.yueshushu.learn.util.RedisUtil;

import javax.annotation.Resource;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @Description 交易相关的缓存实现
 * @Author yuejianli
 * @Date 2022/5/20 23:38
 **/
@Service
@Slf4j
public class TradeCacheServiceImpl implements TradeCacheService {
    @Resource
    private RedisUtil redisUtil;
    @Value("${easyMoney.second:300}")
    private Integer easyMoneySecond;

    @Override
    public void removeRealEasyMoneyCache(TradeRealValueType tradeRealValueType, Integer userId) {
        redisUtil.remove(buildCacheKey(tradeRealValueType, userId));
    }

    @Override
    public void buildRealEasyMoneyCache(TradeRealValueType tradeRealValueType, Integer userId, Object value, Integer seconds) {
        redisUtil.set(buildCacheKey(tradeRealValueType, userId), value, Optional.ofNullable(seconds).orElse(easyMoneySecond), TimeUnit.SECONDS);
    }

    @Override
    public Object getRealEasyMoneyCache(TradeRealValueType tradeRealValueType, Integer userId) {
        return Optional.ofNullable(redisUtil.get(buildCacheKey(tradeRealValueType, userId))).orElse(null);
    }


    private String buildCacheKey(TradeRealValueType tradeRealValueType, Integer userId) {
        return Const.CACHE_PRIVATE_KEY_PREFIX + userId + ":" + tradeRealValueType.getCode();
    }


}
