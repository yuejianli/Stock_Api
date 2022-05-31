package top.yueshushu.learn.mode.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 股票的历史交易记录表
 * </p>
 *
 * @author 岳建立 自定义的
 * @since 2022-01-02
 */
@Data
public class StockHistoryVo implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String code;
    private String name;
    /** 使用@JsonFormat注解格式化日期 */
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
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
