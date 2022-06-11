package top.yueshushu.learn.extension.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.yueshushu.learn.enumtype.EntrustType;
import top.yueshushu.learn.enumtype.ExtJobInfoType;
import top.yueshushu.learn.extension.business.ExtJobInfoBusiness;
import top.yueshushu.learn.extension.domainservice.ExtCustomerDomainService;

import javax.annotation.Resource;

/**
 * 系统扩展任务
 *
 * @author yuejianli
 * @date 2022-06-07
 */
@Component
@Slf4j
public class ExtJobSechduler {
    @Resource
    private ExtJobInfoBusiness extJobInfoBusiness;
    @Resource
    private ExtCustomerDomainService extCustomerDomainService;
	
	/**
	 * 每天早上7点半，发送天气信息
	 */
	@Scheduled(cron = "1 30 7 * * ?")
	public void morning() {
        extJobInfoBusiness.execJob(ExtJobInfoType.MORNING, EntrustType.AUTO.getCode());
    }
	/**
	 * 每天晚上10点半，发送信息.
	 */
	@Scheduled(cron = "1 20 22 * * ?")
	public void night() {
        extJobInfoBusiness.execJob(ExtJobInfoType.NIGHT, EntrustType.AUTO.getCode());
    }
}
