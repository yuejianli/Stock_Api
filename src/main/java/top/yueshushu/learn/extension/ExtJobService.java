package top.yueshushu.learn.extension;

import org.springframework.beans.factory.annotation.Value;
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
import top.yueshushu.learn.extension.model.tianxing.BaiKeTiKuInfo;
import top.yueshushu.learn.extension.model.tianxing.ProverbInfo;
import top.yueshushu.learn.extension.model.tianxing.TianXingInfo;
import top.yueshushu.learn.extension.model.tianxing.TianXingResponse;
import top.yueshushu.learn.extension.model.tianxing.ZiMiInfo;

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
	
	/*
	 使用的key
	 */
	/**
	 * towBufferfly  129@qq.com   1234abcd
	 */
	@Value("${tianxing.key1}")
	private String tianxingKey1;
	
	/**
	 * wangzheyuhou  2048146495@qq.com   1234abcd
	 */
	@Value("${tianxing.key2}")
	private String tianxingKey2;
	
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
	 * @param city 城市
	 */
	public WeatherResponse getWeather(String city) {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("key", "3060ad090681f5c501f5ca3cf5798b53");
		paramMap.put("city", city);
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
	
	/**
	 * 获取对联信息
	 *
	 * @param
	 */
	public TianXingResponse<TianXingInfo> getCouplets() {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("key", tianxingKey1);
		TianXingResponse<TianXingInfo> couplets = restTemplate.getForEntity(
				"http://api.tianapi.com/duilian/index?key={key}",
				TianXingResponse.class,
				paramMap
		).getBody();
		return couplets;
	}
	
	/**
	 * 获取古典诗句
	 */
	public TianXingResponse<TianXingInfo> getClassical() {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("key", tianxingKey1);
		TianXingResponse<TianXingInfo> couplets = restTemplate.getForEntity(
				"http://api.tianapi.com/gjmj/index?key={key}",
				TianXingResponse.class,
				paramMap
		).getBody();
		return couplets;
	}
	
	/**
	 * 获取经典对话
	 */
	public TianXingResponse<TianXingInfo> getDialogue() {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("key", tianxingKey1);
		TianXingResponse<TianXingInfo> couplets = restTemplate.getForEntity(
				"http://api.tianapi.com/dialogue/index?key={key}",
				TianXingResponse.class,
				paramMap
		).getBody();
		return couplets;
	}
	
	/**
	 * 获取彩虹屁
	 * <p>
	 * {
	 * "content": "她脸上的不是汗水而是玫瑰花的露水。"
	 * }
	 */
	public TianXingResponse<TianXingInfo> getCaiHongPi() {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("key", tianxingKey1);
		TianXingResponse<TianXingInfo> couplets = restTemplate.getForEntity(
				"http://api.tianapi.com/caihongpi/index?key={key}",
				TianXingResponse.class,
				paramMap
		).getBody();
		return couplets;
	}
	
	/**
	 * 获取百科题库信息
	 */
	public TianXingResponse<BaiKeTiKuInfo> getBaiKeTiKu() {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("key", tianxingKey1);
		TianXingResponse<BaiKeTiKuInfo> couplets = restTemplate.getForEntity(
				"http://api.tianapi.com/baiketiku/index?key={key}",
				TianXingResponse.class,
				paramMap
		).getBody();
		return couplets;
	}
	
	/**
	 * 获取英语信息
	 */
	public TianXingResponse<TianXingInfo> getEnglish() {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("key", tianxingKey1);
		TianXingResponse<TianXingInfo> couplets = restTemplate.getForEntity(
				"http://api.tianapi.com/everyday/index?key={key}",
				TianXingResponse.class,
				paramMap
		).getBody();
		return couplets;
	}
	
	
	/**
	 * 获取早安的信息
	 * {
	 * "content": "用努力去喂养梦想，愿跌倒不哭，明媚如初，早安。"
	 * }
	 */
	public TianXingResponse<TianXingInfo> getZaoAn() {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("key", tianxingKey1);
		TianXingResponse<TianXingInfo> couplets = restTemplate.getForEntity(
				"http://api.tianapi.com/zaoan/index?key={key}",
				TianXingResponse.class,
				paramMap
		).getBody();
		return couplets;
	}
	
	/**
	 * 获取晚安的信息
	 */
	public TianXingResponse<TianXingInfo> getWanAn() {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("key", tianxingKey1);
		TianXingResponse<TianXingInfo> couplets = restTemplate.getForEntity(
				"http://api.tianapi.com/wanan/index?key={key}",
				TianXingResponse.class,
				paramMap
		).getBody();
		return couplets;
	}
	
	/**
	 * 获取十万个为什么
	 */
	public TianXingResponse<TianXingInfo> getTenWhy() {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("key", tianxingKey1);
		TianXingResponse<TianXingInfo> couplets = restTemplate.getForEntity(
				"http://api.tianapi.com/tenwhy/index?key={key}",
				TianXingResponse.class,
				paramMap
		).getBody();
		return couplets;
	}
	
	/**
	 * 获取字迷信息
	 */
	public TianXingResponse<ZiMiInfo> getZiMi() {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("key", tianxingKey1);
		TianXingResponse<ZiMiInfo> couplets = restTemplate.getForEntity(
				"http://api.tianapi.com/zimi/index?key={key}",
				TianXingResponse.class,
				paramMap
		).getBody();
		return couplets;
	}
	
	
	/**
	 * 获取文化谚语
	 */
	public TianXingResponse<ProverbInfo> getProverb() {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("key", tianxingKey2);
		TianXingResponse<ProverbInfo> couplets = restTemplate.getForEntity(
				"http://api.tianapi.com/proverb/index?key={key}",
				TianXingResponse.class,
				paramMap
		).getBody();
		return couplets;
	}
}

