package top.yueshushu.learn.mode.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @ClassName:TradePositionVo
 * @Description TODO
 * @Author 岳建立
 * @Date 2022/1/3 20:41
 * @Version 1.0
 **/
@Data
@ApiModel("持仓信息展示")
public class TradePositionVo implements Serializable {
    @ApiModelProperty("主键自增")
    private Integer id;
    @ApiModelProperty("股票编号")
    private String code;
    @ApiModelProperty("股票的名称")
    private String name;
    @ApiModelProperty("总数量")
    private Integer allAmount;
    @ApiModelProperty("可用数量")
    private Integer useAmount;
    @ApiModelProperty("成本价")
    private BigDecimal avgPrice;
    @ApiModelProperty("当前价")
    private BigDecimal price;
    @ApiModelProperty("总的市值")
    private BigDecimal allMoney;
    @ApiModelProperty("浮动盈亏")
    private BigDecimal floatMoney;
    @ApiModelProperty("盈亏比例")
    private BigDecimal floatProportion;
    @ApiModelProperty("类型,1为虚拟 0为正式")
    private Integer mockType;
    @ApiModelProperty("类型 1为持仓 2为自选")
    private Integer selectType;
}
