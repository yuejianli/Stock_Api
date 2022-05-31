package top.yueshushu.learn.mode.ro;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import top.yueshushu.learn.response.PageRo;

import java.io.Serializable;

/**
 * @ClassName:ConfigRo
 * @Description TODO
 * @Author 岳建立
 * @Date 2022/1/3 10:39
 * @Version 1.0
 **/
@Data
@ApiModel("系统配置参数")
public class ConfigRo extends PageRo implements Serializable {
    @ApiModelProperty("配置参数")
    private Integer userId;
    @ApiModelProperty("对应的id")
    private Integer id;
    @ApiModelProperty("修改新名称")
    private String name;
    @ApiModelProperty("修改的值")
    private String codeValue;
}
