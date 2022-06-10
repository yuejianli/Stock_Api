package top.yueshushu.learn.extension.model.tianxing;

import java.io.Serializable;

import lombok.Data;

/**
 * 天行返回信息
 * * https://www.tianapi.com/apiview/213
 *
 * @author yuejianli
 * @date 2022-06-09
 */
@Data
public class TianXingInfo implements Serializable {
	/**
	 * title 题目
	 */
	private String title;
	/**
	 * 返回内容
	 */
	private String content;
	/**
	 * 来源
	 */
	private String source;
	/**
	 * 对话
	 */
	private String dialogue;
	/**
	 * 中文信息
	 */
	private String note;
}
