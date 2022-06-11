package top.yueshushu.learn.extension.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import top.yueshushu.learn.extension.entity.ExtInterface;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 关联使用的客户信息
 *
 * @author yuejianli
 * @date 2022-06-09
 */
@Data
@ApiModel("用户配置任务展示")
public class ExtCustomerJobVo implements Serializable {

	private static final long serialVersionUID = 1L;
	@ApiModelProperty("客户id")
	private Integer extCustomerId;
	@ApiModelProperty("任务id")
	private Integer extJobId;
	@ApiModelProperty("所有的功能列表")
	private List<ExtInterface> allInterfaceList;
	@ApiModelProperty("关联的功能列表")
	private List<Integer> interfaceIdList;
	@ApiModelProperty("创建时间")
	private Date createTime;

}
