package top.yueshushu.learn.mode.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName:TradeRuleDbVo
 * @Description TODO
 * @Author zk_yjl
 * @Date 2022/1/27 19:34
 * @Version 1.0
 * @Since 1.0
 **/
@Data
@ApiModel("交易打板规则处理")
public class TradeRuleDbVo implements Serializable {
    @ApiModelProperty("id主键")
    private Integer id;
    @ApiModelProperty("规则的名称")
    private String name;
    @ApiModelProperty("打板的股票类型")
    private Integer codeType;
    @ApiModelProperty("打板的股票类型描述")
    private String codeTypeStr;
    @ApiModelProperty("每日最多买入次数")
    private Integer buyNum;
    @ApiModelProperty("买入的参数配置信息")
    private String buyParam;
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("修改时间")
    private Date updateTime;
}
