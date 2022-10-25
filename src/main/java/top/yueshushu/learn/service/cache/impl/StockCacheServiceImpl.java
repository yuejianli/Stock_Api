package top.yueshushu.learn.service.cache.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import top.yueshushu.learn.assembler.TradePositionHistoryAssembler;
import top.yueshushu.learn.common.Const;
import top.yueshushu.learn.domain.JobInfoDo;
import top.yueshushu.learn.domain.TradePositionHistoryDo;
import top.yueshushu.learn.domainservice.JobInfoDomainService;
import top.yueshushu.learn.domainservice.TradePositionHistoryDomainService;
import top.yueshushu.learn.entity.TradePositionHistoryCache;
import top.yueshushu.learn.helper.DateHelper;
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
    @Resource
    private DateHelper dateHelper;
    @Resource
    private JobInfoDomainService jobInfoDomainService;

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
        redisUtil.set(workingKey, isWorking, 8 * 3600);
        return workingDay;
    }

    @Override
    public String getJobInfoCronCacheByCode(String code) {
        String jobInfoKey = Const.JOB_INFO + code;

        String jobCronCache = redisUtil.get(jobInfoKey);
        if (!ObjectUtils.isEmpty(jobCronCache)) {
            return jobCronCache;
        }
        // 为空的话，进行计算。
        JobInfoDo jobInfoDo = jobInfoDomainService.getByCode(code);
        String cronResult = " ";
        if (jobInfoDomainService.isValid(jobInfoDo)) {
            cronResult = jobInfoDo.getCron();
        }
        redisUtil.set(jobInfoKey, cronResult);
        return cronResult;
    }

    @Override
    public void clearJobInfoCronCacheByCode(String code) {
        redisUtil.delByKey(Const.JOB_INFO + code);
    }
}
