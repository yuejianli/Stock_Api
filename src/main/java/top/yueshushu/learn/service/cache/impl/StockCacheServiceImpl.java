package top.yueshushu.learn.service.cache.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import top.yueshushu.learn.assembler.TradePositionHistoryAssembler;
import top.yueshushu.learn.common.Const;
import top.yueshushu.learn.domain.JobInfoDo;
import top.yueshushu.learn.domain.TradeDealDo;
import top.yueshushu.learn.domain.TradePositionHistoryDo;
import top.yueshushu.learn.domainservice.JobInfoDomainService;
import top.yueshushu.learn.domainservice.TradeDealDomainService;
import top.yueshushu.learn.domainservice.TradePositionHistoryDomainService;
import top.yueshushu.learn.entity.Stock;
import top.yueshushu.learn.entity.StockHistory;
import top.yueshushu.learn.entity.TradePositionHistoryCache;
import top.yueshushu.learn.enumtype.ConfigCodeType;
import top.yueshushu.learn.enumtype.DealType;
import top.yueshushu.learn.helper.DateHelper;
import top.yueshushu.learn.mode.dto.TradeDealQueryDto;
import top.yueshushu.learn.mode.vo.ConfigVo;
import top.yueshushu.learn.service.ConfigService;
import top.yueshushu.learn.service.StockHistoryService;
import top.yueshushu.learn.service.StockService;
import top.yueshushu.learn.service.cache.StockCacheService;
import top.yueshushu.learn.util.RedisUtil;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Optional;

/**
 * @Description 股票相关的缓存实现 @Author yuejianli @Date 2022/5/20 23:38
 */
@Service("stockCacheService")
@Slf4j
public class StockCacheServiceImpl implements StockCacheService {
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private TradePositionHistoryDomainService tradePositionHistoryDomainService;
    @Resource
    private TradePositionHistoryAssembler tradePositionHistoryAssembler;
    @Resource
    private DateHelper dateHelper;
    @Resource
    private JobInfoDomainService jobInfoDomainService;
    @Resource
    private StockService stockService;
    @Resource
    private StockHistoryService stockHistoryService;
    @Resource
    private TradeDealDomainService tradeDealDomainService;
    @Resource
    private ConfigService configService;

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
            return Const.DEFAULT_PRICE;
        }
        return (BigDecimal) o;
    }

    private void setPrice(String key, BigDecimal price) {
        redisUtil.set(key, price);
    }

    private BigDecimal getPrice(String key, String code) {
        Object o = redisUtil.get(key);
        if (!ObjectUtils.isEmpty(o)) {
            return (BigDecimal) o;
        }
        StockHistory lastHistory = stockHistoryService.getLastHistory(code);

        if (null == lastHistory) {
            return Const.DEFAULT_PRICE;
        }
        BigDecimal price = null;
        if (key.startsWith(Const.STOCK_YES_CLOSE_PRICE)) {
            price = lastHistory.getClosingPrice();
        } else if (key.startsWith(Const.STOCK_YES_OPENING_PRICE)) {
            price = lastHistory.getOpeningPrice();
        } else if (key.startsWith(Const.STOCK_YES_HIGH_PRICE)) {
            price = lastHistory.getHighestPrice();
        } else if (key.startsWith(Const.STOCK_YES_LOWEST_PRICE)) {
            price = lastHistory.getLowestPrice();
        }
        setPrice(key, price);
        return price;
    }

    @Override
    public void setYesterdayCloseCachePrice(String code, BigDecimal price) {
        setPrice(Const.STOCK_YES_CLOSE_PRICE + code, price);
    }


    @Override
    public BigDecimal getYesterdayCloseCachePrice(String code) {
        return getPrice(Const.STOCK_YES_CLOSE_PRICE + code, code);
    }
    @Override
    public void clearYesPrice() {
        // 清空所有的
        redisUtil.deleteByPrefix(Const.STOCK_YES_CLOSE_PRICE);
    }

    @Override
    public void setYesterdayOpenCachePrice(String code, BigDecimal price) {
        setPrice(Const.STOCK_YES_OPENING_PRICE + code, price);
    }

    @Override
    public BigDecimal getYesterdayOpenCachePrice(String code) {
        return getPrice(Const.STOCK_YES_OPENING_PRICE + code, code);
    }

    @Override
    public void setYesterdayHighCachePrice(String code, BigDecimal price) {
        setPrice(Const.STOCK_YES_HIGH_PRICE + code, price);
    }

    @Override
    public BigDecimal getYesterdayHighCachePrice(String code) {
        return getPrice(Const.STOCK_YES_HIGH_PRICE + code, code);
    }

    @Override
    public void setYesterdayLowestCachePrice(String code, BigDecimal price) {
        setPrice(Const.STOCK_YES_LOWEST_PRICE + code, price);
    }

    @Override
    public BigDecimal getYesterdayLowestCachePrice(String code) {
        return getPrice(Const.STOCK_YES_LOWEST_PRICE + code, code);
    }


    private void setDealPrice(String key, BigDecimal price) {
        redisUtil.set(key, price, Const.STOCK_PRICE_EXPIRE_TIME);
    }

    private BigDecimal getDealPrice(String key, Integer userId, Integer mockType, String code, Integer dealType) {
        Object o = redisUtil.get(key);
        if (!ObjectUtils.isEmpty(o)) {
            return (BigDecimal) o;
        }

        // 获取最后的成交记录.
        TradeDealQueryDto tradeDealQueryDto = new TradeDealQueryDto();
        tradeDealQueryDto.setUserId(userId);
        tradeDealQueryDto.setMockType(mockType);
        tradeDealQueryDto.setCode(code);
        tradeDealQueryDto.setDealType(dealType);

        TradeDealDo tradeDealDo = tradeDealDomainService.getLastDeal(tradeDealQueryDto);

        if (null == tradeDealDo) {
            return Const.DEFAULT_PRICE;
        }
        setDealPrice(key, tradeDealDo.getDealPrice());
        return tradeDealDo.getDealPrice();
    }

    @Override
    public void setLastBuyCachePrice(Integer userId, Integer mockType, String code, BigDecimal price) {
        setDealPrice(Const.STOCK_LAST_BUY_PRICE + mockType + ":" + userId + ":" + code, price);
    }


    @Override
    public BigDecimal getLastBuyCachePrice(Integer userId, Integer mockType, String code) {
        return getDealPrice(Const.STOCK_LAST_BUY_PRICE + mockType + ":" + userId + ":" + code, userId, mockType, code, DealType.BUY.getCode());
    }


    @Override
    public void setLastSellCachePrice(Integer userId, Integer mockType, String code, BigDecimal price) {
        setDealPrice(Const.STOCK_LAST_SELL_PRICE + mockType + ":" + userId + ":" + code, price);
    }


    @Override
    public BigDecimal getLastSellCachePrice(Integer userId, Integer mockType, String code) {
        return getDealPrice(Const.STOCK_LAST_SELL_PRICE + mockType + ":" + userId + ":" + code, userId, mockType, code, DealType.SELL.getCode());
    }


    private void setTodayDealPrice(String key, BigDecimal price) {
        redisUtil.set(key, price, Const.STOCK_TODAY_PRICE_EXPIRE_TIME);
    }

    private BigDecimal getTodayDealPrice(String key, Integer userId, Integer mockType, String code, Integer dealType) {
        Object o = redisUtil.get(key);
        if (!ObjectUtils.isEmpty(o)) {
            return (BigDecimal) o;
        }
        return null;
    }

    @Override
    public void setTodayBuyCachePrice(Integer userId, Integer mockType, String code, BigDecimal price) {
        setTodayDealPrice(Const.STOCK_TODAY_BUY_PRICE + mockType + ":" + userId + ":" + code, price);
    }


    @Override
    public BigDecimal getTodayBuyCachePrice(Integer userId, Integer mockType, String code) {
        return getTodayDealPrice(Const.STOCK_TODAY_BUY_PRICE + mockType + ":" + userId + ":" + code, userId, mockType, code, DealType.BUY.getCode());
    }


    @Override
    public void setTodaySellCachePrice(Integer userId, Integer mockType, String code, BigDecimal price) {
        setTodayDealPrice(Const.STOCK_TODAY_SELL_PRICE + mockType + ":" + userId + ":" + code, price);
    }


    @Override
    public BigDecimal getTodaySellCachePrice(Integer userId, Integer mockType, String code) {
        return getTodayDealPrice(Const.STOCK_TODAY_SELL_PRICE + mockType + ":" + userId + ":" + code, userId, mockType, code, DealType.SELL.getCode());
    }


    @Override
    public void reduceTodayBuySurplusNum(Integer userId, Integer mockType, String code) {
        redisUtil.increment(Const.STOCK_TODAY_BUY_NUM + mockType + ":" + userId + ":" + code, -1);
    }

    @Override
    public Long getTodayBuySurplusNum(Integer userId, Integer mockType, String code) {
        String key = Const.STOCK_TODAY_BUY_NUM + mockType + ":" + userId + ":" + code;
        Object o = redisUtil.get(key);
        if (!ObjectUtils.isEmpty(o)) {
            return Long.valueOf(o.toString());
        }
        // 如果没有，则获取后设置.
        ConfigVo configInfo = configService.getConfigByCode(userId, ConfigCodeType.TODAY_BUY_NUM.getCode());
        long buyNum = Long.valueOf(configInfo.getCodeValue());
        // 存储。
        redisUtil.set(key, buyNum, Const.STOCK_TODAY_PRICE_EXPIRE_TIME);
        return buyNum;
    }

    @Override
    public void reduceTodaySellSurplusNum(Integer userId, Integer mockType, String code) {
        redisUtil.increment(Const.STOCK_TODAY_SELL_NUM + mockType + ":" + userId + ":" + code, -1);
    }

    @Override
    public Long getTodaySellSurplusNum(Integer userId, Integer mockType, String code) {
        String key = Const.STOCK_TODAY_SELL_NUM + mockType + ":" + userId + ":" + code;
        Object o = redisUtil.get(key);
        if (!ObjectUtils.isEmpty(o)) {
            return Long.valueOf(o.toString());
        }
        // 如果没有，则获取后设置.
        ConfigVo configInfo = configService.getConfigByCode(userId, ConfigCodeType.TODAY_SELL_NUM.getCode());
        long sellNum = Long.valueOf(configInfo.getCodeValue());
        // 存储。
        redisUtil.set(key, sellNum, Const.STOCK_TODAY_PRICE_EXPIRE_TIME);
        return sellNum;
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
        redisUtil.set(key, Optional.ofNullable(tradePositionHistoryCache).orElse(new TradePositionHistoryCache()), Const.STOCK_PRICE_EXPIRE_TIME);

        return tradePositionHistoryCache;
    }

    @Override
    public void cleanLastTradePositionHistory() {
        redisUtil.deleteByPrefix(Const.POSITION_HISTORY);
    }

    @Override
    public boolean isWorkingDay(DateTime currDate) {
        String currDateStr = DateUtil.format(currDate, DatePattern.NORM_DATE_PATTERN);

        String workingKey = Const.DATE_WORKING + currDateStr;

        boolean isWorking = redisUtil.get(workingKey);
        if (!ObjectUtils.isEmpty(isWorking)) {
            return isWorking;
        }
        // 为空的话，进行计算。
        boolean workingDay = dateHelper.isWorkingDay(currDate);
        // 8 个小时 清空一次 即可。
        redisUtil.set(workingKey, isWorking, 24 * 3600);
        return workingDay;
    }

    @Override
    public String getJobInfoCronCacheByCode(String code) {
        String jobInfoKey = Const.JOB_INFO + code;

        String jobCronCache = redisUtil.get(jobInfoKey);
        if (StringUtils.hasText(jobCronCache)) {
            return jobCronCache;
        }
        // 为空的话，进行计算。
        JobInfoDo jobInfoDo = jobInfoDomainService.getByCode(code);
        String cronResult = " ";
        if (jobInfoDomainService.isValid(jobInfoDo)) {
            cronResult = jobInfoDo.getCron();
        }
        redisUtil.set(jobInfoKey, cronResult, Const.JOB_CRON_EXPIRE_TIME);
        return cronResult;
    }

    @Override
    public void clearJobInfoCronCacheByCode(String code) {
        redisUtil.delByKey(Const.JOB_INFO + code);
    }

    @Override
    public Stock selectByCode(String code) {
        Object stockInfo = redisUtil.hGet(Const.CACHE_STOCK_INFO, code);
        if (null != stockInfo) {
            return (Stock) stockInfo;
        }
        // 查询一个数据库
        Stock stock = stockService.selectByCode(code);
        // 往里面放置
        redisUtil.hPut(Const.CACHE_STOCK_INFO, code, stock);
        return stock;
    }

    @Override
    public void clearStockInfo() {
        // 清除缓存， 是一个 map 形式。
        redisUtil.delByKey(Const.CACHE_STOCK_INFO);
    }
}
