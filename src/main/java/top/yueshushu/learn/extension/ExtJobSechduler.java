package top.yueshushu.learn.extension;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;
import top.yueshushu.learn.domain.ext.ExtCustomerDo;
import top.yueshushu.learn.domainservice.ext.ExtCustomerDomainService;
import top.yueshushu.learn.extension.business.ExtJobBusiness;

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
	private ExtJobBusiness extJobBusiness;
	@Resource
	private ExtCustomerDomainService extCustomerDomainService;
	
	/**
	 * 每天早上7点半，发送天气信息
	 */
	@Scheduled(cron = "1 30 7 * * ?")
	public void morning() {
		try {
			
			// 1. 查询所有的用户
			List<ExtCustomerDo> extCustomerDoList = extCustomerDomainService.list();
			log.info(">>>>共查询出用户 {}", extCustomerDoList.size());
			if (CollectionUtils.isEmpty(extCustomerDoList)) {
				return;
			}
			extCustomerDoList.parallelStream().forEach(
					n -> {
						try {
							extJobBusiness.morning(n);
						} catch (Exception e) {
							log.error(">>>早安，发送用户 {} 失败", n.getUserAccount(), e);
						}
					}
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 每天晚上10点半，发送信息.
	 */
	@Scheduled(cron = "1 20 22 * * ?")
	public void night() {
		
		try {
			// 1. 查询所有的用户
			List<ExtCustomerDo> extCustomerDoList = extCustomerDomainService.list();
			log.info(">>>>共查询出用户 {}", extCustomerDoList.size());
			if (CollectionUtils.isEmpty(extCustomerDoList)) {
				return;
			}
			extCustomerDoList.parallelStream().forEach(
					n -> {
						try {
							extJobBusiness.night(n);
						} catch (Exception e) {
							log.error(">>>晚安，发送用户 {} 失败", n.getUserAccount(), e);
						}
					}
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
