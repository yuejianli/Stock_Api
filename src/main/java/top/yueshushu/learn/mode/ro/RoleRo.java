package top.yueshushu.learn.mode.ro;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用途描述
 *
 * @author yuejianli
 * @date 2023-03-16
 */
@ApiModel("角色Ro")
@Data
public class RoleRo implements Serializable {
    @ApiModelProperty("角色id")
    private Integer id;

    @ApiModelProperty("角色的名称")
    private String name;

    @ApiModelProperty("角色描述")
    private String description;
}
