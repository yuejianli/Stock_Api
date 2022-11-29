package top.yueshushu.learn.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 股票的大单交易记录
 * </p>
 *
 * @author 岳建立 自定义的
 * @since 2022-01-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("stock_big_deal")
public class StockBigDealDo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 股票的全编码
     */
    @TableField("full_code")
    private String fullCode;

    /**
     * 股票的名称
     */
    @TableField("name")
    private String name;

    /**
     * 当天的日期不包括周六周天
     */
    @TableField("curr_date")
    private Date currDate;

    /**
     * 成交时间
     */
    @TableField("tick_time")
    private String tickTime;

    /**
     * 当前价格
     */
    @TableField("price")
    private BigDecimal price;

    /**
     * 上一个价格
     */
    @TableField("prev_price")
    private BigDecimal prevPrice;

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
     * 种类
     */
    @TableField("kind")
    private Integer kind;


}
