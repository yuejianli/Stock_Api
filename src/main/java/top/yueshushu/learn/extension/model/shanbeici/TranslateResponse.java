package top.yueshushu.learn.extension.model.shanbeici;

import java.io.Serializable;

import lombok.Data;

/**
 * 扇背每日一句翻译
 *
 * @author yuejianli
 * @date 2022-06-07
 */
@Data
public class TranslateResponse implements Serializable {
	/**
	 * 英文内容
	 */
	private String content;
	/**
	 * 作者
	 */
	private String author;
	/**
	 * 日期
	 */
	private String assign_date;
	/**
	 * 中文翻译
	 */
	private String translation;
}
