package top.yueshushu.learn.service.cache;

import top.yueshushu.learn.enumtype.TradeRealValueType;

/**
 * @Description 交易的缓存信息
 * @Author yuejianli
 * @Date 2022/5/20 23:36
 **/
public interface TradeCacheService {

    /**
     * 清理缓存信息
     *
     * @param userId 用户编号
     */
    void removeRealEasyMoneyCache(TradeRealValueType tradeRealValueType, Integer userId);


    /**
     * 保存缓存信息
     *
     * @param userId  用户编号
     * @param seconds 保存的秒数， 如果没有，则用默认的。
     */
    void buildRealEasyMoneyCache(TradeRealValueType tradeRealValueType, Integer userId, Object value, Integer seconds);


    /**
     * 获取缓存信息
     *
     * @param userId 用户编号
     */
    Object getRealEasyMoneyCache(TradeRealValueType tradeRealValueType, Integer userId);
}
