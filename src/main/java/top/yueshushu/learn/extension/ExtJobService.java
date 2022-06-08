package top.yueshushu.learn.extension;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import top.yueshushu.learn.extension.model.gaodeweather.WeatherResponse;
import top.yueshushu.learn.extension.model.shanbeici.TranslateResponse;
import top.yueshushu.learn.extension.model.shici.PoemResponse;

/**
 * ext job任务具体的服务信息
 *
 * @author yuejianli
 * @date 2022-06-07
 */
@Component
@Slf4j
public class ExtJobService {
	@Resource
	private RestTemplate restTemplate;
	
	/**
	 * 获取天气情况
	 *
	 * @param
	 */
	public String getMorning() {
		return "早上好,欢欢";
	}
	
	/**
	 * 获取天气情况
	 *
	 * @param
	 */
	public String getNight() {
		return "该睡觉了,欢欢 。吾日三省吾身，回忆一下，今天都做了哪些有意义的事情.";
	}
	
	/**
	 * 获取天气情况
	 *
	 * @param
	 */
	public WeatherResponse getWeather() {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("key", "3060ad090681f5c501f5ca3cf5798b53");
		paramMap.put("city", "522701");
		paramMap.put("extensions", "all");
		WeatherResponse weatherResponse = restTemplate.getForEntity(
				"https://restapi.amap.com/v3/weather/weatherInfo?key={key}&city={city}&extensions={extensions}",
				WeatherResponse.class,
				paramMap
		).getBody();
		return weatherResponse;
	}
	
	/**
	 * 获取每日一句
	 *
	 * @param
	 */
	public TranslateResponse getTranslate() {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("date", DateUtil.format(DateUtil.date(), "yyyyMMdd"));
		TranslateResponse translateResponse = restTemplate.getForEntity(
				"https://apiv3.shanbay.com/weapps/dailyquote/quote/?date={date}",
				TranslateResponse.class,
				paramMap
		).getBody();
		return translateResponse;
	}
	
	/**
	 * 获取一句诗词
	 *
	 * @param
	 */
	public PoemResponse getPoem() {
		Map<String, String> paramMap = new HashMap<>();
		return restTemplate.getForEntity(
				"https://v1.jinrishici.com/all.json",
				PoemResponse.class,
				paramMap
		).getBody();
	}
}
