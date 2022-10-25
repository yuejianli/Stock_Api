package top.yueshushu.learn.job.user;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import top.yueshushu.learn.business.JobInfoBusiness;
import top.yueshushu.learn.enumtype.EntrustType;
import top.yueshushu.learn.enumtype.JobInfoType;
import top.yueshushu.learn.helper.DateHelper;

import javax.annotation.Resource;

/**
 * 用户1的定时任务
 * @author Yue Jianli
 * @date 2022-05-31
 */
@Component
@Slf4j
@Configuration
@ConditionalOnProperty(value = {"xxlJobTime"}, matchIfMissing = true)
public class UserId1Job {
    @Value("${xxlJobTime}")
    boolean xxlJobTime;
    @Resource
    private DateHelper dateHelper;
    @Resource
    private JobInfoBusiness jobInfoBusiness;

    // @Scheduled(cron = "2/5 * 9,10,11,13,14,15 ? * 1-5")
    public void mockDeal(){
        if (xxlJobTime){
            if (!dateHelper.isTradeTime(DateUtil.date())) {
                return ;
            }
        }
        jobInfoBusiness.execJob(JobInfoType.MOCK_DEAL, EntrustType.AUTO.getCode());
    }

    // @Scheduled(cron = "2/5 * 9,10,11,13,14,15 ? * 1-5")
    public void mockEntrust() {
        if (xxlJobTime) {
            if (!dateHelper.isTradeTime(DateUtil.date())) {
                return;
            }
        }
        jobInfoBusiness.execJob(JobInfoType.MOCK_ENTRUST, EntrustType.AUTO.getCode());
    }
}
