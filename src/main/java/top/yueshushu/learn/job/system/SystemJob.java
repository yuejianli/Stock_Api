package top.yueshushu.learn.job.system;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.yueshushu.learn.enumtype.MockType;
import top.yueshushu.learn.helper.DateHelper;
import top.yueshushu.learn.service.*;

import javax.annotation.Resource;
import java.util.List;

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

    @Value("${xxlJobTime}")
    boolean xxlJobTime;

    @Scheduled(cron = "1 1 0 1 1 ?")
    public void holiday() {
        //当前的年
        int year = DateUtil.thisYear();
        log.info(">>>同步 {}年的假期数据", year);
        holidayCalendarService.syncYear(year);
    }

    @Scheduled(cron = "1 30 20 ? * 2,3,4,5,6")
    public void positionUseAmount() {
        if (!dateHelper.isWorkingDay(DateUtil.date())) {
            log.info("当前时间{}不是交易日，不需要同步", DateUtil.now());
            return;
        }
        log.info(">>>同步股票可用数量");
        tradePositionService.syncUseAmountByXxlJob();
    }

    @Scheduled(cron = "1 10 8 * * ?")
    public void yesPrice() {
        if (!dateHelper.isWorkingDay(DateUtil.date())){
            log.info("当前时间{}不是交易日，不需要同步",DateUtil.now());
            return ;
        }
        log.info(">>> {} 时,获取股票的收盘价信息", DateUtil.now());
        stockSelectedService.cacheClosePrice();
    }
    @Scheduled(cron = "1 10 18 * * ?")
    public void stockHistory() {
        if (!dateHelper.isWorkingDay(DateUtil.date())){
            log.info("当前时间{}不是交易日，不需要同步",DateUtil.now());
            return ;
        }
        log.info(">>> {} 时,更新目前自选表里面的历史表记录信息", DateUtil.now());
        stockSelectedService.syncDayHistory();
    }

    @Scheduled(cron = "1/10 * 9,10,11,13,14 ? * 2,3,4,5,6")
    public void stockPrice() {
        //获取当前的股票信息。取第一个值.
        if (xxlJobTime) {
            if (!dateHelper.isTradeTime(DateUtil.date())) {
                return;
            }
        }
        stockSelectedService.updateSelectedCodePrice(null);
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
        List<Integer> userIdList = userService.listUserId();
        // 设置类型为虚拟
        userIdList
                .parallelStream()
                .forEach(
                        userId -> tradeStrategyService.revokeEntrustJob(userId, MockType.MOCK.getCode())
                );
    }

    @Scheduled(cron = "1 20 20 ? * 2,3,4,5,6")
    public void tradePositionHistory() {
        if (!dateHelper.isWorkingDay(DateUtil.date())) {
            log.info("当前时间{}不是交易日，不需要同步", DateUtil.now());
            return;
        }
        log.info(">>> {} 时,保存股票的当前持仓信息", DateUtil.now());
        List<Integer> userIdList = userService.listUserId();
        //设置类型为虚拟
        userIdList.parallelStream().forEach(
                userId -> {
                    tradePositionService.savePositionHistory(
                            userId, MockType.MOCK
                    );
                    tradePositionService.savePositionHistory(
                            userId,MockType.REAL
                    );
                }
        );
    }
}
