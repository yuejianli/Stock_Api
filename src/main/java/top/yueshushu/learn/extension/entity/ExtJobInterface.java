package top.yueshushu.learn.extension.entity;

import lombok.Data;

import java.util.Date;

/**
 * 关联使用的客户信息
 *
 * @author yuejianli
 * @date 2022-06-09
 */
@Data
public class ExtJobInterface {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer extJobId;

    private Integer extInterfaceId;

    private Date createTime;

}
