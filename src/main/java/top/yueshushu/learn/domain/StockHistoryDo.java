package top.yueshushu.learn.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 股票的历史交易记录表
 * </p>
 *
 * @author 岳建立 自定义的
 * @since 2022-01-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("stock_history")
public class StockHistoryDo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 股票的编码
     */
    @TableField("code")
    private String code;

    /**
     * 股票的名称
     */
    @TableField("name")
    private String name;

    /**
     * 当天的日期不包括周六周天
     */
    @TableField("curr_date")
    private LocalDateTime currDate;

    /**
     * 最低价格
     */
    @TableField("lowest_price")
    private BigDecimal lowestPrice;

    /**
     * 开盘价
     */
    @TableField("opening_price")
    private BigDecimal openingPrice;

    /**
     * 前收盘
     */
    @TableField("yesClosing_price")
    private BigDecimal yesClosingPrice;

    /**
     * 涨跌额
     */
    @TableField("amplitude")
    private BigDecimal amplitude;

    /**
     * 涨跌幅
     */
    @TableField("amplitude_proportion")
    private BigDecimal amplitudeProportion;

    /**
     * 成交量
     */
    @TableField("trading_volume")
    private BigDecimal tradingVolume;

    /**
     * 成交金额
     */
    @TableField("trading_value")
    private BigDecimal tradingValue;

    /**
     * 收盘价
     */
    @TableField("closing_price")
    private BigDecimal closingPrice;

    /**
     * 最高价格
     */
    @TableField("highest_price")
    private BigDecimal highestPrice;


    /**
     * 外盘数量
     */
    @TableField("out_dish")
    private Integer outDish;

    /**
     * 内盘数量
     */
    @TableField("inner_dish")
    private Integer innerDish;
    /**
     * 换手率
     */
    @TableField("changing_proportion")
    private BigDecimal changingProportion;
    /**
     * 量比
     */
    @TableField("than")
    private BigDecimal than;

    /**
     * 均价
     */
    @TableField("avg_price")
    private BigDecimal avgPrice;

    /**
     * 静态市盈率
     */
    @TableField("static_price_ratio")
    private BigDecimal staticPriceRatio;

    /**
     * 动态市盈率
     */
    @TableField("dynamic_price_ratio")
    private BigDecimal dynamicPriceRatio;

    /**
     * TTM 市盈率
     */
    @TableField("ttm_price_ratio")
    private BigDecimal ttmPriceRatio;

    /**
     * 买的 前五手
     */
    @TableField("buy_hand")
    private Integer buyHand;

    /**
     * 卖的 前五手
     */
    @TableField("sell_hand")
    private Integer sellHand;

    /**
     * 委比
     */
    @TableField("appoint_than")
    private String appointThan;

    /**
     * 1为正常 0为删除
     */
    @TableField("flag")
    private Integer flag;


}
