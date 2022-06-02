package top.yueshushu.learn.job.user;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.yueshushu.learn.business.DealBusiness;
import top.yueshushu.learn.enumtype.EntrustType;
import top.yueshushu.learn.enumtype.MockType;
import top.yueshushu.learn.helper.DateHelper;
import top.yueshushu.learn.mode.ro.BuyRo;
import top.yueshushu.learn.mode.ro.DealRo;
import top.yueshushu.learn.service.TradeStrategyService;

import javax.annotation.Resource;

/**
 * 用户1的定时任务
 * @author Yue Jianli
 * @date 2022-05-31
 */
@Component
@Slf4j
public class UserId1Job {
    @Value("${xxlJobTime}")
    boolean xxlJobTime;
    @Resource
    private DealBusiness dealBusiness;
    @Resource
    private DateHelper dateHelper;

    @Resource
    private TradeStrategyService tradeStrategyService;

    @Scheduled(cron = "2/5 * 9,10,11,13,14,15 ? * 2,3,4,5,6")
    public void mockDeal(){
        if (xxlJobTime){
            if (!dateHelper.isTradeTime(DateUtil.date())) {
                return ;
            }
        }
        String userId = "1";
        log.info(">>>扫描当前的用户id 为{}", userId);
        DealRo dealRo = new DealRo();
        dealRo.setMockType(MockType.MOCK.getCode());
        dealRo.setUserId(Integer.parseInt(userId));
        dealRo.setEntrustType(EntrustType.AUTO.getCode());
        dealBusiness.mockDealXxlJob(dealRo);
    }

    @Scheduled(cron = "2/5 * 9,10,11,13,14,15 ? * 2,3,4,5,6")
    public void mockEntrust() {
        if (xxlJobTime) {
            if (!dateHelper.isTradeTime(DateUtil.date())) {
                return;
            }
        }
        String userId = "1";
        log.info(">>>扫描当前的用户id 为{}", userId);
        BuyRo buyRo = new BuyRo();
        buyRo.setMockType(MockType.MOCK.getCode());
        buyRo.setUserId(Integer.parseInt(userId));
        tradeStrategyService.mockEntrustXxlJob(buyRo);
    }
}
