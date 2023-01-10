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
     * @param userId 用户编号
     */
    void buildRealEasyMoneyCache(TradeRealValueType tradeRealValueType, Integer userId, Object value);


    /**
     * 获取缓存信息
     *
     * @param userId 用户编号
     */
    Object getRealEasyMoneyCache(TradeRealValueType tradeRealValueType, Integer userId);
}
