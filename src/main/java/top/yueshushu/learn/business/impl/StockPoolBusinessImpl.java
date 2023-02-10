package top.yueshushu.learn.business.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.yueshushu.learn.business.StockPoolBusiness;
import top.yueshushu.learn.crawler.crawler.ExtCrawlerService;
import top.yueshushu.learn.crawler.entity.StockPoolInfo;
import top.yueshushu.learn.enumtype.DBStockType;
import top.yueshushu.learn.enumtype.StockCodeType;
import top.yueshushu.learn.enumtype.StockPoolType;
import top.yueshushu.learn.enumtype.message.VelocityTemplateType;
import top.yueshushu.learn.message.email.EmailService;
import top.yueshushu.learn.message.weixin.service.WeChatService;
import top.yueshushu.learn.service.cache.StockCacheService;
import top.yueshushu.learn.util.MyDateUtil;

import javax.annotation.Resource;
import java.util.*;

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

    @Override
    public void handlerPool(Date date) {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("line", "\r\n");
        dataMap.put("currTime", DateUtil.now());
        dataMap.put("currDate", DateUtil.format(DateUtil.date(), DatePattern.NORM_DATE_PATTERN));
        //1. 交易时间的话，只发送强势股。
        if (MyDateUtil.isDealTime()) {
            List<StockPoolInfo> poolList = extCrawlerService.findPoolByType(StockPoolType.QS, date);
            if (CollectionUtils.isEmpty(poolList)) {
                return;
            }
            poolList = filterStockList(poolList, DBStockType.SH_SZ);
            // 获取所有的 股票编号和密码组装的信息
            dataMap.put("qcCodeList", poolList);
        } else {
            // 非交易时间，是分析时间的话，发送信息.
            List<StockPoolInfo> poolList = extCrawlerService.findPoolByType(StockPoolType.ZT, date);
            if (!CollectionUtils.isEmpty(poolList)) {
                poolList = filterStockList(poolList, DBStockType.SH_SZ);
                // 获取所有的 股票编号和密码组装的信息
                dataMap.put("ztCodeList", poolList);
            }
            poolList = extCrawlerService.findPoolByType(StockPoolType.DT, date);
            if (!CollectionUtils.isEmpty(poolList)) {
                poolList = filterStockList(poolList, DBStockType.SH_SZ);
                // 获取所有的 股票编号和密码组装的信息
                dataMap.put("dtCodeList", poolList);
            }
            poolList = extCrawlerService.findPoolByType(StockPoolType.CX, date);
            if (!CollectionUtils.isEmpty(poolList)) {
                poolList = filterStockList(poolList, DBStockType.SH_SZ);
                // 获取所有的 股票编号和密码组装的信息
                dataMap.put("cxCodeList", poolList);
            }
        }

        if (dataMap.size() > 3) {
            String velocityContent = emailService.getVelocityContent(VelocityTemplateType.POOL, dataMap);
            weChatService.sendTextMessage(1, velocityContent);
        }
    }

    /**
     * 过滤股票信息
     */
    private List<StockPoolInfo> filterStockList(List<StockPoolInfo> poolList, DBStockType dbStockType) {
        if (null == dbStockType) {
            return poolList;
        }
        List<StockPoolInfo> result = new ArrayList<>();

        for (StockPoolInfo stockPoolInfo : poolList) {
            StockCodeType typeByStockCode = StockCodeType.getTypeByStockCode(stockPoolInfo.getCode());
            if (null == typeByStockCode) {
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
