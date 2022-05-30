package top.yueshushu.learn.mode.vo.ten10stat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description 股票历史展示信息Vo
 * @Author yuejianli
 * @Date 2022/6/5 7:42
 **/
@ApiModel("股票历史展示信息Vo")
@Data
public class HistoryTen10Vo implements Serializable {
    private String currDate;
    @ApiModelProperty("类型 1赚 -1赔 0是平")
    private Integer type;
    @ApiModelProperty("涨跌比例")
    private String amplitudeProportion;
}
