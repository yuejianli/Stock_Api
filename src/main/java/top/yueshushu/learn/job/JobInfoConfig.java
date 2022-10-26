package top.yueshushu.learn.job;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.util.StringUtils;
import top.yueshushu.learn.business.JobInfoBusiness;
import top.yueshushu.learn.enumtype.EntrustType;
import top.yueshushu.learn.enumtype.JobInfoType;
import top.yueshushu.learn.service.cache.StockCacheService;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 用途描述
 *
 * @author yuejianli
 * @date 2022-10-24
 */
@Configuration
@EnableScheduling
@Slf4j
public class JobInfoConfig implements SchedulingConfigurer {
    @Resource
    private JobInfoBusiness jobInfoBusiness;

    @Resource
    private StockCacheService stockCacheService;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        // 获取每一个定时任务列表.
        JobInfoType[] values = JobInfoType.values();
        for (JobInfoType jobInfoType : values) {
            taskRegistrar.addTriggerTask(
                    () -> {
                        jobInfoBusiness.execJob(jobInfoType, EntrustType.AUTO.getCode());
                    },
                    new Trigger() {
                        @Override
                        public Date nextExecutionTime(TriggerContext triggerContext) {
                            String cron = stockCacheService.getJobInfoCronCacheByCode(jobInfoType.getCode());
                            if (!StringUtils.hasText(cron)) {
                                return null;
                            }
                            CronTrigger cronTrigger = new CronTrigger(cron);
                            // 延迟1分钟执行
                            return DateUtil.offsetMinute(cronTrigger.nextExecutionTime(triggerContext), 1);
                        }
                    }
            );
        }

    }
}
