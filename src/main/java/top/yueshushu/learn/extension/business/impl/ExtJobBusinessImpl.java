package top.yueshushu.learn.extension.business.impl;

import com.alibaba.fastjson.JSON;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import cn.hutool.core.bean.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import top.yueshushu.learn.domain.ext.ExtCustomerDo;
import top.yueshushu.learn.enumtype.message.ExtInterfaceTemplateType;
import top.yueshushu.learn.extension.business.ExtJobBusiness;
import top.yueshushu.learn.extension.model.gaodeweather.WeatherResponse;
import top.yueshushu.learn.extension.model.shanbeici.TranslateResponse;
import top.yueshushu.learn.extension.model.shici.PoemResponse;
import top.yueshushu.learn.extension.model.tianxing.BaiKeTiKuInfo;
import top.yueshushu.learn.extension.model.tianxing.ProverbInfo;
import top.yueshushu.learn.extension.model.tianxing.TianXingInfo;
import top.yueshushu.learn.extension.model.tianxing.TianXingResponse;
import top.yueshushu.learn.extension.model.tianxing.ZiMiInfo;
import top.yueshushu.learn.extension.service.ExtFunctionService;
import top.yueshushu.learn.message.weixin.service.WeChatService;

/**
 * 扩展定时任务
 *
 * @author yuejianli
 * @date 2022-06-10
 */
@Service
@Slf4j
public class ExtJobBusinessImpl implements ExtJobBusiness {
	@Resource
	private ExtFunctionService extFunctionService;
	private VelocityEngine velocityEngine;
	@Resource
	private WeChatService weChatService;
	
	@Value("${spring.velocity.FILE_RESOURCE_LOADER_PATH}")
	private String fileResourceLoaderPath;
	
	@PostConstruct
	public void initVelocityEngine() {
		velocityEngine = new VelocityEngine();
		Properties p = new Properties();
		p.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, fileResourceLoaderPath);
		velocityEngine.init(p);
	}
	
	@Override
	public void morning(ExtCustomerDo extCustomerDo) {
		// 查询该用户拥有的权限数据， 分别处理.
		//设置权限数据
		List<String> authInterfaceList;
		authInterfaceList = Arrays.asList(ExtInterfaceTemplateType.TRANSLATE.getCode());
		StringBuilder stringBuilder = new StringBuilder();
		if (CollectionUtils.isEmpty(authInterfaceList)) {
			log.info(">>>用户 {} 未配置功能，不执行调用", extCustomerDo.getUserAccount());
			return;
		}
		for (String interfaceCode : authInterfaceList) {
			ExtInterfaceTemplateType extInterfaceTemplateType = ExtInterfaceTemplateType.getInterfaceType(interfaceCode);
			if (extInterfaceTemplateType == null) {
				return;
			}
			stringBuilder.append(execInterfacTemplate(extCustomerDo, extInterfaceTemplateType));
		}
		//发送用户
		weChatService.sendTextMessage(extCustomerDo.getUserId(), stringBuilder.toString());
	}
	
	@Override
	public void night(ExtCustomerDo extCustomerDo) {
	
	
	}
	
	/**
	 * 执行任务，进行调用
	 *
	 * @param extCustomerDo            调用人
	 * @param extInterfaceTemplateType 调用的任务
	 */
	private String execInterfacTemplate(ExtCustomerDo extCustomerDo, ExtInterfaceTemplateType extInterfaceTemplateType) {
		//将对应转换成 map
		Map<String, Object> interfaceResultMap = Collections.emptyMap();
		switch (extInterfaceTemplateType) {
			case WEATHER: {
				//获取天气
				WeatherResponse weather = extFunctionService.getWeather("330114");
				//将对应转换成 map
				interfaceResultMap = BeanUtil.beanToMap(weather.getForecasts().get(0));
				break;
			}
			case TRANSLATE: {
				TranslateResponse translateResponse = extFunctionService.getTranslate();
				//将对应转换成 map
				interfaceResultMap = BeanUtil.beanToMap(translateResponse);
				break;
			}
			case POEM: {
				PoemResponse poemResponse = extFunctionService.getPoem();
				//将对应转换成 map
				interfaceResultMap = BeanUtil.beanToMap(poemResponse);
				break;
			}
			case COUPLETS: {
				TianXingResponse<TianXingInfo> tianXingInfoTianXingResponse = extFunctionService.getCouplets();
				//将对应转换成 map
				interfaceResultMap = BeanUtil.beanToMap(convertContent(tianXingInfoTianXingResponse));
				break;
			}
			case CLASSICAL: {
				TianXingResponse<TianXingInfo> tianXingInfoTianXingResponse = extFunctionService.getClassical();
				//将对应转换成 map
				interfaceResultMap = BeanUtil.beanToMap(convertContent(tianXingInfoTianXingResponse));
				break;
			}
			case DIALOGUE: {
				TianXingResponse<TianXingInfo> tianXingInfoTianXingResponse = extFunctionService.getDialogue();
				//将对应转换成 map
				interfaceResultMap = BeanUtil.beanToMap(convertContent(tianXingInfoTianXingResponse));
				break;
			}
			case CAIHONGPI: {
				TianXingResponse<TianXingInfo> tianXingInfoTianXingResponse = extFunctionService.getCaiHongPi();
				//将对应转换成 map
				interfaceResultMap = BeanUtil.beanToMap(convertContent(tianXingInfoTianXingResponse));
				break;
			}
			case BAIKETIKU: {
				TianXingResponse<BaiKeTiKuInfo> tianXingInfoTianXingResponse = extFunctionService.getBaiKeTiKu();
				//将对应转换成 map
				interfaceResultMap = BeanUtil.beanToMap(convertBaiKeContent(tianXingInfoTianXingResponse));
				break;
			}
			case ZAOAN: {
				TianXingResponse<TianXingInfo> tianXingInfoTianXingResponse = extFunctionService.getZaoAn();
				//将对应转换成 map
				interfaceResultMap = BeanUtil.beanToMap(convertContent(tianXingInfoTianXingResponse));
				break;
			}
			case WANAN: {
				TianXingResponse<TianXingInfo> tianXingInfoTianXingResponse = extFunctionService.getWanAn();
				//将对应转换成 map
				interfaceResultMap = BeanUtil.beanToMap(convertContent(tianXingInfoTianXingResponse));
				break;
			}
			case TENWHY: {
				TianXingResponse<TianXingInfo> tianXingInfoTianXingResponse = extFunctionService.getTenWhy();
				//将对应转换成 map
				interfaceResultMap = BeanUtil.beanToMap(convertContent(tianXingInfoTianXingResponse));
				break;
			}
			case ZIMI: {
				TianXingResponse<ZiMiInfo> tianXingInfoTianXingResponse = extFunctionService.getZiMi();
				//将对应转换成 map
				interfaceResultMap = BeanUtil.beanToMap(convertZiMiContent(tianXingInfoTianXingResponse));
				break;
			}
			case PROVERB: {
				TianXingResponse<ProverbInfo> tianXingInfoTianXingResponse = extFunctionService.getProverb();
				//将对应转换成 map
				interfaceResultMap = BeanUtil.beanToMap(convertProverbContent(tianXingInfoTianXingResponse));
				break;
			}
			default: {
				break;
			}
		}
		String line = System.lineSeparator();
		interfaceResultMap.put("line", line);
		
		// 将信息组合后发布
		
		//获取信息
		String info = getVelocityMailText(extInterfaceTemplateType, interfaceResultMap);
		log.info(">>>>输出信息 {}", info);
		return info;
	}
	
	
	private String getVelocityMailText(ExtInterfaceTemplateType extInterfaceTemplateType, Map<String, Object> dataMap) {
		VelocityContext velocityContext = new VelocityContext(dataMap);
		StringWriter writer = new StringWriter();
		String templateLocation = "interface_" + extInterfaceTemplateType.getCode() + ".vm";
		velocityEngine.mergeTemplate(templateLocation, "UTF-8", velocityContext, writer);
		return writer.toString();
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
	 * 将信息对象转换
	 *
	 * @param tianXingInfoTianXingResponse 对象转换
	 */
	private ZiMiInfo convertZiMiContent(TianXingResponse<ZiMiInfo> tianXingInfoTianXingResponse) {
		return JSON.parseObject(JSON.toJSONString(tianXingInfoTianXingResponse.getNewslist().get(0)), ZiMiInfo.class);
	}
	
	/**
	 * 将信息对象转换
	 *
	 * @param tianXingInfoTianXingResponse 对象转换
	 */
	private ProverbInfo convertProverbContent(TianXingResponse<ProverbInfo> tianXingInfoTianXingResponse) {
		return JSON.parseObject(JSON.toJSONString(tianXingInfoTianXingResponse.getNewslist().get(0)), ProverbInfo.class);
	}
	
	private BaiKeTiKuInfo convertBaiKeContent(TianXingResponse<BaiKeTiKuInfo> tianXingInfoTianXingResponse) {
		return JSON.parseObject(JSON.toJSONString(tianXingInfoTianXingResponse.getNewslist().get(0)), BaiKeTiKuInfo.class);
	}
}
