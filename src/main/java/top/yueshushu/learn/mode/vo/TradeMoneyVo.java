package top.yueshushu.learn.mode.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @ClassName:TradeMoneyVo
 * @Description TODO
 * @Author 岳建立
 * @Date 2022/1/3 18:12
 * @Version 1.0
 **/
@Data
@ApiModel("交易资产Vo")
public class TradeMoneyVo implements Serializable {
    @ApiModelProperty("主键")
    private Integer id;
    @ApiModelProperty("总金额")
    private BigDecimal totalMoney;
    @ApiModelProperty("可用金额")
    private BigDecimal useMoney;
    @ApiModelProperty("市值金额")
    private BigDecimal marketMoney;
    @ApiModelProperty("可取金额")
    private BigDecimal takeoutMoney;
    @ApiModelProperty(" 虚拟类型 1为虚拟 0为真实")
    private Integer mockType;

}
