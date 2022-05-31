package top.yueshushu.learn.mode.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName:StockRuleDto
 * @Description TODO
 * @Author zk_yjl
 * @Date 2022/1/28 15:08
 * @Version 1.0
 * @Since 1.0
 **/
@Data
@ApiModel("股票配置规则")
public class StockRuleDto implements Serializable {
    @ApiModelProperty("股票编码")
    private String stockCode;
    @ApiModelProperty("规则Id")
    private Integer ruleId;
    @ApiModelProperty("规则的名称")
    private String ruleName;
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("类型 1为启用 0为禁用")
    private Integer status;
    @ApiModelProperty("类型 1买入 2卖出")
    private Integer ruleType;
    @ApiModelProperty("类型 1为模拟 0为真实")
    private Integer mockType;
}
