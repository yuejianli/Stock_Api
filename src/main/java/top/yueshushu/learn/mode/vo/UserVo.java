package top.yueshushu.learn.mode.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import top.yueshushu.learn.entity.User;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName:UserVo
 * @Description TODO
 * @Author 岳建立
 * @Date 2022/1/2 10:55
 * @Version 1.0
 **/
@Data
@ApiModel("用户登录后展示信息")
public class UserVo implements Serializable {
    @ApiModelProperty("当前的用户")
    private User currentUser;
    @ApiModelProperty("当前的菜单")
    private List<MenuVo> menuList;
}
