package top.yueshushu.learn.extension;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import top.yueshushu.learn.extension.model.gaodeweather.Forecast;
import top.yueshushu.learn.extension.model.gaodeweather.ForecastCasts;
import top.yueshushu.learn.extension.model.shanbeici.TranslateResponse;
import top.yueshushu.learn.extension.model.shici.PoemResponse;
import top.yueshushu.learn.message.weixin.service.WeChatService;

/**
 * 系统扩展任务
 *
 * @author yuejianli
 * @date 2022-06-07
 */
@Component
public class ExtJobSechduler {
	@Resource
	private ExtJobService extJobService;
	@Resource
	private WeChatService weChatService;
	
	/**
	 * 每天早上7点半，发送天气信息
	 */
	@Scheduled(cron = "1 30 7 * * ?")
	public void morning() {
		try {
			String line = System.lineSeparator();
			//1. 晚安信息
			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append(extJobService.getMorning());
			stringBuffer.append(line);
			//获取天气
			Forecast forecast = extJobService.getWeather().getForecasts().get(0);
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
			
			weChatService.sendMessage("LaLaLa", stringBuffer.toString());
			//	weChatService.sendMessage("d14ded4d3b48629e09268cc90ab0f38b",stringBuffer.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 每天晚上10点半，发送信息.
	 */
	@Scheduled(cron = "10 20 22 * * ?")
	public void night() {
		try {
			//1. 晚安信息
			String line = System.lineSeparator();
			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append(extJobService.getNight());
			stringBuffer.append(line);
			TranslateResponse translate = extJobService.getTranslate();
			stringBuffer.append(line);
			stringBuffer.append("晚上每日一句： ").append(line).append("英文:" + translate.getContent()).append(line).append("对应中文:" + translate.getTranslation()).append(line);
			stringBuffer.append(line);
			PoemResponse poemResponse = extJobService.getPoem();
			stringBuffer.append("晚上每日优美诗句:   ").append(line).append("内容   " + poemResponse.getContent()).
					append(line).append("出处:  " + poemResponse.getOrigin()).append(line).append("作者:  " + poemResponse.getAuthor()).append(line);
			//发送诗句
			weChatService.sendMessage("YueJianLi", stringBuffer.toString());
			weChatService.sendMessage("d14ded4d3b48629e09268cc90ab0f38b", stringBuffer.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
