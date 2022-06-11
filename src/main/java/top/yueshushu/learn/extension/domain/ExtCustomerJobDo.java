package top.yueshushu.learn.extension.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 关联使用的客户信息
 *
 * @author yuejianli
 * @date 2022-06-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ext_customer_job")
public class ExtCustomerJobDo {

    private static final long serialVersionUID = 1L;

    /**
     * id编号自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 关联的客户
     */
    @TableField("ext_customer_id")
    private Integer extCustomerId;

    /**
     * 关联的任务
     */
    @TableField("ext_job_id")
    private Integer extJobId;

    /**
     * 关联的接口id
     */
    @TableField("ext_interface_id")
    private Integer extInterfaceId;

    /**
     * 关联时间
     */
    @TableField("create_time")
    private Date createTime;

}
