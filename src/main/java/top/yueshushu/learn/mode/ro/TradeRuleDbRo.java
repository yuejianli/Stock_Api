package top.yueshushu.learn.mode.ro;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import top.yueshushu.learn.response.PageRo;

import java.io.Serializable;

/**
 * @ClassName:TradeRuleRo
 * @Description 交易规则的Ro
 * @Author zk_yjl
 * @Date 2022/1/26 15:39
 * @Version 1.0
 * @Since 1.0
 **/
@ApiModel("交易打板规则的Ro")
@Data
public class TradeRuleDbRo extends PageRo implements Serializable {
    @ApiModelProperty("id主键")
    private Integer id;
    @ApiModelProperty("规则的名称")
    private String name;
    @ApiModelProperty("打板的股票类型")
    private Integer codeType;
    @ApiModelProperty("每日最多买入次数")
    private Integer buyNum;
    @ApiModelProperty("买入的参数配置信息")
    private String buyParam;
    @ApiModelProperty("用户编号")
    private Integer userId;
    @ApiModelProperty("1为模拟0为实际")
    private Integer mockType;
}
