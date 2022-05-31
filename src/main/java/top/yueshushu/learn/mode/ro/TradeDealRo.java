package top.yueshushu.learn.mode.ro;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName:TradeDealRo
 * @Description TODO
 * @Author 岳建立
 * @Date 2022/1/3 17:00
 * @Version 1.0
 **/
@Data
@ApiModel("我的今日成交Ro")
public class TradeDealRo extends TradeRo implements Serializable {
    @ApiModelProperty("开始日期")
    private String startDate;
    @ApiModelProperty("结束日期")
    private String endDate;
    @ApiModelProperty("股票编码")
    private String code;
    @ApiModelProperty("买卖类型")
    private Integer dealType;
}
