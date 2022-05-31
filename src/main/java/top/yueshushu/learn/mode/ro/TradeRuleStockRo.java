package top.yueshushu.learn.mode.ro;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import top.yueshushu.learn.response.PageRo;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName:TradeRuleStockRo
 * @Description TODO
 * @Author zk_yjl
 * @Date 2022/1/27 10:47
 * @Version 1.0
 * @Since 1.0
 **/
@Data
@ApiModel("规则配置股票信息Ro")
public class TradeRuleStockRo  extends TradeRo implements Serializable {
    @ApiModelProperty("规则的id")
    private Integer id;
    @ApiModelProperty("应用的股票代码集合")
    private List<String> applyCodeList;
    @ApiModelProperty("去除的股票代码集合")
    private List<String> removeCodeList;
    @ApiModelProperty("查询关键字")
    private String keyword;
}
