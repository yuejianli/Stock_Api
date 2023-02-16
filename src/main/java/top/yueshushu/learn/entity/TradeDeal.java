package top.yueshushu.learn.entity;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 成交表
 * </p>
 *
 * @author 两个蝴蝶飞 自定义的
 * @since 2022-01-03
 */
@Data
public class TradeDeal implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String code;
    private String name;
    private Date dealDate;
    private Integer dealType;
    private Integer dealNum;
    private BigDecimal dealPrice;
    private BigDecimal dealMoney;
    private String dealCode;
    private String entrustCode;
    private Integer entrustType;
    private Integer dbType;
    private Integer userId;
    private Integer mockType;
    private Integer flag;
}
