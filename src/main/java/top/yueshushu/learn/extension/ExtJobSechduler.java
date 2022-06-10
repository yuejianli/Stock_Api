package top.yueshushu.learn.extension;

import com.alibaba.fastjson.JSON;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;
import top.yueshushu.learn.domain.ext.ExtCustomerDo;
import top.yueshushu.learn.domainservice.ext.ExtCustomerDomainService;
import top.yueshushu.learn.extension.model.gaodeweather.Forecast;
import top.yueshushu.learn.extension.model.gaodeweather.ForecastCasts;
import top.yueshushu.learn.extension.model.shanbeici.TranslateResponse;
import top.yueshushu.learn.extension.model.shici.PoemResponse;
import top.yueshushu.learn.extension.model.tianxing.TianXingInfo;
import top.yueshushu.learn.extension.model.tianxing.TianXingResponse;
import top.yueshushu.learn.message.weixin.service.WeChatService;

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
	private ExtJobService extJobService;
	@Resource
	private WeChatService weChatService;
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
							sendSingleMoring(n);
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
	 * 给每一个用户 发送早安的程序信息
	 */
	private void sendSingleMoring(ExtCustomerDo extCustomerDo) {
		String line = System.lineSeparator();
		//1. 晚安信息
		StringBuffer stringBuffer = new StringBuffer();
		
		TianXingResponse<TianXingInfo> zaoAn = extJobService.getZaoAn();
		TianXingInfo zaoAnInfo = convertContent(zaoAn);
		stringBuffer.append(extCustomerDo.getName() + "," + zaoAnInfo.getContent());
		stringBuffer.append(line);
		//获取天气
		Forecast forecast = extJobService.getWeather(extCustomerDo.getCity()).getForecasts().get(0);
		// 今天
		stringBuffer.append("你所在的城市: " + forecast.getCity()).append(line);
		stringBuffer.append(line);
		ForecastCasts today = forecast.getCasts().get(0);
		stringBuffer.append("今天是 " + today.getDate() + ",星期 " + today.getWeek()).append(line);
		stringBuffer.append("白天天气 " + today.getDayweather() + ",晚上天气 :" + today.getNightweather()).append(line);
		stringBuffer.append("白天温度 " + today.getDaytemp() + ",晚上温度: " + today.getNighttemp()).append(line);
		// 明天
		ForecastCasts tomorrow = forecast.getCasts().get(1);
		stringBuffer.append("明天是 " + tomorrow.getDate() + ",星期" + tomorrow.getWeek()).append(line);
		stringBuffer.append("白天天气 " + tomorrow.getDayweather() + ",晚上天气:" + tomorrow.getNightweather()).append(line);
		stringBuffer.append("白天温度 " + tomorrow.getDaytemp() + ",晚上温度:" + tomorrow.getNighttemp()).append(line);
		stringBuffer.append(line);
		TranslateResponse translate = extJobService.getTranslate();
		stringBuffer.append("早上每日一句： ").append(line).append("英文:" + translate.getContent()).append(line).append("对应中文:" + translate.getTranslation()).append(line);
		stringBuffer.append(line);
		PoemResponse poemResponse = extJobService.getPoem();
		stringBuffer.append("早上每日优美诗句:  ").append(line).append("内容  " + poemResponse.getContent()).
				append(line).append("出处:  " + poemResponse.getOrigin()).append(line).append("作者:  " + poemResponse.getAuthor()).append(line);
		
		stringBuffer.append(line);
		TianXingResponse<TianXingInfo> caiHongPi = extJobService.getCaiHongPi();
		TianXingInfo caiHongPiInfo = convertContent(caiHongPi);
		stringBuffer.append("祝福:  " + caiHongPiInfo.getContent());
		
		
		weChatService.sendTextMessage(extCustomerDo.getUserId(), stringBuffer.toString());
	}
	
	/**
	 * 将信息对象转换
	 *
	 * @param tianXingInfoTianXingResponse 对象转换
	 */
	private TianXingInfo convertContent(TianXingResponse<TianXingInfo> tianXingInfoTianXingResponse) {
		return JSON.parseObject(JSON.toJSONString(tianXingInfoTianXingResponse.getNewslist().get(0)), TianXingInfo.class);
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
							sendSingleNight(n);
						} catch (Exception e) {
							log.error(">>>晚安，发送用户 {} 失败", n.getUserAccount(), e);
						}
					}
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 发送晚安数据
	 */
	private void sendSingleNight(ExtCustomerDo extCustomerDo) {
		
		//1. 晚安信息
		String line = System.lineSeparator();
		StringBuffer stringBuffer = new StringBuffer();
		TianXingResponse<TianXingInfo> wanAn = extJobService.getWanAn();
		TianXingInfo wanAnInfo = convertContent(wanAn);
		stringBuffer.append(extCustomerDo.getName() + "," + wanAnInfo.getContent());
		stringBuffer.append(line);
		TranslateResponse translate = extJobService.getTranslate();
		stringBuffer.append(line);
		stringBuffer.append("晚上每日一句： ").append(line).append("英文:" + translate.getContent()).append(line).append("对应中文:" + translate.getTranslation()).append(line);
		stringBuffer.append(line);
		PoemResponse poemResponse = extJobService.getPoem();
		stringBuffer.append("晚上每日优美诗句:   ").append(line).append("内容   " + poemResponse.getContent()).
				append(line).append("出处:  " + poemResponse.getOrigin()).append(line).append("作者:  " + poemResponse.getAuthor()).append(line);
		//发送诗句
		stringBuffer.append(line);
		TianXingResponse<TianXingInfo> caiHongPi = extJobService.getCaiHongPi();
		TianXingInfo caiHongPiInfo = convertContent(caiHongPi);
		stringBuffer.append("祝福:  " + caiHongPiInfo.getContent());
		
		weChatService.sendTextMessage(extCustomerDo.getUserId(), stringBuffer.toString());
	}
}
