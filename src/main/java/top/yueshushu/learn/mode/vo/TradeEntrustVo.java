package top.yueshushu.learn.mode.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName:TradeEntrustVo
 * @Description TODO
 * @Author 岳建立
 * @Date 2022/1/3 21:00
 * @Version 1.0
 **/
@Data
@ApiModel("委托单展示")
public class TradeEntrustVo implements Serializable {
    @ApiModelProperty("主键")
    private Integer id;
    @ApiModelProperty("股票编号")
    private String code;
    @ApiModelProperty("股票名称")
    private String name;
    @ApiModelProperty("交易时间")
    private Date entrustDate;
    @ApiModelProperty("交易类型 1为买  2为卖")
    private Integer dealType;
    @ApiModelProperty("交易数量")
    private Integer entrustNum;
    @ApiModelProperty("交易价格")
    private BigDecimal entrustPrice;
    @ApiModelProperty("交易的状态 1 进行中 2 成交 3 撤回")
    private Integer entrustStatus;
    @ApiModelProperty("委托编号")
    private String entrustCode;
    @ApiModelProperty("可用金额")
    private BigDecimal useMoney;
    @ApiModelProperty("可取金额")
    private BigDecimal takeoutMoney;
    @ApiModelProperty("委托费用，即交易费用")
    private BigDecimal entrustMoney;
    @ApiModelProperty("手续费")
    private BigDecimal handMoney;
    @ApiModelProperty("总的金额")
    private BigDecimal totalMoney;
    @ApiModelProperty("委托方式 1 手动 0 自动")
    private Integer entrustType;
    @ApiModelProperty("类型 1为虚拟 0为正式")
    private Integer mockType;
    @ApiModelProperty("用户编号")
    private Integer userId;
    @ApiModelProperty("1正常 0 删除")
    private Integer flag;
}
