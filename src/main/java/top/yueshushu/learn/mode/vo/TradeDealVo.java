package top.yueshushu.learn.mode.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName:TradeDealVo
 * @Description TODO
 * @Author 岳建立
 * @Date 2022/1/3 20:41
 * @Version 1.0
 **/
@Data
@ApiModel("今日成交信息展示")
public class TradeDealVo implements Serializable {
    @ApiModelProperty("主键")
    private Integer id;
    @ApiModelProperty("股票编号")
    private String code;
    @ApiModelProperty("名称")
    private String name;
    /**
     * 使用@JsonFormat注解格式化日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty("成交时间")
    private Date dealDate;
    @ApiModelProperty("成交类型 1为买 2为卖")
    private Integer dealType;
    @ApiModelProperty("成交数量")
    private Integer dealNum;
    @ApiModelProperty("成交价格")
    private BigDecimal dealPrice;
    @ApiModelProperty("成交金额")
    private BigDecimal dealMoney;
    @ApiModelProperty("成交编号")
    private String dealCode;
    @ApiModelProperty("委托编号")
    private String entrustCode;
    @ApiModelProperty("委托方式 1 手动 0 自动")
    private Integer entrustType;
    @ApiModelProperty("关联用户")
    private Integer userId;
    @ApiModelProperty("类型 1为虚拟 0为正式")
    private Integer mockType;
    @ApiModelProperty("类型 1为正常 0为删除")
    private Integer flag;
}
