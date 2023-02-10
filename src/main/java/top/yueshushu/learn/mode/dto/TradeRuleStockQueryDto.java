package top.yueshushu.learn.mode.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName:TradeRuleStockQueryDto
 * @Description TODO
 * @Author zk_yjl
 * @Date 2022/1/27 15:03
 * @Version 1.0
 * @Since 1.0
 **/
@Data
@ApiModel("交易股票规则查询")
public class TradeRuleStockQueryDto implements Serializable {
    @ApiModelProperty("用户id")
    private Integer userId;
    @ApiModelProperty("规则id")
    private Integer ruleId;
    @ApiModelProperty("对应的类型")
    private Integer ruleType;
    @ApiModelProperty("是否是模拟的")
    private Integer mockType;
    @ApiModelProperty("状态")
    private Integer status;
    @ApiModelProperty("股票编码")
    private String stockCode;
    @ApiModelProperty("规则条件Id")
    private Integer ruleConditionId;

    @ApiModelProperty("规则id集合")
    private List<Integer> ruleIdList;

    @ApiModelProperty("股票编码集合")
    private List<String> stockCodeList;
}
