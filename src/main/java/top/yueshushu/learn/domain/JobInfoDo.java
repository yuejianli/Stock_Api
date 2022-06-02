package top.yueshushu.learn.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 定时任务信息处理
 * </p>
 *
 * @author 岳建立 自定义的
 * @since 2022-01-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("job_info")
public class JobInfoDo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 任务编码
     */
    @TableField("code")
    private String code;

    /**
     * 任务名称
     */
    @TableField("name")
    private String name;

    /**
     * 任务描述
     */
    @TableField("description")
    private String description;
    /**
     * 任务参数
     */
    @TableField("param")
    private String param;

    /**
     * 创建者
     */
    @TableField("author")
    private String author;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;
    /**
     * 任务cron表达式
     */
    @TableField("cron")
    private String cron;
    /**
     * 触发类型 1为手动 0为自动
     */
    @TableField("trigger_type")
    private Integer triggerType;

    /**
     * 触发状态 1启动中 0为禁止
     */
    @TableField("trigger_status")
    private Integer triggerStatus;

    /**
     * 上次触发的时间
     */
    @TableField("trigger_last_time")
    private LocalDateTime triggerLastTime;

    /**
     * 上次触发的结果 1为正常 0为失败
     */
    @TableField("trigger_last_result")
    private Integer triggerLastResult;

    /**
     * 上次触发错误的错误信息
     */
    @TableField("trigger_last_error_message")
    private String triggerLastErrorMessage;


    /**
     * 下次触发的时间
     */
    @TableField("trigger_next_time")
    private LocalDateTime triggerNextTime;
    /**
     * 1为正常 0为删除
     */
    @TableField("flag")
    private Integer flag;

}
