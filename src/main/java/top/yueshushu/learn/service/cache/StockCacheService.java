package top.yueshushu.learn.service.cache;

import cn.hutool.core.date.DateTime;
import top.yueshushu.learn.crawler.entity.StockIndexInfo;
import top.yueshushu.learn.entity.Stock;
import top.yueshushu.learn.entity.TradePositionHistoryCache;

import java.math.BigDecimal;
import java.util.List;

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
     * 获取缓存中股票的昨天的收盘价
     *
     * @param code 股票代码
     */
    BigDecimal getYesterdayCloseCachePrice(String code);

    /**
     * 设置股票的昨天的开盘价
     *
     * @param code  股票代码
     * @param price 股票的价格
     */
    void setYesterdayOpenCachePrice(String code, BigDecimal price);

    /**
     * 获取缓存中股票的昨天的开盘价
     *
     * @param code 股票代码
     */
    BigDecimal getYesterdayOpenCachePrice(String code);


    /**
     * 设置股票的昨天的最高价
     *
     * @param code  股票代码
     * @param price 股票的价格
     */
    void setYesterdayHighCachePrice(String code, BigDecimal price);

    /**
     * 获取缓存中股票的昨天的最高价
     *
     * @param code 股票代码
     */
    BigDecimal getYesterdayHighCachePrice(String code);

    /**
     * 设置股票的昨天的开盘价
     *
     * @param code  股票代码
     * @param price 股票的价格
     */
    void setYesterdayLowestCachePrice(String code, BigDecimal price);

    /**
     * 获取缓存中股票的昨天的开盘价
     *
     * @param code 股票代码
     */
    BigDecimal getYesterdayLowestCachePrice(String code);


    /**
     * 清空所有的昨日价格
     */
    void clearYesPrice();

    /**
     * 设置缓存中股票的最近一次买入成交的价格
     *
     * @param userId   用户编号
     * @param mockType 类型
     * @param code     股票代码
     * @param price    股票的价格
     */
    void setLastBuyCachePrice(Integer userId, Integer mockType, String code, BigDecimal price);

    /**
     * 获取缓存中股票的最近一次买入的价格
     *
     * @param userId   用户编号
     * @param mockType 类型
     * @param code     股票代码
     */
    BigDecimal getLastBuyCachePrice(Integer userId, Integer mockType, String code);


    /**
     * 设置缓存中股票的最近一次卖出的价格
     *
     * @param userId
     * @param mockType
     * @param code     股票代码
     * @param price    股票的价格
     */
    void setLastSellCachePrice(Integer userId, Integer mockType, String code, BigDecimal price);


    /**
     * 获取缓存中股票的最近一次卖出的价格
     *
     * @param userId
     * @param mockType
     * @param code     股票代码
     */
    BigDecimal getLastSellCachePrice(Integer userId, Integer mockType, String code);


    /**
     * 设置缓存中股票的今日一次买入成交的价格
     *
     * @param userId   用户编号
     * @param mockType 类型
     * @param code     股票代码
     * @param price    股票的价格
     */
    void setTodayBuyCachePrice(Integer userId, Integer mockType, String code, BigDecimal price);

    /**
     * 获取缓存中股票的最近一次买入的价格
     *
     * @param userId   用户编号
     * @param mockType 类型
     * @param code     股票代码
     */
    BigDecimal getTodayBuyCachePrice(Integer userId, Integer mockType, String code);


    /**
     * 设置缓存中股票的最近一次卖出的价格
     *
     * @param userId
     * @param mockType
     * @param code     股票代码
     * @param price    股票的价格
     */
    void setTodaySellCachePrice(Integer userId, Integer mockType, String code, BigDecimal price);


    /**
     * 获取缓存中股票的最近一次卖出的价格
     *
     * @param userId
     * @param mockType
     * @param code     股票代码
     */
    BigDecimal getTodaySellCachePrice(Integer userId, Integer mockType, String code);


    /**
     * 减少该股票可买次数
     *
     * @param userId   用户编号
     * @param mockType 类型
     * @param code     股票代码
     */
    void reduceTodayBuySurplusNum(Integer userId, Integer mockType, String code);

    /**
     * 获取今日可买次数
     *
     * @param userId   用户编号
     * @param mockType 类型
     * @param code     股票代码
     */
    Long getTodayBuySurplusNum(Integer userId, Integer mockType, String code);

    /**
     * 减少该股票的可卖次数
     *
     * @param userId   用户编号
     * @param mockType 类型
     * @param code     股票代码
     */
    void reduceTodaySellSurplusNum(Integer userId, Integer mockType, String code);

    /**
     * 获取该股票今日的可卖次数
     *
     * @param userId   用户编号
     * @param mockType 类型
     * @param code     股票代码
     */
    Long getTodaySellSurplusNum(Integer userId, Integer mockType, String code);


    /**
     * 减少该股票 今日打版 可买次数
     *
     * @param userId   用户编号
     * @param mockType 类型
     * @param code     股票代码
     */
    void reduceTodayBuyDBSurplusNum(Integer userId, Integer mockType, String code);

    /**
     * 获取今日打版可买次数
     *
     * @param userId   用户编号
     * @param mockType 类型
     * @param code     股票代码
     */
    Long getTodayBuyDBSurplusNum(Integer userId, Integer mockType, String code);


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


    /**
     * 设置昨天涨停股票编码集合
     */
    void setYesZtCodeList(List<String> codeList);

    /**
     * 获取缓存中昨天涨停的股票信息
     */
    List<String> getYesZtCodeList();

    /**
     * 设置今日涨停股票编码集合
     */
    void setTodayZtCodeList(List<String> codeList);

    /**
     * 获取缓存中今日涨停的股票信息
     */
    List<String> getTodayZtCodeList();


    /**
     * 添加今日打板买入的股票
     *
     * @param userId   用户编号
     * @param mockType 类型
     * @param code     股票代码
     */
    void addTodayDBCode(Integer userId, Integer mockType, String code);

    /**
     * 获取今日打板买入的股票
     *
     * @param userId   用户编号
     * @param mockType 类型
     */
    List<String> getTodayDBCodeList(Integer userId, Integer mockType);

    /**
     * 更新股票指数信息
     *
     * @param stockIndexInfo 股票指数信息
     */
    void updateStockIndex(StockIndexInfo stockIndexInfo);
}
