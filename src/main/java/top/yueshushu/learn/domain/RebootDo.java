package top.yueshushu.learn.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * reboot 钉钉机器人配置
 *
 * @author yuejianli
 * @date 2023-01-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("reboot")
public class RebootDo implements Serializable {
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 编码，唯一约束
     */
    @TableField("code")
    private String code;
    /**
     * 描述
     */
    @TableField("description")
    private String description;
    /**
     * 发送消息使用的 webhook
     */
    @TableField("webhook")
    private String webhook;

    /**
     * 参数信息
     */
    @TableField("param")
    private String param;
    /**
     * 触发状态 1启动中 0为禁止
     */
    @TableField("status")
    private Integer status;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;
}
