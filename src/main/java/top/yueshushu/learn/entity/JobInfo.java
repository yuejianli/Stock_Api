package top.yueshushu.learn.entity;

import lombok.Data;

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
public class JobInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id自增
     */
    private Integer id;

    /**
     * 任务编码
     */
    private String code;

    /**
     * 任务名称
     */
    private String name;

    /**
     * 任务描述
     */
    private String description;
    /**
     * 任务参数
     */
    private String param;

    /**
     * 创建者
     */
    private String author;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 任务cron表达式
     */
    private String cron;
    /**
     * 触发类型 1为手动 0为自动
     */
    private Integer triggerType;

    /**
     * 触发状态 1启动中 0为禁止
     */
    private Integer triggerStatus;

    /**
     * 上次触发的时间
     */
    private LocalDateTime triggerLastTime;

    /**
     * 上次触发的结果 1为正常 0为失败
     */
    private Integer triggerLastResult;

    /**
     * 上次触发错误的错误信息
     */
    private String triggerLastErrorMessage;


    /**
     * 下次触发的时间
     */
    private LocalDateTime triggerNextTime;
    /**
     * 1为正常 0为删除
     */
    private Integer flag;

}
