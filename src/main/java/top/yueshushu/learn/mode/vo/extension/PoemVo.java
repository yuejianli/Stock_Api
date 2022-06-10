package top.yueshushu.learn.mode.vo.extension;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 用途描述
 *
 * @author yuejianli
 * @date 2022-06-10
 */
@Data
@ApiModel("每日诗词")
public class PoemVo implements Serializable {
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 来源
	 */
	private String origin;
	/**
	 * 作者
	 */
	private String author;
	/**
	 * 类别
	 */
	private String category;
}
