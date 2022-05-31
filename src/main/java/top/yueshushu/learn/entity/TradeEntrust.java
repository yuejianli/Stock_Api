package top.yueshushu.learn.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 委托表
 * </p>
 *
 * @author 两个蝴蝶飞 自定义的
 * @since 2022-01-03
 */
@Data
public class TradeEntrust implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String code;
    private String name;
    private Date entrustDate;
    private Integer dealType;
    private Integer entrustNum;
    private BigDecimal entrustPrice;
    private Integer entrustStatus;
    private String entrustCode;
    private BigDecimal useMoney;
    private BigDecimal takeoutMoney;
    private BigDecimal entrustMoney;
    private BigDecimal handMoney;
    private BigDecimal totalMoney;
    private Integer userId;
    private Integer entrustType;
    private Integer mockType;
    private Integer flag;
}
