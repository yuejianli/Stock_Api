package top.yueshushu.learn.business.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import top.yueshushu.learn.business.StockPoolBusiness;
import top.yueshushu.learn.common.Const;
import top.yueshushu.learn.crawler.crawler.ExtCrawlerService;
import top.yueshushu.learn.crawler.entity.StockPoolInfo;
import top.yueshushu.learn.enumtype.DBStockType;
import top.yueshushu.learn.enumtype.StockCodeType;
import top.yueshushu.learn.enumtype.StockPoolType;
import top.yueshushu.learn.enumtype.message.VelocityTemplateType;
import top.yueshushu.learn.message.dingtalk.DingTalkService;
import top.yueshushu.learn.message.email.EmailService;
import top.yueshushu.learn.message.weixin.service.WeChatService;
import top.yueshushu.learn.service.StockPoolHistoryService;
import top.yueshushu.learn.service.cache.StockCacheService;
import top.yueshushu.learn.util.MyDateUtil;
import top.yueshushu.learn.util.RedisUtil;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 用途描述
 *
 * @author yuejianli
 * @date 2023-02-10
 */
@Service
@Slf4j
public class StockPoolBusinessImpl implements StockPoolBusiness {
    @Resource
    private StockCacheService stockCacheService;
    @Resource
    private WeChatService weChatService;
    @Resource
    private ExtCrawlerService extCrawlerService;
    @Resource
    private EmailService emailService;
    @Resource
    private DingTalkService dingTalkService;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private StockPoolHistoryService stockPoolHistoryService;

    @Override
    public void handlerPool(Date date) {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("line", "\r\n");
        dataMap.put("currTime", DateUtil.now());
        dataMap.put("currDate", DateUtil.format(DateUtil.date(), DatePattern.NORM_DATE_PATTERN));
        //1. 交易时间的话，只发送强势股。
        if (!MyDateUtil.isEveningStat()) {
            List<StockPoolInfo> poolList = extCrawlerService.findPoolByType(StockPoolType.QS, date);
            if (CollectionUtils.isEmpty(poolList)) {
                return;
            }
            poolList = filterStockList(poolList, DBStockType.SH_SZ, StockPoolType.QS);
            String key = Const.STOCK_TODAY_QS_CODE;
            Set<String> addCodeList = poolList.stream().map(StockPoolInfo::getCode).collect(Collectors.toSet());
            if (!CollectionUtils.isEmpty(addCodeList)) {
                redisUtil.sAdd(key, addCodeList.toArray(new String[]{}));
            }
            // 获取所有的 股票编号和密码组装的信息
            dataMap.put("qcCodeList", poolList);
            List<StockPoolInfo> poolZtList = extCrawlerService.findPoolByType(StockPoolType.ZT, date);
            if (!CollectionUtils.isEmpty(poolZtList)) {
                poolZtList = filterStockList(poolZtList, DBStockType.SH_SZ, null);
                // 获取 codeList
                List<String> codeList = poolZtList.stream().map(StockPoolInfo::getCode).collect(Collectors.toList());
                stockCacheService.setTodayZtCodeList(codeList);
            }

        } else {
            // 非交易时间，是分析时间的话，发送信息.
            List<StockPoolInfo> poolZtList = extCrawlerService.findPoolByType(StockPoolType.ZT, date);
            if (!CollectionUtils.isEmpty(poolZtList)) {
                poolZtList = filterStockList(poolZtList, DBStockType.SH_SZ, null);
                stockPoolHistoryService.savePoolHistory(poolZtList);
                // 获取所有的 股票编号和密码组装的信息
                dataMap.put("ztCodeList", poolZtList);
                // 获取 codeList
                List<String> codeList = poolZtList.stream().map(StockPoolInfo::getCode).collect(Collectors.toList());
                stockCacheService.setYesZtCodeList(codeList);
            }
            List<StockPoolInfo> poolDtList = extCrawlerService.findPoolByType(StockPoolType.DT, date);
            if (!CollectionUtils.isEmpty(poolDtList)) {
                poolDtList = filterStockList(poolDtList, DBStockType.SH_SZ, null);
                stockPoolHistoryService.savePoolHistory(poolDtList);
                // 获取所有的 股票编号和密码组装的信息
                dataMap.put("dtCodeList", poolDtList);
            }
            List<StockPoolInfo> poolCxList = extCrawlerService.findPoolByType(StockPoolType.CX, date);
            if (!CollectionUtils.isEmpty(poolCxList)) {
                poolCxList = filterStockList(poolCxList, DBStockType.SH_SZ, null);
                stockPoolHistoryService.savePoolHistory(poolCxList);
                // 获取所有的 股票编号和密码组装的信息
                dataMap.put("cxCodeList", poolCxList);
            }
            List<StockPoolInfo> poolQsList = extCrawlerService.findPoolByType(StockPoolType.QS, date);
            if (!CollectionUtils.isEmpty(poolQsList)) {
                poolQsList = filterStockList(poolQsList, DBStockType.SH_SZ, null);
                stockPoolHistoryService.savePoolHistory(poolQsList);
            }

            List<StockPoolInfo> poolZbList = extCrawlerService.findPoolByType(StockPoolType.ZB, date);
            if (!CollectionUtils.isEmpty(poolZbList)) {
                poolZbList = filterStockList(poolZbList, DBStockType.SH_SZ, null);
                stockPoolHistoryService.savePoolHistory(poolZbList);
            }
        }

        if (dataMap.size() > 3) {
            String velocityContent = emailService.getVelocityContent(VelocityTemplateType.POOL, dataMap);
            weChatService.sendTextMessage(1, velocityContent);
            dingTalkService.sendTextMessage(null, velocityContent);
        }
    }

    /**
     * 过滤股票信息
     */
    private List<StockPoolInfo> filterStockList(List<StockPoolInfo> poolList, DBStockType dbStockType,
                                                StockPoolType stockPoolType) {
        if (null == dbStockType) {
            return poolList;
        }
        Set<String> codeList = Collections.emptySet();
        if (StockPoolType.QS.equals(stockPoolType)) {
            String key = Const.STOCK_TODAY_QS_CODE;
            // 获取到今日的已经发送的强势股票
            Object qsRangeList = redisUtil.sMembers(key);
            if (ObjectUtils.isEmpty(qsRangeList)) {
                codeList = Collections.emptySet();
            } else {
                codeList = (Set<String>) qsRangeList;
            }
        }
        List<StockPoolInfo> result = new ArrayList<>();
        for (StockPoolInfo stockPoolInfo : poolList) {
            StockCodeType typeByStockCode = StockCodeType.getTypeByStockCode(stockPoolInfo.getCode());
            if (null == typeByStockCode) {
                continue;
            }
            if (codeList.contains(stockPoolInfo.getCode())) {
                continue;
            }
            if (!dbStockType.contains(typeByStockCode)) {
                continue;
            }
            // 放置进去
            result.add(stockPoolInfo);
        }
        return result;
    }
}
