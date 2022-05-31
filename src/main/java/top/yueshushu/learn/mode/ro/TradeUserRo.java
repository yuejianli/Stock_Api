package top.yueshushu.learn.mode.ro;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.StringUtils;
import top.yueshushu.learn.common.Const;

import java.io.Serializable;

/**
 * @ClassName:TradeUserRo
 * @Description TODO
 * @Author 岳建立
 * @Date 2022/1/2 9:35
 * @Version 1.0
 **/
@Data
@ApiModel("交易用户信息Ro")
public class TradeUserRo implements Serializable {
    @ApiModelProperty(value = "登录的用户的id",hidden = true)
    private Integer id;
    @ApiModelProperty("密码")
    private String password;
    @ApiModelProperty("四位纯数字的验证码")
    private String identifyCode ;
    @ApiModelProperty("随机数")
    private String randNum ;

    public String getRandNum(){
        if(StringUtils.hasText(randNum)){
            return randNum;
        }
        return Const.RAND_NUMBER_PREFIX +System.currentTimeMillis();
    }
}
