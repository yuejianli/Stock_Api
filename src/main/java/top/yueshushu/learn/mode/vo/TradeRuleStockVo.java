package top.yueshushu.learn.mode.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName:TradeRuleStockVo
 * @Description 规则适用股票展示
 * @Author zk_yjl
 * @Date 2022/1/27 11:54
 * @Version 1.0
 * @Since 1.0
 **/
@Data
@ApiModel("股票配置规则")
public class TradeRuleStockVo implements Serializable {
    @ApiModelProperty("所有的自选股票信息")
    private List<StockSelectedVo> allList;
    @ApiModelProperty("当前适用的股票信息")
    private List<StockSelectedVo> applyList;
    @ApiModelProperty("其它适用的股票信息")
    private List<StockSelectedVo> otherApplyList;
}
