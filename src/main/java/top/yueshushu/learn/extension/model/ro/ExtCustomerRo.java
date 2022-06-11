package top.yueshushu.learn.extension.model.ro;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import top.yueshushu.learn.response.PageRo;

import java.io.Serializable;

/**
 * @Description 查询客户信息
 * @Author yuejianli
 * @Date 2022/6/11 15:20
 **/
@Data
@ApiModel("客户信息")
public class ExtCustomerRo extends PageRo implements Serializable {
    @ApiModelProperty("关键字")
    private String keyword;
    @ApiModelProperty("id编号")
    private Integer id;
    @ApiModelProperty("用户的账号")
    private String userAccount;
    @ApiModelProperty("用户的昵称")
    private String name;
    @ApiModelProperty("用户的企业微信id号")
    private String wxId;
    @ApiModelProperty("性别  1为男  0为 女")
    private Integer sex;
    @ApiModelProperty("生日")
    private String birthdayStr;
    @ApiModelProperty("用户所在城市的编码")
    private String city;
}
