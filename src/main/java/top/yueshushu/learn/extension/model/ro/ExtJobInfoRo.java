package top.yueshushu.learn.extension.model.ro;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import top.yueshushu.learn.response.PageRo;

import java.io.Serializable;

/**
 * @Description 扩展任务Ro
 * @Author yuejianli
 * @Date 2022/6/11 15:20
 **/
@Data
public class ExtJobInfoRo extends PageRo implements Serializable {
    @ApiModelProperty("扩展任务id")
    private Integer id;
    @ApiModelProperty("扩展任务状态")
    private Integer triggerStatus;
}
