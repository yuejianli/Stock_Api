package top.yueshushu.learn.mode.vo.ten10stat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description 查询近十天的交易信息记录VO
 * @Author yuejianli
 * @Date 2022/6/5 7:35
 **/
@Data
@ApiModel("股票最近十天交易信息Vo")
public class StockTen10Vo implements Serializable {
    @ApiModelProperty("股票编码")
    private String code;
    @ApiModelProperty("股票名称")
    private String name;
    @ApiModelProperty("涨跌信息")
    private List<HistoryTen10Vo> ten10List;
}
