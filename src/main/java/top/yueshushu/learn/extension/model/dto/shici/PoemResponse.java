package top.yueshushu.learn.extension.model.dto.shici;

import lombok.Data;

import java.io.Serializable;

/**
 * 诗词响应信息
 *
 * @author yuejianli
 * @date 2022-06-07
 */
@Data
public class PoemResponse implements Serializable {
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
