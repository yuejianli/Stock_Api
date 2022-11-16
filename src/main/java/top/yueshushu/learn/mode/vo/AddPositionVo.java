package top.yueshushu.learn.mode.vo;

import lombok.Data;
import top.yueshushu.learn.entity.TradeMoney;
import top.yueshushu.learn.entity.TradePosition;

import java.io.Serializable;
import java.util.List;

/**
 * 添加持仓信息
 *
 * @author yuejianli
 * @date 2022-11-16
 */
@Data
public class AddPositionVo implements Serializable {

    private TradeMoney moneyInfo;
    /**
     * 要处理的定义持仓信息
     */
    private List<TradePosition> positionList;
    /**
     * 类型信息  0 为昨天的 1为今天的。
     */
    private Integer type;

    private String account;
}
