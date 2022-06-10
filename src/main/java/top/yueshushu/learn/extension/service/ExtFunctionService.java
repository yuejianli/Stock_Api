package top.yueshushu.learn.extension.service;

import top.yueshushu.learn.extension.model.gaodeweather.WeatherResponse;
import top.yueshushu.learn.extension.model.shanbeici.TranslateResponse;
import top.yueshushu.learn.extension.model.shici.PoemResponse;
import top.yueshushu.learn.extension.model.tianxing.BaiKeTiKuInfo;
import top.yueshushu.learn.extension.model.tianxing.ProverbInfo;
import top.yueshushu.learn.extension.model.tianxing.TianXingInfo;
import top.yueshushu.learn.extension.model.tianxing.TianXingResponse;
import top.yueshushu.learn.extension.model.tianxing.ZiMiInfo;

/**
 * 扩展功能使用的service
 *
 * @author Yue Jianli
 * @date 2022-06-10
 */

public interface ExtFunctionService {
	/**
	 * 获取相应城市对应的天气信息
	 *
	 * @param city 城市编码
	 */
	WeatherResponse getWeather(String city);
	
	/**
	 * 获取每日一句
	 */
	TranslateResponse getTranslate();
	
	/**
	 * 获取一句诗词
	 */
	PoemResponse getPoem();
	
	/**
	 * 获取对联信息
	 *
	 * @param
	 */
	TianXingResponse<TianXingInfo> getCouplets();
	
	/**
	 * 获取古典诗句
	 */
	TianXingResponse<TianXingInfo> getClassical();
	
	/**
	 * 获取经典对话
	 */
	TianXingResponse<TianXingInfo> getDialogue();
	
	/**
	 * 获取彩虹屁
	 * <p>
	 * {
	 * "content": "她脸上的不是汗水而是玫瑰花的露水。"
	 * }
	 */
	TianXingResponse<TianXingInfo> getCaiHongPi();
	
	/**
	 * 获取百科题库信息
	 */
	TianXingResponse<BaiKeTiKuInfo> getBaiKeTiKu();
	
	/**
	 * 获取英语信息
	 */
	TianXingResponse<TianXingInfo> getEnglish();
	
	
	/**
	 * 获取早安的信息
	 * {
	 * "content": "用努力去喂养梦想，愿跌倒不哭，明媚如初，早安。"
	 * }
	 */
	TianXingResponse<TianXingInfo> getZaoAn();
	
	/**
	 * 获取晚安的信息
	 */
	TianXingResponse<TianXingInfo> getWanAn();
	
	/**
	 * 获取十万个为什么
	 */
	TianXingResponse<TianXingInfo> getTenWhy();
	
	/**
	 * 获取字迷信息
	 */
	TianXingResponse<ZiMiInfo> getZiMi();
	
	
	/**
	 * 获取文化谚语
	 */
	TianXingResponse<ProverbInfo> getProverb();
}
