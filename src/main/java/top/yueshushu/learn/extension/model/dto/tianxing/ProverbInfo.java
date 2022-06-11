package top.yueshushu.learn.extension.model.dto.tianxing;

import lombok.Data;

import java.io.Serializable;

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
