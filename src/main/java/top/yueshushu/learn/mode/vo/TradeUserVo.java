package top.yueshushu.learn.mode.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName:TradeUserVo
 * @Description TODO
 * @Author 岳建立
 * @Date 2022/1/2 14:07
 * @Version 1.0
 **/
@Data
@ApiModel("交易用户登录成功后返回")
public class TradeUserVo implements Serializable {
    @ApiModelProperty("交易用户id")
    private Integer userId;
    @ApiModelProperty("对应菜单id")
    private List<MenuVo> menuList;
}
