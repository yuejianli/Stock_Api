package top.yueshushu.learn.domain;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
     * 1为正常 0为删除
     */
    @TableField("flag")
    private Integer flag;


}
