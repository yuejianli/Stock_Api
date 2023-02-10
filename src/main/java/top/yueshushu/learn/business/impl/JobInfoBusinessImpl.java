package top.yueshushu.learn.business.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ArrayUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import top.yueshushu.learn.business.*;
import top.yueshushu.learn.common.Const;
import top.yueshushu.learn.common.ResultCode;
import top.yueshushu.learn.domain.JobInfoDo;
import top.yueshushu.learn.domain.TradeRuleDbDo;
import top.yueshushu.learn.domainservice.TradeRuleDbDomainService;
import top.yueshushu.learn.entity.JobInfo;
import top.yueshushu.learn.enumtype.*;
import top.yueshushu.learn.helper.DateHelper;
import top.yueshushu.learn.message.dingtalk.DingTalkService;
import top.yueshushu.learn.message.weixin.service.WeChatService;
import top.yueshushu.learn.mode.ro.BuyRo;
import top.yueshushu.learn.mode.ro.DealRo;
import top.yueshushu.learn.mode.ro.JobInfoRo;
import top.yueshushu.learn.mode.vo.ConfigVo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.service.*;
import top.yueshushu.learn.service.cache.StockCacheService;
import top.yueshushu.learn.util.CronExpression;
import top.yueshushu.learn.util.MyDateUtil;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 用途描述
 *
 * @author yuejianli
 * @date 2022-06-02
 */
@Service
@Slf4j
public class JobInfoBusinessImpl implements JobInfoBusiness {
    public static final String JOB_PARAM_SPLIT = "\\,";
    @Resource
    private JobInfoService jobInfoService;

    @Resource
    private HolidayCalendarService holidayCalendarService;
    @Resource
    private TradePositionService tradePositionService;
    @Resource
    private StockSelectedService stockSelectedService;
    @Resource
    private UserService userService;
    @Resource
    private DealBusiness dealBusiness;
    @Resource
    private TradeStrategyService tradeStrategyService;
    @Resource
    private StockCrawlerService stockCrawlerService;
    @Resource
    private WeChatService weChatService;
    @Resource
    private StatBusinessImpl statBusiness;
    @Resource
    private TradeMoneyService tradeMoneyService;
    @Resource
    private TradePositionBusiness tradePositionBusiness;
    @Resource
    private StockCacheService stockCacheService;
    @Resource
    private DateHelper dateHelper;
    @Resource
    private StockBigDealService stockBigDealService;
    @Resource
    private TradeEntrustService tradeEntrustService;
    @Resource
    private TradeDealService tradeDealService;
    @Resource
    private ConfigService configService;
    @Resource
    private BuyNewStockBusiness buyNewStockBusiness;
    @Resource
    private AutoLoginBusiness autoLoginBusiness;
    @Resource
    private DingTalkService dingTalkService;
    @Resource
    private TradeRuleDbDomainService tradeRuleDbDomainService;
    @Resource
    private StockPoolBusiness stockPoolBusiness;
    @Resource
    private BKBusiness bkBusiness;
    @SuppressWarnings("all")
    @Resource(name = Const.ASYNC_SERVICE_EXECUTOR_BEAN_NAME)
    private AsyncTaskExecutor executor;

    private LocalTime STOCK_PRICE_START_TIME = LocalTime.parse("14:59:00");
    private LocalTime STOCK_PRICE_END_TIME = LocalTime.parse("15:01:00");

    private LocalTime STOCK_MORNING_START_TIME = LocalTime.parse("11:29:00");
    private LocalTime STOCK_MORNING_END_TIME = LocalTime.parse("11:31:00");

    @Override
    public OutputResult listJob(JobInfoRo jobInfoRo) {
        return jobInfoService.pageJob(jobInfoRo);
    }

    @Override
    public OutputResult changeStatus(Integer id, DataFlagType dataFlagType) {
        JobInfoDo jobInfoDo = jobInfoService.changeStatus(id, dataFlagType).getData();
        if (null == jobInfoDo) {
            return OutputResult.buildAlert(ResultCode.JOB_ID_NOT_EXIST);
        }
        stockCacheService.clearJobInfoCronCacheByCode(jobInfoDo.getCode());

        return OutputResult.buildSucc();
    }

    @Override
    public OutputResult execJob(JobInfoType jobInfoType, Integer triggerType) {
        String cron = stockCacheService.getJobInfoCronCacheByCode(jobInfoType.getCode());
        if (!StringUtils.hasText(cron)) {
            return OutputResult.buildAlert(ResultCode.JOB_ID_NOT_EXIST);
        }
        //是获取股票实时价格的任务，并且是自动运行。 非时间，不执行。
        if ((JobInfoType.STOCK_PRICE.equals(jobInfoType) || JobInfoType.DB_STOCK_TRADE.equals(jobInfoType)) && EntrustType.AUTO.getCode().equals(triggerType)) {
            if (!MyDateUtil.isDealTime() || !dateHelper.isWorkingDay(DateUtil.date())) {
                return OutputResult.buildSucc();
            }
        }
        //是获取股票保存价格的任务，并且是自动运行。 非时间，不执行。
        if (JobInfoType.STOCK_PRICE_SAVE.equals(jobInfoType) && EntrustType.AUTO.getCode().equals(triggerType)) {
            if (!MyDateUtil.isDealTime() || !dateHelper.isWorkingDay(DateUtil.date())) {
                return OutputResult.buildSucc();
            }
        }
        //股票历史
        if (JobInfoType.STOCK_HISTORY.equals(jobInfoType)) {
            if (!MyDateUtil.after15Hour()) {
                return OutputResult.buildFail(ResultCode.JOB_AFTER_3);
            }
        }
        //查询任务
        JobInfo jobInfo = jobInfoService.getByCode(jobInfoType);
        jobInfo.setTriggerLastTime(DateUtil.date().toLocalDateTime());
        try {
            String param = jobInfo.getParam();
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
                    if (isEndStockPriceTime()) {
                        // 进行休眠30 s, 使 查询股票的价格时间 在3点之后。
                        sleepTime(30000);
                    }
                    stockSelectedService.updateSelectedCodePrice(null);
                    break;
                }
                case STOCK_PRICE_SAVE: {
                    if (!StringUtils.hasText(param)) {
                        break;
                    }
                    if (isEndStockMorningTime() || isEndStockPriceTime()) {
                        // 进行休眠 1min , 使 保存股票的价格时间 在3点之后。
                        sleepTime(60000);
                    }
                    // 同步股票的编码
                    String[] stockCodeList = param.split(JOB_PARAM_SPLIT);
                    stockSelectedService.saveStockPrice(stockCodeList);
                    break;
                }
                case TRADE_ING_TO_REVOKE: {
                    List<Integer> userIdList = userService.listUserIds();
                    // 设置类型为虚拟
                    userIdList
                            .parallelStream()
                            .forEach(
                                    userId -> tradeStrategyService.revokeEntrustJob(userId, MockType.MOCK.getCode())
                            );
                    break;
                }
                case TRADE_POSITION_HISTORY: {
                    List<Integer> userIdList = userService.listUserIds();
                    //设置类型为虚拟
                    userIdList.parallelStream().forEach(
                            userId -> {
                                tradePositionService.savePositionHistory(
                                        userId, MockType.MOCK, DateUtil.date()
                                );
                                tradePositionService.savePositionHistory(
                                        userId, MockType.REAL, DateUtil.date()
                                );
                                // 对金额进行处理
                                tradeMoneyService.saveMoneyHistory(userId, MockType.MOCK, DateUtil.date());
                                tradeMoneyService.saveMoneyHistory(userId, MockType.REAL, DateUtil.date());
                            }
                    );
                    break;
                }
                case MOCK_DEAL: {
                    if (EntrustType.AUTO.getCode().equals(triggerType)) {
                        if (!MyDateUtil.isDealTime() || !dateHelper.isWorkingDay(DateUtil.date())) {
                            return OutputResult.buildSucc();
                        }
                    }
                    String userIdList = param;
                    for (String userId : userIdList.split(JOB_PARAM_SPLIT)) {
                        DealRo dealRo = new DealRo();
                        dealRo.setMockType(MockType.MOCK.getCode());
                        dealRo.setUserId(Integer.parseInt(userId));
                        dealRo.setEntrustType(EntrustType.AUTO.getCode());
                        dealBusiness.mockDealXxlJob(dealRo);
                    }
                    break;
                }
                case MOCK_ENTRUST: {
                    if (EntrustType.AUTO.getCode().equals(triggerType)) {
                        if (!MyDateUtil.isDealTime() || !dateHelper.isWorkingDay(DateUtil.date())) {
                            return OutputResult.buildSucc();
                        }
                    }
                    String userIdList = param;
                    for (String userId : userIdList.split(JOB_PARAM_SPLIT)) {
                        BuyRo buyRo = new BuyRo();
                        buyRo.setMockType(MockType.MOCK.getCode());
                        buyRo.setUserId(Integer.parseInt(userId));
                        tradeStrategyService.mockEntrustXxlJob(buyRo);
                    }
                    break;
                }
                case STOCK_UPDATE: {
                    stockCrawlerService.updateAllStock();
                    break;
                }
                case STOCK_FIVE_EMAIL: {
                    List<Integer> userIdList = userService.listUserIds();
                    // 设置类型为虚拟
                    userIdList
                            .forEach(
                                    userId -> {
                                        statBusiness.ten5ToMail(userId);
                                        try {
                                            TimeUnit.SECONDS.sleep(1);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                            );
                    break;
                }
                case CALL_PROFIT: {
                    List<Integer> userIdList = userService.listUserIds();
                    // 设置类型为虚拟
                    userIdList
                            .forEach(
                                    userId -> {
                                        tradePositionBusiness.callProfit(userId, MockType.MOCK);
                                    }
                            );
                    break;
                }
                case BIG_DEAL: {

                    String[] splitUserIdArr = param.split("\\,");

                    if (ArrayUtil.isEmpty(splitUserIdArr)) {
                        splitUserIdArr = new String[]{Const.DEFAULT_USER_ID + ""};
                    }
                    List<String> codeList = new ArrayList<>();

                    for (String userId : splitUserIdArr) {
                        List<String> tempCodeList = stockSelectedService.findCodeList(Integer.parseInt(userId));
                        if (!CollectionUtils.isEmpty(tempCodeList)) {
                            codeList.addAll(tempCodeList);
                        }
                    }
                    //去重
                    codeList = codeList.stream().distinct().collect(Collectors.toList());
                    stockBigDealService.syncBigDeal(codeList);

                    break;
                }
                case SYNC_EASY_MONEY: {
                    List<Integer> userIdList = userService.listUserIds();
                    // 设置类型为虚拟
                    userIdList
                            .forEach(
                                    userId -> {
                                        try {
                                            tradeMoneyService.syncEasyMoneyToDB(userId, MockType.REAL);
                                            sleepTime(200);
                                            tradePositionService.syncEasyMoneyToDB(userId, MockType.REAL);
                                            sleepTime(200);
                                            // 委托处理。
                                            tradeEntrustService.syncEasyMoneyToDB(userId, MockType.REAL);
                                            sleepTime(200);
                                            // 成交处理
                                            tradeDealService.syncEasyMoneyToDB(userId, MockType.REAL);
                                            sleepTime(200);
                                        } catch (Exception e) {

                                        }
                                    }
                            );
                    break;
                }
                case BUY_NEW_STOCK: {
                    List<Integer> userIdList = userService.listUserIds();
                    // 设置类型为虚拟
                    userIdList
                            .forEach(
                                    userId -> {
                                        try {
                                            ConfigVo config = configService.getConfig(userId, ConfigCodeType.BUY_NEW_STOCK);
                                            if (FunctionUseType.USE.getCode().equalsIgnoreCase(config.getCodeValue())) {
                                                // 启用
                                                buyNewStockBusiness.applyBuyNewStock(userId);
                                            }
                                        } catch (Exception e) {

                                        }
                                    }
                            );
                    break;
                }
                case AUTO_LOGIN: {
                    List<Integer> userIdList = userService.listUserIds();
                    // 设置类型为虚拟
                    userIdList
                            .forEach(
                                    userId -> {
                                        try {
                                            ConfigVo config = configService.getConfig(userId, ConfigCodeType.AUTO_LOGIN);
                                            if (FunctionUseType.USE.getCode().equalsIgnoreCase(config.getCodeValue())) {
                                                // 自动登录
                                                autoLoginBusiness.autoLogin(userId);
                                            }
                                        } catch (Exception e) {

                                        }
                                    }
                            );
                    break;
                }
                case STOCK_BK: {
                    bkBusiness.syncBkAndMoney();
                    // 获取所有的自选股票
                    List<String> codeList = stockSelectedService.findCodeList(null);
                    if (CollectionUtils.isEmpty(codeList)) {
                        break;
                    }
                    for (String code : codeList) {
                        bkBusiness.syncRelationCode(code);
                    }

                    break;
                }
                case DB_STOCK_TRADE: {
                    // 1. 查询出所有支持打版的用户编号。
                    List<Integer> userIdList = configService.listEnableUserId(ConfigCodeType.DB_ENABLE);
                    Integer mockType = MockType.MOCK.getCode();
                    // 2. 查询出对应的类型信息
                    if (!CollectionUtils.isEmpty(userIdList)) {
                        for (Integer userId : userIdList) {
                            Long todayBuyDBSurplusNum = stockCacheService.getTodayBuyDBSurplusNum(userId, mockType, null);
                            if (todayBuyDBSurplusNum <= 0) {
                                continue;
                            }
                            TradeRuleDbDo tradeRuleDbDo = tradeRuleDbDomainService.getByQuery(userId, mockType);

                            if (null == tradeRuleDbDo) {
                                continue;
                            }
                            // 获取类型
                            DBStockType stockType = DBStockType.getStockType(tradeRuleDbDo.getCodeType());
                            if (stockType != null) {
                                BuyRo buyRo = new BuyRo();
                                buyRo.setUserId(userId);
                                buyRo.setMockType(mockType);
                                tradeStrategyService.mockDbEntrustXxlJob(buyRo, stockType);
                            }
                        }
                    }

                    break;
                }
                case STOCK_POOL: {
                    stockPoolBusiness.handlerPool(DateUtil.date());
                    break;
                }
                default: {
                    break;
                }

            }
            jobInfo.setTriggerLastResult(1);
            jobInfo.setTriggerLastErrorMessage(null);
        } catch (Exception e) {
            jobInfo.setTriggerLastResult(0);
            jobInfo.setTriggerLastErrorMessage(e.getMessage());
            //执行任务失败，会发送消息到当前的用户.
            String errorWxMessage = MessageFormat.format("执行任务 {0} 失败，失败原因是:{1}",
                    jobInfoType.getDesc(), e);
            weChatService.sendTextMessage(Const.DEFAULT_USER_ID, errorWxMessage);
            //  dingTalkService.sendTextMessage(Const.DEFAULT_USER_ID, errorWxMessage);
            log.error("执行任务失败{}", e);
        } finally {
            //设置下次触发的时间
            jobInfo.setTriggerType(triggerType);
            jobInfo.setTriggerLastTime(LocalDateTime.now());
            try {
                CronExpression cronExpression = new CronExpression(jobInfo.getCron());
                Date date = cronExpression.getNextValidTimeAfter(new Date());
                Instant instant = date.toInstant();
                ZoneId zoneId = ZoneId.systemDefault();
                LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
                jobInfo.setTriggerNextTime(localDateTime);
            } catch (Exception e) {
                log.error("获取下一次触发时间失败", e);
            }
            jobInfoService.updateInfoById(jobInfo);
        }
        if (StringUtils.isEmpty(jobInfo.getTriggerLastErrorMessage())){
            return OutputResult.buildSucc();
        }else{
            return OutputResult.buildFail(jobInfo.getTriggerLastErrorMessage());
        }
    }

    @Override
    public OutputResult deleteById(Integer id) {
        //立即执行
        JobInfo job = jobInfoService.getById(id);
        if (null == job) {
            return OutputResult.buildAlert(ResultCode.JOB_ID_NOT_EXIST);
        }
        jobInfoService.deleteById(id);
        stockCacheService.clearJobInfoCronCacheByCode(job.getCode());
        return OutputResult.buildSucc();
    }

    @Override
    public OutputResult handlerById(Integer id) {
        //立即执行
        JobInfo job = jobInfoService.getById(id);

        //是获取股票实时价格的任务，并且是自动运行。 非时间，不执行。
        if (JobInfoType.STOCK_HISTORY.equals(JobInfoType.getJobInfoType(job.getCode()))) {
            if (!MyDateUtil.after15Hour()) {
                return OutputResult.buildFail(ResultCode.JOB_AFTER_3);
            }
        }
        executor.submit(
                () -> {
                    execJob(JobInfoType.getJobInfoType(job.getCode()), EntrustType.HANDLER.getCode());
                }
        );
        return OutputResult.buildSucc();
    }

    @Override
    public OutputResult changeCron(Integer id, String cron) {

        JobInfo job = jobInfoService.getById(id);
        if (null == job) {
            return OutputResult.buildAlert(ResultCode.JOB_ID_NOT_EXIST);
        }
        // 更新 cron 信息
        JobInfo jobInfo = new JobInfo();
        jobInfo.setId(id);
        jobInfo.setCron(cron);
        jobInfoService.updateInfoById(jobInfo);

        // 清理缓存信息
        stockCacheService.clearJobInfoCronCacheByCode(job.getCode());
        return OutputResult.buildSucc();
    }

    private void sleepTime(int milliSeconds) {
        try {
            TimeUnit.MILLISECONDS.sleep(milliSeconds);
        } catch (Exception e) {

        }
    }

    /**
     * 是否是股票最后的特殊时间，
     * 即 14:59 到  15:01 期间
     */
    private boolean isEndStockPriceTime() {
        // 进行延迟， 如果时间在 14:59 之后，则睡眠 1分钟。
        LocalTime now = LocalTime.now();
        if (now.isAfter(STOCK_PRICE_START_TIME) && now.isBefore(STOCK_PRICE_END_TIME)) {
            return true;
        }
        return false;
    }

    /**
     * 是否是股票最后的特殊时间，
     * 即 11:29 到  11:31 期间
     */
    private boolean isEndStockMorningTime() {
        // 进行延迟， 如果时间在 14:59 之后，则睡眠 1分钟。
        LocalTime now = LocalTime.now();
        if (now.isAfter(STOCK_MORNING_START_TIME) && now.isBefore(STOCK_MORNING_END_TIME)) {
            return true;
        }
        return false;
    }
}
