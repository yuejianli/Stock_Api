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
 *
 * </p>
 *
 * @author 岳建立
 * @since 2023-02-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("stock_pool_history")
public class StockPoolHistoryDo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 当时日期
     */
    @TableField("curr_date")
    private Date currDate;

    /**
     * 股票编码
     */
    @TableField("code")
    private String code;

    /**
     * 股票名称
     */
    @TableField("name")
    private String name;
    /**
     * 类型
     */
    @TableField("type")
    private Integer type;

    /**
     * 涨跌幅度
     */
    @TableField("amplitude")
    private BigDecimal amplitude;


}
