package top.yueshushu.learn.mode.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @ClassName:StockPriceCacheDto
 * @Description TODO
 * @Author 岳建立
 * @Date 2022/1/8 9:53
 * @Version 1.0
 **/
@ApiModel("股票存储于缓存里面的信息")
@Data
public class StockPriceCacheDto implements Serializable {
    @ApiModelProperty("股票的编码")
    private String code;
    @ApiModelProperty("股票当前对应的价格")
    private BigDecimal price;
}
