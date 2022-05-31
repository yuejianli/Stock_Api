package top.yueshushu.learn.mode.ro;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import top.yueshushu.learn.response.PageRo;

import java.io.Serializable;

/**
 * @ClassName:TradeRo
 * @Description TODO
 * @Author 岳建立
 * @Date 2022/1/3 17:14
 * @Version 1.0
 **/
@Data
@ApiModel("交易的信息Ro")
public class TradeRo extends PageRo implements Serializable {
    @ApiModelProperty("类型 1为模拟 0为正式")
    private Integer mockType;
    @ApiModelProperty("对应的登录员工")
    private Integer userId;
}
