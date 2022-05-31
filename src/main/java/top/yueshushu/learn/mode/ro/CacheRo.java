package top.yueshushu.learn.mode.ro;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import top.yueshushu.learn.response.PageRo;

import java.io.Serializable;

/**
 * @ClassName:CacheRo
 * @Description TODO
 * @Author 岳建立
 * @Date 2022/1/3 12:03
 * @Version 1.0
 **/
@Data
@ApiModel("缓存配置的Ro")
public class CacheRo extends PageRo implements Serializable {
    @ApiModelProperty("用户id")
    private Integer userId;
    @ApiModelProperty("配置的key")
    private String key;
    @ApiModelProperty("配置的值")
    private String value;
    @ApiModelProperty("筛选关键字")
    private String keyword;
    @ApiModelProperty("类型 1是私有的 0为公共的")
    private Integer type;
}
