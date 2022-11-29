package top.yueshushu.learn.service.cache;

import cn.hutool.core.date.DateTime;
import top.yueshushu.learn.entity.Stock;
import top.yueshushu.learn.entity.TradePositionHistoryCache;

import java.math.BigDecimal;

/**
 * @Description 股票的的缓存信息
 * @Author yuejianli
 * @Date 2022/5/20 23:36
 **/
public interface StockCacheService {
    /**
     * 设置股票的当前价格缓存信息
     *
     * @param code  股票代码
     * @param price 股票的价格
     */
    void setNowCachePrice(String code, BigDecimal price);

    /**
     * 删除当前价格的缓存信息
     *
     * @param code 股票代码
     */
    void deleteNowCachePrice(String code);


    /**
     * 获取当前缓存中的股票的当前价格信息
     *
     * @param code 股票代码
     */
    BigDecimal getNowCachePrice(String code);


    /**
     * 设置股票的昨天的收盘价
     *
     * @param code  股票代码
     * @param price 股票的价格
     */
    void setYesterdayCloseCachePrice(String code, BigDecimal price);


    /**
     * 删除股票的昨天的收盘价
     *
     * @param code 股票代码
     */
    void deleteYesterdayCloseCachePrice(String code);


    /**
     * 获取缓存中股票的昨天的收盘价
     *
     * @param code 股票代码
     */
    BigDecimal getYesterdayCloseCachePrice(String code);

    /**
     * 清空所有的昨日价格
     */
    void clearYesPrice();

    /**
     * 设置缓存中股票的最近一次买入的价格  第二天为重置成昨晚的收盘价
     *
     * @param code  股票代码
     * @param price 股票的价格
     */
    void setLastBuyCachePrice(String code, BigDecimal price);

    /**
     * 删除缓存中股票的最近一次买入的价格  第二天为重置成昨晚的收盘价
     *
     * @param code 股票代码
     */
    void deleteLastBuyCachePrice(String code);

    /**
     * 获取缓存中股票的最近一次买入的价格
     *
     * @param code 股票代码
     */
    BigDecimal getLastBuyCachePrice(String code);

    /**
     * 设置缓存中股票的最近一次卖出的价格  第二天为重置成昨晚的收盘价
     *
     * @param code  股票代码
     * @param price 股票的价格
     */
    void setLastSellCachePrice(String code, BigDecimal price);

    /**
     * 删除缓存中股票的最近一次卖出的价格  第二天为重置成昨晚的收盘价
     *
     * @param code 股票代码
     */
    void deleteLastSellCachePrice(String code);

    /**
     * 获取缓存中股票的最近一次卖出的价格
     *
     * @param code 股票代码
     */
    BigDecimal getLastSellCachePrice(String code);

    /**
     * @param userId   用户编号
     * @param mockType 类型
     * @param code     股票代码
     * @return 返回最近的历史记录
     */
    TradePositionHistoryCache getLastTradePositionByCode(Integer userId, Integer mockType, String code);

    /**
     * 清理缓存信息
     */
    void cleanLastTradePositionHistory();

    /**
     * 当前日期是否是工作日，直接对缓存进行处理。
     *
     * @param currDate 当前日期
     */
    boolean isWorkingDay(DateTime currDate);

    /**
     * 根据任务编码，获取对应的 cron.
     * 如果任务被禁用，则 cron 为 null
     *
     * @param code 任务编码
     */
    String getJobInfoCronCacheByCode(String code);


    /**
     * 删除 jobInfo 的缓存信息
     */
    void clearJobInfoCronCacheByCode(String code);


    /**
     * 根据股票的编码，获取股票的信息
     */
    Stock selectByCode(String code);

    /**
     * 清除股票的集合信息
     */
    void clearStockInfo();

}
