package top.yueshushu.learn.mode.ro;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import top.yueshushu.learn.response.PageRo;

import java.io.Serializable;

/**
 * 任务信息Ro
 *
 * @author yuejianli
 * @date 2022-06-02
 */
@Data
@ApiModel("任务信息使用的Ro")
public class JobInfoRo extends PageRo implements Serializable {
    private Integer id;
    private Integer triggerStatus;
}
