package top.yueshushu.learn.extension.model.dto.tianxing;

import lombok.Data;

import java.io.Serializable;

/**
 * 百科题库信息
 *
 * @author yuejianli
 * @date 2022-06-09
 */
@Data
public class BaiKeTiKuInfo implements Serializable {
    /**
	 * 题目
	 */
	private String title;
	/**
	 * 回答  A- D
	 */
	private String answerA;
	private String answerB;
	private String answerC;
	private String answerD;
	/**
	 * 答案
	 */
	private String answer;
	/**
	 * 答案解析
	 */
	private String analytic;
}
