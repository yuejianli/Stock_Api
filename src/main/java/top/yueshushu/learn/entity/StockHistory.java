package top.yueshushu.learn.entity;

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
public class StockHistory implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String code;
    private String name;
    private LocalDateTime currDate;
    private BigDecimal lowestPrice;
    private BigDecimal openingPrice;
    private BigDecimal yesClosingPrice;
    private BigDecimal amplitude;
    private BigDecimal amplitudeProportion;
    private BigDecimal tradingVolume;
    private BigDecimal tradingValue;
    private BigDecimal closingPrice;
    private BigDecimal highestPrice;
    private Integer flag;
}
