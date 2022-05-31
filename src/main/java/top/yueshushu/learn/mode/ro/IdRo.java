package top.yueshushu.learn.mode.ro;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName:IdRo
 * @Description TODO
 * @Author 岳建立
 * @Date 2022/1/2 13:40
 * @Version 1.0
 **/
@Data
@ApiModel("id配置Ro")
public class IdRo implements Serializable {
    @ApiModelProperty("单个id")
    private Integer id;
    @ApiModelProperty("ids集合")
    private List<Integer> ids;
}
