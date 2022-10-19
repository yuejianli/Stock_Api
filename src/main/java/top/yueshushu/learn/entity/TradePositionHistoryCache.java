package top.yueshushu.learn.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 我的持仓表
 * </p>
 *
 * @author 两个蝴蝶飞 自定义的
 * @since 2022-01-03
 */
@Data
public class TradePositionHistoryCache implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String code;
    private String name;
    private Integer allAmount;
    private Integer useAmount;
    private BigDecimal avgPrice;
    private BigDecimal price;
    private BigDecimal allMoney;
    private BigDecimal floatMoney;
    private BigDecimal floatProportion;
    private Integer userId;
    private Integer mockType;
}
