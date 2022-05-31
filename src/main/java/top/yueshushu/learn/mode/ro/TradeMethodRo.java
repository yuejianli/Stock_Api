package top.yueshushu.learn.mode.ro;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import top.yueshushu.learn.response.PageRo;

import java.io.Serializable;

/**
 * @ClassName:TradeMethodRo
 * @Description 交易方法
 * @Author 岳建立
 * @Date 2022/1/3 12:03
 * @Version 1.0
 **/
@Data
@ApiModel("缓存配置的Ro")
public class TradeMethodRo extends PageRo implements Serializable {
    @ApiModelProperty("用户id")
    private Integer userId;
    @ApiModelProperty("关键字查询")
    private String keyword;
}
