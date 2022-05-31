package top.yueshushu.learn.mode.ro;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName:RevokeRo
 * @Description TODO
 * @Author 岳建立
 * @Date 2022/1/8 16:58
 * @Version 1.0
 **/
@ApiModel("撤销委托处理")
@Data
public class RevokeRo extends TradeRo {
    @ApiModelProperty("撤销单的id信息")
    private Integer id;
}
