package top.yueshushu.learn.mode.ro;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName:DealRo
 * @Description TODO
 * @Author 岳建立
 * @Date 2022/1/8 16:58
 * @Version 1.0
 **/
@ApiModel("成交委托处理")
@Data
public class DealRo extends TradeRo {
    @ApiModelProperty("成交单的id信息")
    private Integer id;
    @ApiModelProperty("委托方式 1手动,0自动")
    private Integer entrustType;
}
