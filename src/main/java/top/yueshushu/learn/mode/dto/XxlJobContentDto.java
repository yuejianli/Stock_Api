package top.yueshushu.learn.mode.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName:XxlJobContentDto
 * @Description 定时任务 xxlJob 响应信息
 * @Author 岳建立
 * @Date 2022/1/9 13:58
 * @Version 1.0
 **/
@Data
public class XxlJobContentDto implements Serializable {
    private int code;
    private String msg;
    private String content;
}
