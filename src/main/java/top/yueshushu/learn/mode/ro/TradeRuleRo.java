package top.yueshushu.learn.mode.ro;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import top.yueshushu.learn.response.PageRo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @ClassName:TradeRuleRo
 * @Description 交易规则的Ro
 * @Author zk_yjl
 * @Date 2022/1/26 15:39
 * @Version 1.0
 * @Since 1.0
 **/
@ApiModel("交易规则的Ro")
@Data
public class TradeRuleRo extends PageRo implements Serializable {
    @ApiModelProperty("id主键")
    private Integer id;
    @ApiModelProperty("规则的名称")
    private String name;
    @ApiModelProperty("规则条件编号")
    private String conditionCode;
    @ApiModelProperty("规则计算 1为小于 2为大于")
    private Integer conditionType;
    @ApiModelProperty("比较类型 1为金额 2为比例")
    private Integer ruleValueType;
    @ApiModelProperty(" 规则对应值")
    private BigDecimal ruleValue;
    @ApiModelProperty("交易股票数")
    private Integer tradeNum;
    @ApiModelProperty("交易值类型 1为金额 2为比例")
    private Integer tradeValueType;
    @ApiModelProperty("交易差值")
    private BigDecimal tradePrice;
    @ApiModelProperty("规则类型 1为买入 2为卖出")
    private Integer ruleType;
    @ApiModelProperty("所属用户")
    private Integer userId;
    @ApiModelProperty("1为模拟0为实际")
    private Integer mockType;
}
