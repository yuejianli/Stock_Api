package top.yueshushu.learn.mode.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用途描述
 *
 * @author yuejianli
 * @date 2023-03-16
 */
@Data
@ApiModel("角色Vo")
public class RoleVo implements Serializable {
    @ApiModelProperty("角色id")
    private Integer id;

    @ApiModelProperty("角色的名称")
    private String name;

    @ApiModelProperty("角色描述")
    private String description;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;
}
