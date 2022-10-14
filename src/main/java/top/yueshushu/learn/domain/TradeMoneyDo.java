package top.yueshushu.learn.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@EqualsAndHashCode(callSuper = false)
@TableName("trade_money")
public class TradeMoneyDo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 总金额
     */
    @TableField("total_money")
    private BigDecimal totalMoney;

    /**
     * 可用金额
     */
    @TableField("use_money")
    private BigDecimal useMoney;

    /**
     * 市值金额
     */
    @TableField("market_money")
    private BigDecimal marketMoney;

    /**
     * 可取金额
     */
    @TableField("takeout_money")
    private BigDecimal takeoutMoney;

    /**
     * 盈亏金额
     */
    @TableField("profit_money")
    private BigDecimal profitMoney;

    /**
     * 关联用户
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 虚拟类型 1为虚拟 0为真实
     */
    @TableField("mock_type")
    private Integer mockType;


}
