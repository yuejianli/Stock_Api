package top.yueshushu.learn.extension.model.ro;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description 客户配置任务使用
 * @Author yuejianli
 * @Date 2022/6/11 15:20
 **/
@Data
@ApiModel("客户配置任务使用")
public class ExtCustomerJobRo implements Serializable {
    @ApiModelProperty("客户id")
    private Integer extCustomerId;
    @ApiModelProperty("任务id")
    private Integer extJobId;
    @ApiModelProperty("功能接口列表id集合")
    private List<Integer> interfaceIdList;
}
