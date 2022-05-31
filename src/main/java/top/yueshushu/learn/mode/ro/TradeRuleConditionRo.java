package top.yueshushu.learn.mode.ro;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import top.yueshushu.learn.controller.BaseController;

import java.io.Serializable;

/**
 * @ClassName:TradeRuleConditionRo
 * @Description 交易规则条件对应的Ro 信息
 * @Author zk_yjl
 * @Date 2022/1/26 14:25
 * @Version 1.0
 * @Since 1.0
 **/
@Data
@ApiModel("交易规则条件对应的Ro")
public class TradeRuleConditionRo implements Serializable {
    @ApiModelProperty("id编号")
    private Integer id;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("描述信息")
    private String description;
    @ApiModelProperty("用户Id")
    private Integer userId;
}
