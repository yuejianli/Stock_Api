package top.yueshushu.learn.mode.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName:StockRuleVo
 * @Description 股票配置规则展示信息
 * @Author zk_yjl
 * @Date 2022/1/28 14:30
 * @Version 1.0
 * @Since 1.0
 **/
@Data
@ApiModel("股票配置规则对应信息")
public class StockRuleVo implements Serializable {
    @ApiModelProperty("股票编码")
    private String stockCode;
    @ApiModelProperty("股票名称")
    private String stockName;
    @ApiModelProperty("买入的规则Id")
    private Integer buyRuleId;
    @ApiModelProperty("买入的规则名称")
    private String buyRuleName;
    @ApiModelProperty("买入的规则配置时间")
    private Date buyCreateTime;
    @ApiModelProperty("买入的规则状态")
    private Integer buyRuleStatus;
    @ApiModelProperty("卖出的规则Id")
    private Integer sellRuleId;
    @ApiModelProperty("卖出的规则名称")
    private String sellRuleName;
    @ApiModelProperty("卖出的规则配置时间")
    private Date sellCreateTime;
    @ApiModelProperty("卖出的规则状态")
    private Integer sellRuleStatus;
}
