package top.yueshushu.learn.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 资金表
 * </p>
 *
 * @author 两个蝴蝶飞 自定义的
 * @since 2022-01-03
 */
@Data
public class TradeMoney implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private BigDecimal totalMoney;

    private BigDecimal useMoney;

    private BigDecimal marketMoney;
    private BigDecimal takeoutMoney;
    private BigDecimal profitMoney;
    private Integer userId;
    private Integer mockType;


}
