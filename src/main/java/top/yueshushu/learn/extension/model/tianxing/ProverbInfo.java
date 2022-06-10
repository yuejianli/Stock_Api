package top.yueshushu.learn.extension.model.tianxing;

import java.io.Serializable;

import lombok.Data;

/**
 * 文化谚语
 *
 * @author yuejianli
 * @date 2022-06-09
 */
@Data
public class ProverbInfo implements Serializable {
	/**
	 * 前半部分
	 */
	private String front;
	/**
	 * 后半部分
	 */
	private String behind;
}
