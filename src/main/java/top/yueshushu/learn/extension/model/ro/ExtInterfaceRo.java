package top.yueshushu.learn.extension.model.ro;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import top.yueshushu.learn.response.PageRo;

import java.io.Serializable;

/**
 * @Description 接口信息
 * @Author yuejianli
 * @Date 2022/6/11 15:20
 **/
@ApiModel("提供功能Ro")
@Data
public class ExtInterfaceRo extends PageRo implements Serializable {
    @ApiModelProperty("查询关键字")
    private String keyword;
}
