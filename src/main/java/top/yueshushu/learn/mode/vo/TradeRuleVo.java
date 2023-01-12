package top.yueshushu.learn.mode.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName:TradeRuleVo
 * @Description TODO
 * @Author zk_yjl
 * @Date 2022/1/27 19:34
 * @Version 1.0
 * @Since 1.0
 **/
@Data
@ApiModel("交易规则处理")
public class TradeRuleVo implements Serializable {
    @ApiModelProperty("id主键")
    private Integer id;
    @ApiModelProperty("规则的名称")
    private String name;
    @ApiModelProperty("规则条件编号")
    private Integer conditionId;
    @ApiModelProperty("规则条件名称")
    private String conditionName;
    @ApiModelProperty("比较类型 1为金额 2为比例")
    private Integer ruleValueType;
    @ApiModelProperty("规则对应值")
    private BigDecimal ruleValue;
    @ApiModelProperty("交易股票数")
    private Integer tradeNum;
    @ApiModelProperty("交易值类型 1为金额 2为比例")
    private Integer tradeValueType;
    @ApiModelProperty("交易差值")
    private BigDecimal tradePrice;
    @TableField("规则类型 1为买入 2为卖出")
    private Integer ruleType;
    @ApiModelProperty("状态 1为正常 0为禁用")
    private Integer status;
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("修改时间")
    private Date updateTime;
}
