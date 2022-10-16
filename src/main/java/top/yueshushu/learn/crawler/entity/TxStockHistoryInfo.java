package top.yueshushu.learn.crawler.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Description 腾讯股票历史记录信息
 * @Author yuejianli
 * @Date 2022/10/16 9:14
 **/
@Data
public class TxStockHistoryInfo implements Serializable {
    /**
     * 当前天
     */
    private String currDate;

    /**
     * 股票的代码
     */
    private String code;
    /**
     * 股票的名称
     */
    private String name;

    /**
     * 收盘价
     */
    private BigDecimal closingPrice;


    /**
     * 最高价格
     */
    private BigDecimal highestPrice;
    /**
     * 最低价格
     */
    private BigDecimal lowestPrice;
    /**
     * 开盘价
     */
    private BigDecimal openingPrice;

    /**
     * 昨天的收盘价
     */
    private BigDecimal yesClosingPrice;

    /**
     * 涨跌幅度
     */
    private BigDecimal amplitude;

    /**
     * 涨跌幅度百分比
     */
    private BigDecimal amplitudeProportion;
    /**
     * 成交量(股)
     */
    private long tradingVolume;
    /**
     * 成交量金额
     */
    private BigDecimal tradingValue;

    /**
     * 外盘数量
     */
    private Integer outDish;

    /**
     * 内盘数量
     */
    private Integer innerDish;
    /**
     * 换手率
     */
    private BigDecimal changingProportion;
    /**
     * 量比
     */
    private BigDecimal than;

    /**
     * 均价
     */
    private BigDecimal avgPrice;

    /**
     * 静态市盈率
     */
    private BigDecimal staticPriceRatio;

    /**
     * 动态市盈率
     */
    private BigDecimal dynamicPriceRatio;

    /**
     * TTM 市盈率
     */
    private BigDecimal ttmPriceRatio;

    /**
     * 买的 前五手
     */
    private Integer buyHand;

    /**
     * 卖的 前五手
     */
    private Integer sellHand;

    /**
     * 委比
     */
    private String appointThan;
}
