package top.yueshushu.learn.mode.ro;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @ClassName:TradeEntrustRo
 * @Description TODO
 * @Author 岳建立
 * @Date 2022/1/3 20:59
 * @Version 1.0
 **/
@Data
@ApiModel("委托信息Ro")
public class TradeEntrustRo extends TradeRo {
    @ApiModelProperty("开始时间")
    private String startDate;
    @ApiModelProperty("状态集合")
    private List<Integer> statusList;
}
