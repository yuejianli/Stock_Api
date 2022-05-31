package top.yueshushu.learn.mode.ro;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName:UserRo
 * @Description TODO
 * @Author 岳建立
 * @Date 2022/1/2 9:35
 * @Version 1.0
 **/
@Data
@ApiModel("用户信息Ro")
public class UserRo implements Serializable {
    @ApiModelProperty("账号")
    private String account;
    @ApiModelProperty("密码")
    private String password;
    @ApiModelProperty("是否自动登录 1为自动登录 0为非自动")
    private Integer autoLogin = 0;
    @ApiModelProperty("是否记住密码 1为记住密码 0为非记住密码")
    private Integer rememberPs = 0;
    @ApiModelProperty("是否同意协议 1为同意 0为不同意")
    private Integer readAgreement = 0;
}
