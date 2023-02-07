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
 * 股票的每分钟实时价格
 * </p>
 *
 * @author 岳建立
 * @since 2023-02-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("stock_price_history")
public class StockPriceHistoryDo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 股票编码
     */
    @TableField("code")
    private String code;

    /**
     * 时间
     */
    @TableField("curr_time")
    private Date currTime;

    /**
     * 价格
     */
    @TableField("price")
    private BigDecimal price;


}
