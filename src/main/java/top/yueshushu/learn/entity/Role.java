package top.yueshushu.learn.entity;

import io.swagger.annotations.ApiModel;
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
@ApiModel("角色")
public class Role implements Serializable {
    private Integer id;

    private String name;

    private String description;

    private Date createTime;

    private Date updateTime;

    private Integer flag;
}
