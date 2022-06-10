package top.yueshushu.learn.extension.model.tianxing;

import java.io.Serializable;

import lombok.Data;

/**
 * 字迷信息
 *
 * @author yuejianli
 * @date 2022-06-09
 */
@Data
public class ZiMiInfo implements Serializable {
	/**
	 * 迷面
	 */
	private String content;
	/**
	 * 答案
	 */
	private String answer;
	/**
	 * 原因
	 */
	private String reason;
}
