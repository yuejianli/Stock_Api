package top.yueshushu.learn.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import top.yueshushu.learn.business.JobInfoBusiness;
import top.yueshushu.learn.enumtype.EntrustType;
import top.yueshushu.learn.enumtype.JobInfoType;
import top.yueshushu.learn.service.cache.StockCacheService;

import javax.annotation.Resource;

/**
 * 定时任务处理
 *
 * @author yuejianli
 * @date 2022-10-24
 */
@Configuration
@EnableScheduling
@ConditionalOnProperty(value = {"enableJob"}, matchIfMissing = true)
@Component
@Slf4j
public class JobInfoConfig implements SchedulingConfigurer {
    @Resource
    private JobInfoBusiness jobInfoBusiness;

    @Resource
    private StockCacheService stockCacheService;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        log.info(">>>>>  启动定时任务配置");
        // 获取每一个定时任务列表.
        JobInfoType[] values = JobInfoType.values();
        for (JobInfoType jobInfoType : values) {
            taskRegistrar.addTriggerTask(
                    () -> {
                        jobInfoBusiness.execJob(jobInfoType, EntrustType.AUTO.getCode());
                    },
                    triggerContext -> {
                        String cron = stockCacheService.getJobInfoCronCacheByCode(jobInfoType.getCode());
                        if (!StringUtils.hasText(cron)) {
                            return null;
                        }
                        CronTrigger cronTrigger = new CronTrigger(cron);
                        return cronTrigger.nextExecutionTime(triggerContext);
                    }
            );
        }

    }
}
