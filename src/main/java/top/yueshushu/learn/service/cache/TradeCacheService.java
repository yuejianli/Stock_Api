package top.yueshushu.learn.service.cache;

import top.yueshushu.learn.enumtype.TradeRealValueType;

/**
 * @Description 交易的缓存信息
 * @Author yuejianli
 * @Date 2022/5/20 23:36
 **/
public interface TradeCacheService {
    /**
     * 是否需要同步真实的持仓信息,同步的话，需要返回 true,不需要的话，返回false
     * @return 是否需要同步真实的持仓信息,同步的话，需要返回 true,不需要的话，返回false
     * @param tradeRealValueType
     * @param userId 用户编号 为空时，表示全部
     */
    boolean needSyncReal(TradeRealValueType tradeRealValueType, Integer userId);

    /**
     * 立刻同步真实的持仓信息
     * @param userId 用户编号 为空时，表示全部
     */
    void immediatelySyncReal(TradeRealValueType tradeRealValueType,Integer userId);


}
