package top.yueshushu.learn.mode.ro;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName:SellRo
 * @Description 卖出股票
 * @Author 岳建立
 * @Date 2022/1/3 20:59
 * @Version 1.0
 **/
@Data
@ApiModel("卖出股票Ro")
public class SellRo extends TradeRo {
    @ApiModelProperty("买入/卖出的股票代码")
    private String code;
    @ApiModelProperty("买入/卖出的股票名称")
    private String name;
    @ApiModelProperty("买入/卖出的数量")
    private Integer amount;
    @ApiModelProperty("买入/卖出的价格")
    private BigDecimal price;
    @ApiModelProperty("委托方式 1手动,0自动")
    private Integer entrustType;
    @ApiModelProperty("是否是打板卖出，即涨停卖出")
    private Integer dbBuy = 0;
}
