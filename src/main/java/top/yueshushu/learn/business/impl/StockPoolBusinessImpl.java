package top.yueshushu.learn.business.impl;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import top.yueshushu.learn.assembler.StockPoolHistoryAssembler;
import top.yueshushu.learn.business.StockPoolBusiness;
import top.yueshushu.learn.common.Const;
import top.yueshushu.learn.common.SystemConst;
import top.yueshushu.learn.crawler.crawler.ExtCrawlerService;
import top.yueshushu.learn.crawler.entity.StockPoolInfo;
import top.yueshushu.learn.enumtype.AmplitudeType;
import top.yueshushu.learn.enumtype.DBStockType;
import top.yueshushu.learn.enumtype.StockCodeType;
import top.yueshushu.learn.enumtype.StockPoolType;
import top.yueshushu.learn.enumtype.message.VelocityTemplateType;
import top.yueshushu.learn.helper.DateHelper;
import top.yueshushu.learn.message.dingtalk.DingTalkService;
import top.yueshushu.learn.message.email.EmailService;
import top.yueshushu.learn.message.weixin.service.WeChatService;
import top.yueshushu.learn.mode.dto.StockPoolQueryDto;
import top.yueshushu.learn.mode.ro.StockPoolRo;
import top.yueshushu.learn.mode.vo.DistVo;
import top.yueshushu.learn.mode.vo.StockHistoryVo;
import top.yueshushu.learn.mode.vo.ten10stat.HistoryRelationVo;
import top.yueshushu.learn.mode.vo.ten10stat.StockRelationVo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.response.PageResponse;
import top.yueshushu.learn.service.StockHistoryService;
import top.yueshushu.learn.service.StockPoolHistoryService;
import top.yueshushu.learn.service.cache.StockCacheService;
import top.yueshushu.learn.util.BigDecimalUtil;
import top.yueshushu.learn.util.MyDateUtil;
import top.yueshushu.learn.util.PageUtil;
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
    @Resource
    private StockPoolHistoryAssembler stockPoolHistoryAssembler;
    @Resource
    private StockHistoryService stockHistoryService;
    @Resource
    private DateHelper dateHelper;

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
            if (!CollectionUtils.isEmpty(poolList)) {
                // 获取所有的 股票编号和密码组装的信息
                dataMap.put("qcCodeList", poolList);
            }
            List<StockPoolInfo> poolZtList = extCrawlerService.findPoolByType(StockPoolType.ZT, date);
            if (!CollectionUtils.isEmpty(poolZtList)) {
                poolZtList = filterStockList(poolZtList, DBStockType.SH_SZ, null);
                // 获取 codeList
                List<String> codeList = poolZtList.stream().map(StockPoolInfo::getCode).collect(Collectors.toList());
                stockCacheService.setTodayZtCodeList(codeList);
            }
            if (dataMap.size() > 3) {
                String velocityContent = emailService.getVelocityContent(VelocityTemplateType.POOL, dataMap);
                weChatService.sendTextMessage(null, velocityContent);
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

                String velocityContent = emailService.getVelocityContent(VelocityTemplateType.POOL, dataMap);
                weChatService.sendTextMessage(null, velocityContent);
            }
            List<StockPoolInfo> poolDtList = extCrawlerService.findPoolByType(StockPoolType.DT, date);
            if (!CollectionUtils.isEmpty(poolDtList)) {
                poolDtList = filterStockList(poolDtList, DBStockType.SH_SZ, null);
                stockPoolHistoryService.savePoolHistory(poolDtList);
                // 获取所有的 股票编号和密码组装的信息
                dataMap.put("dtCodeList", poolDtList);
                dataMap.remove("ztCodeList");

                String velocityContent = emailService.getVelocityContent(VelocityTemplateType.POOL, dataMap);
                weChatService.sendTextMessage(null, velocityContent);
            }
            List<StockPoolInfo> poolCxList = extCrawlerService.findPoolByType(StockPoolType.CX, date);
            if (!CollectionUtils.isEmpty(poolCxList)) {
                poolCxList = filterStockList(poolCxList, DBStockType.SH_SZ, null);
                stockPoolHistoryService.savePoolHistory(poolCxList);
                // 获取所有的 股票编号和密码组装的信息
                dataMap.put("cxCodeList", poolCxList);

                dataMap.remove("ztCodeList");
                dataMap.remove("dtCodeList");

                String velocityContent = emailService.getVelocityContent(VelocityTemplateType.POOL, dataMap);
                weChatService.sendTextMessage(null, velocityContent);
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
    }

    @Override
    public OutputResult<List<DistVo>> listPoolType() {
        StockPoolType[] values = StockPoolType.values();

        List<DistVo> result = new ArrayList<>(values.length);

        for (StockPoolType stockPoolType : values) {
            //去掉昨日涨停的
            if (StockPoolType.YES_ZT.equals(stockPoolType)) {
                continue;
            }

            DistVo distVo = new DistVo();
            distVo.setCode(stockPoolType.getCode() + "");
            distVo.setName(stockPoolType.getDesc());
            result.add(distVo);
        }

        return OutputResult.buildSucc(result);


    }

    @Override
    public OutputResult<PageResponse<StockRelationVo>> listPool(StockPoolRo stockPoolRo) {
        // 1. 查询出对应的股票列表
        StockPoolQueryDto stockPoolQueryDto = stockPoolHistoryAssembler.roToDto(stockPoolRo);
        int daySize = dateHelper.betweenWorkDay(stockPoolRo.getStartDate(), stockPoolRo.getEndDate()).size();
        Map<String, List<String>> codeDateMap = stockPoolHistoryService.listStockCodeByCondition(stockPoolQueryDto);
        List<String> codeList = ListUtil.toList(codeDateMap.keySet());
        if (CollectionUtils.isEmpty(codeList)) {
            return OutputResult.buildSucc(PageResponse.emptyPageResponse());
        }
        // 2. 进行手动分页 出 股票列表
        List<String> filterCodeList = PageUtil.startPage(codeList, stockPoolRo.getPageNum(), stockPoolRo.getPageSize());
        // 3. 每个股票都去查询当前日期下信息.

        List<StockRelationVo> result = new ArrayList<>();
        Date startDate = DateUtil.parse(stockPoolRo.getStartDate());
        Date endDate = DateUtil.parse(stockPoolRo.getEndDate());
        for (String stockCode : filterCodeList) {
            // 查询出股票的名称
            StockRelationVo stockRelationVo = new StockRelationVo();
            stockRelationVo.setCode(stockCode);
            List<StockHistoryVo> stockHistoryByCodeAndRangeDateList = stockHistoryService.getStockHistoryByCodeAndRangeDate(stockCode, startDate, endDate);
            if (!CollectionUtils.isEmpty(stockHistoryByCodeAndRangeDateList)) {
                stockRelationVo.setName(stockHistoryByCodeAndRangeDateList.get(0).getName());
                List<String> dateList = codeDateMap.get(stockCode);
                // 判断一下 长度
                List<HistoryRelationVo> detailList = new ArrayList<>();
                if (stockHistoryByCodeAndRangeDateList.size() < daySize) {
                    detailList.add(new HistoryRelationVo());
                }
                stockHistoryByCodeAndRangeDateList.forEach(
                        n -> {
                            HistoryRelationVo historyRelationVo = new HistoryRelationVo();
                            int type;
                            if (n.getAmplitudeProportion().compareTo(SystemConst.DEFAULT_EMPTY) > 0) {
                                type = AmplitudeType.ROSE.getCode();
                            } else {
                                type = AmplitudeType.WANE.getCode();
                            }
                            String currDate = DateUtil.format(n.getCurrDate(), Const.SIMPLE_DATE_FORMAT);
                            historyRelationVo.setCurrDate(currDate);
                            if (dateList.contains(currDate)) {
                                historyRelationVo.setSign(1);
                            }
                            historyRelationVo.setType(type);
                            historyRelationVo.setAmplitudeProportion(BigDecimalUtil.toString(n.getAmplitudeProportion()));
                            historyRelationVo.setClosePrice(BigDecimalUtil.toString(n.getClosingPrice()));
                            detailList.add(historyRelationVo);
                        }
                );
                stockRelationVo.setDetailList(detailList);
            }
            result.add(stockRelationVo);
        }
        //4. 组装成分页，返回数据。
        return OutputResult.buildSucc(new PageResponse<>((long) codeList.size(), result));
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
