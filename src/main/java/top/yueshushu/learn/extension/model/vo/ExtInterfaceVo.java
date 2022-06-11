package top.yueshushu.learn.extension.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 使用的功能接口
 *
 * @author yuejianli
 * @date 2022-06-09
 */
@Data
@ApiModel("接口")
public class ExtInterfaceVo implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("id编号")
    private Integer id;
    @ApiModelProperty("编码")
    private String code;
    @ApiModelProperty("接口名称")
    private String name;
    @ApiModelProperty("描述信息")
    private String description;
    @ApiModelProperty("文档地址")
    private String docUrl;
    @ApiModelProperty("标识")
    private Integer flag;
}
