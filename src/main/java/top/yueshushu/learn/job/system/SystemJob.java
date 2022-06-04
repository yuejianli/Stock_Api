package top.yueshushu.learn.job.system;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.yueshushu.learn.business.JobInfoBusiness;
import top.yueshushu.learn.enumtype.EntrustType;
import top.yueshushu.learn.enumtype.JobInfoType;
import top.yueshushu.learn.helper.DateHelper;
import top.yueshushu.learn.service.*;

import javax.annotation.Resource;

/**
 * 系统定时任务
 * @author Yue Jianli
 * @date 2022-05-31
 */
@Component
@Slf4j
public class SystemJob {
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
    private TradeStrategyService tradeStrategyService;

    @Resource
    private JobInfoBusiness jobInfoBusiness;

    @Value("${xxlJobTime}")
    boolean xxlJobTime;

    @Scheduled(cron = "1 1 0 1 1 ?")
    public void holiday() {
        log.info(">>>同步 {}年的假期数据", DateUtil.thisYear());
        jobInfoBusiness.execJob(JobInfoType.HOLIDAY, EntrustType.AUTO.getCode());
    }

    @Scheduled(cron = "1 30 20 ? * 2,3,4,5,6")
    public void positionUseAmount() {
        if (!dateHelper.isWorkingDay(DateUtil.date())) {
            log.info("当前时间{}不是交易日，不需要同步", DateUtil.now());
            return;
        }
        log.info(">>>同步股票可用数量");
        jobInfoBusiness.execJob(JobInfoType.POSITION_USE_AMOUNT, EntrustType.AUTO.getCode());
    }

    @Scheduled(cron = "1 10 8 * * ?")
    public void yesPrice() {
        if (!dateHelper.isWorkingDay(DateUtil.date())){
            log.info("当前时间{}不是交易日，不需要同步",DateUtil.now());
            return ;
        }
        log.info(">>> {} 时,获取股票的收盘价信息", DateUtil.now());
        jobInfoBusiness.execJob(JobInfoType.YESTERDAY_PRICE, EntrustType.AUTO.getCode());
    }
    @Scheduled(cron = "1 10 18 * * ?")
    public void stockHistory() {
        if (!dateHelper.isWorkingDay(DateUtil.date())){
            log.info("当前时间{}不是交易日，不需要同步",DateUtil.now());
            return ;
        }
        log.info(">>> {} 时,更新目前自选表里面的历史表记录信息", DateUtil.now());
        jobInfoBusiness.execJob(JobInfoType.STOCK_HISTORY, EntrustType.AUTO.getCode());
    }

    @Scheduled(cron = "1/10 * 9,10,11,13,14 ? * 2,3,4,5,6")
    public void stockPrice() {
        //获取当前的股票信息。取第一个值.
        if (xxlJobTime) {
            if (!dateHelper.isTradeTime(DateUtil.date())) {
                return;
            }
        }
        jobInfoBusiness.execJob(JobInfoType.STOCK_PRICE, EntrustType.AUTO.getCode());
    }

    /**
     * 取消掉每天未成交的委托单信息
     */
    @Scheduled(cron = "1 10 20 ? * 2,3,4,5,6")
    public void tradeIngToRevoke() {
        if (!dateHelper.isWorkingDay(DateUtil.date())) {
            log.info("当前时间{}不是交易日，不需要同步", DateUtil.now());
            return;
        }
        log.info(">>> {} 时,取消掉委托单信息", DateUtil.now());
        jobInfoBusiness.execJob(JobInfoType.TRADE_ING_TO_REVOKE, EntrustType.AUTO.getCode());
    }

    @Scheduled(cron = "1 20 20 ? * 2,3,4,5,6")
    public void tradePositionHistory() {
        if (!dateHelper.isWorkingDay(DateUtil.date())) {
            log.info("当前时间{}不是交易日，不需要同步", DateUtil.now());
            return;
        }
        log.info(">>> {} 时,保存股票的当前持仓信息", DateUtil.now());
        jobInfoBusiness.execJob(JobInfoType.TRADE_POSITION_HISTORY, EntrustType.AUTO.getCode());
    }
    @Scheduled(cron = "1 20 8 ? * 2,3,4,5,6")
    public void stockUpdate() {
        if (!dateHelper.isWorkingDay(DateUtil.date())) {
            log.info("当前时间{}不是交易日，不需要同步", DateUtil.now());
            return;
        }
        log.info(">>> {} 时,更新股票信息", DateUtil.now());
        jobInfoBusiness.execJob(JobInfoType.STOCK_UPDATE, EntrustType.AUTO.getCode());
    }
}
