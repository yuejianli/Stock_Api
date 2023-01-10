package top.yueshushu.learn.mode.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description 自选股票展示Vo
 * @Author 岳建立
 * @Date 2022/1/3 8:11
 **/
@Data
@ApiModel("股票自选展示Vo")
public class StockSelectedVo implements Serializable {
    @ApiModelProperty("id编号")
    private Integer id;
    @ApiModelProperty("股票编码")
    private String stockCode;
    @ApiModelProperty("股票名称")
    private String stockName;
    @ApiModelProperty("股票自选笔记")
    private String notes;
    /**
     * 使用@JsonFormat注解格式化日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty("添加日期")
    private Date createTime;
}
