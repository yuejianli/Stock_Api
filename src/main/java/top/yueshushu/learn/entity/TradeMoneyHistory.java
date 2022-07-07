package top.yueshushu.learn.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * <p>
 * 资金表
 * </p>
 *
 * @author 两个蝴蝶飞 自定义的
 * @since 2022-01-03
 */
@Data
public class TradeMoneyHistory implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private LocalDateTime currDate;
	
	private BigDecimal totalMoney;
	
	private BigDecimal useMoney;
	
	private BigDecimal marketMoney;
	private BigDecimal takeoutMoney;
	private Integer userId;
	private Integer mockType;
	
	
}
