package top.yueshushu.learn.mode.ro;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName:RevokeRo
 * @Description 撤消单处理
 * @Author 岳建立
 * @Date 2022/1/8 16:58
 * @Version 1.0
 **/
@ApiModel("撤销委托处理")
@Data
public class RevokeRo extends TradeRo {
    @ApiModelProperty("撤销单的id信息")
    private Integer id;
    @ApiModelProperty("类型 1为手动，0为自动. 默认是手动")
    private Integer entrustType = 1;
}
