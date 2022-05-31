package top.yueshushu.learn.mode.ro;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import top.yueshushu.learn.response.PageRo;

import java.io.Serializable;

/**
 * @ClassName:StockSelectedRo
 * @Description TODO
 * @Author 岳建立
 * @Date 2022/1/3 7:53
 * @Version 1.0
 **/
@Data
@ApiModel("股票自选Ro")
public class StockSelectedRo extends PageRo implements Serializable {
    @ApiModelProperty("股票编号")
    private String stockCode;
    @ApiModelProperty("用户Id")
    private Integer userId;
    @ApiModelProperty("搜索的关键字")
    private String keyword;
    @ApiModelProperty("记录id")
    private Integer id;
    @ApiModelProperty("笔记")
    private String notes;
}
