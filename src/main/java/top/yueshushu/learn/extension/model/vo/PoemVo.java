package top.yueshushu.learn.extension.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用途描述
 *
 * @author yuejianli
 * @date 2022-06-10
 */
@Data
@ApiModel("每日诗词")
public class PoemVo implements Serializable {

	@ApiModelProperty("内容")
	private String content;

	@ApiModelProperty("来源")
	private String origin;

	@ApiModelProperty("作者")
	private String author;

	@ApiModelProperty("类别")
	private String category;
}
