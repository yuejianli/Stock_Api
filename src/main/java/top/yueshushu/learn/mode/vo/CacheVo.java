package top.yueshushu.learn.mode.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName:CacheVo
 * @Description TODO
 * @Author 岳建立
 * @Date 2022/1/3 12:08
 * @Version 1.0
 **/
@ApiModel("缓存展示Vo")
@Data
public class CacheVo implements Serializable {
    @ApiModelProperty("缓存key")
    private String key;
    @ApiModelProperty("缓存对应的值")
    private String value;
}
