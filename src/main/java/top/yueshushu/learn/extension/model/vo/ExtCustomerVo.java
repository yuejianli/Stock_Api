package top.yueshushu.learn.extension.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 关联使用的客户信息
 *
 * @author yuejianli
 * @date 2022-06-09
 */
@Data
@ApiModel("客户信息")
public class ExtCustomerVo implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty("id编号自增")
	private Integer id;

	@ApiModelProperty("用户的账号")
	private String userAccount;

	@ApiModelProperty("用户的昵称")
	private String name;

	@ApiModelProperty("用户的微信id号")
	private String wxId;

	@ApiModelProperty("性别  1为男  0为 女")
	private Integer sex;

	@ApiModelProperty("生日")
	/** 使用@JsonFormat注解格式化日期 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date birthday;

	@ApiModelProperty("用户所在城市的编码")
	private String city;

	@ApiModelProperty("是否删除 1为正常 2为删除")
	private Integer flag;
}
