package top.yueshushu.learn.business.impl;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.yueshushu.learn.business.DealBusiness;
import top.yueshushu.learn.business.JobInfoBusiness;
import top.yueshushu.learn.entity.JobInfo;
import top.yueshushu.learn.enumtype.DataFlagType;
import top.yueshushu.learn.enumtype.EntrustType;
import top.yueshushu.learn.enumtype.JobInfoType;
import top.yueshushu.learn.enumtype.MockType;
import top.yueshushu.learn.helper.DateHelper;
import top.yueshushu.learn.mode.ro.BuyRo;
import top.yueshushu.learn.mode.ro.DealRo;
import top.yueshushu.learn.mode.ro.JobInfoRo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.service.*;
import top.yueshushu.learn.util.CronExpression;

import javax.annotation.Resource;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * 用途描述
 *
 * @author yuejianli
 * @date 2022-06-02
 */
@Service
@Slf4j
public class JobInfoBusinessImpl implements JobInfoBusiness {
    @Resource
    private JobInfoService jobInfoService;

    @Resource
    private HolidayCalendarService holidayCalendarService;
    @Resource
    private TradePositionService tradePositionService;
    @Resource
    private DateHelper dateHelper;
    @Resource
    private StockSelectedService stockSelectedService;
    @Resource
    private UserService userService;
    @Resource
    private DealBusiness dealBusiness;
    @Resource
    private TradeStrategyService tradeStrategyService;


    @Override
    public OutputResult listJob(JobInfoRo jobInfoRo) {
        return jobInfoService.pageJob(jobInfoRo);
    }

    @Override
    public OutputResult changeStatus(Integer id, DataFlagType dataFlagType) {
        return jobInfoService.changeStatus(id, dataFlagType);
    }

    @Override
    public void execJob(JobInfoType jobInfoType, Integer triggerType) {
        if (jobInfoType == null) {
            return;
        }
        //查询任务
        JobInfo jobInfo = jobInfoService.getByCode(jobInfoType);
        if (jobInfo == null) {
            log.info("当前任务 {} 已经被删除", jobInfoType.getDesc());
            return;
        }
        if (!DataFlagType.NORMAL.getCode().equals(jobInfo.getTriggerStatus())) {
            log.info(">>当前任务 {}是禁用状态，不执行", jobInfoType.getDesc());
            return;
        }
        try {
            switch (jobInfoType) {
                case HOLIDAY: {
                    //当前的年
                    int year = DateUtil.thisYear();
                    holidayCalendarService.syncYear(year);
                    break;
                }
                case POSITION_USE_AMOUNT: {
                    tradePositionService.syncUseAmountByXxlJob();
                    break;
                }
                case YESTERDAY_PRICE: {
                    stockSelectedService.cacheClosePrice();
                    break;
                }
                case STOCK_HISTORY: {
                    stockSelectedService.syncDayHistory();
                    break;
                }
                case STOCK_PRICE: {
                    stockSelectedService.updateSelectedCodePrice(null);
                    break;
                }
                case TRADE_ING_TO_REVOKE: {
                    List<Integer> userIdList = userService.listUserId();
                    // 设置类型为虚拟
                    userIdList
                            .parallelStream()
                            .forEach(
                                    userId -> tradeStrategyService.revokeEntrustJob(userId, MockType.MOCK.getCode())
                            );
                    break;
                }
                case TRADE_POSITION_HISTORY: {
                    List<Integer> userIdList = userService.listUserId();
                    //设置类型为虚拟
                    userIdList.parallelStream().forEach(
                            userId -> {
                                tradePositionService.savePositionHistory(
                                        userId, MockType.MOCK
                                );
                                tradePositionService.savePositionHistory(
                                        userId, MockType.REAL
                                );
                            }
                    );
                    break;
                }
                case MOCK_DEAL: {
                    String userIdList = jobInfo.getParam();
                    for (String userId : userIdList.split(",")) {
                        log.info(">>>扫描当前的用户id 为{}", userId);
                        DealRo dealRo = new DealRo();
                        dealRo.setMockType(MockType.MOCK.getCode());
                        dealRo.setUserId(Integer.parseInt(userId));
                        dealRo.setEntrustType(EntrustType.AUTO.getCode());
                        dealBusiness.mockDealXxlJob(dealRo);
                    }
                    break;
                }
                case MOCK_ENTRUST: {
                    String userIdList = jobInfo.getParam();
                    for (String userId : userIdList.split(",")) {
                        log.info(">>>扫描当前的用户id 为{}", userId);
                        BuyRo buyRo = new BuyRo();
                        buyRo.setMockType(MockType.MOCK.getCode());
                        buyRo.setUserId(Integer.parseInt(userId));
                        tradeStrategyService.mockEntrustXxlJob(buyRo);
                    }
                    break;
                }
                default: {
                    break;
                }

            }
            jobInfo.setTriggerLastResult(1);
        } catch (Exception e) {
            jobInfo.setTriggerLastResult(0);
            jobInfo.setTriggerLastErrorMessage(e.getMessage());
            log.info("执行任务失败{}", e);

        }
        //设置下次触发的时间
        jobInfo.setTriggerType(triggerType);
        jobInfo.setTriggerLastTime(LocalDateTime.now());
        try {
            CronExpression cronExpression = new CronExpression(jobInfo.getCron());
            Date date = cronExpression.getNextInvalidTimeAfter(new Date());
            Instant instant = date.toInstant();
            ZoneId zoneId = ZoneId.systemDefault();
            LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
            jobInfo.setTriggerNextTime(localDateTime);
        } catch (Exception e) {
            log.error("获取下一次触发时间失败", e);
        }
        jobInfoService.updateInfoById(jobInfo);
    }

    @Override
    public OutputResult deleteById(Integer id) {
        return jobInfoService.deleteById(id);
    }

    @Override
    public OutputResult handlerById(Integer id) {
        //立即执行
        JobInfo job = jobInfoService.getById(id);
        execJob(JobInfoType.getJobInfoType(job.getCode()), EntrustType.HANDLER.getCode());
        return OutputResult.buildSucc();
    }
}
