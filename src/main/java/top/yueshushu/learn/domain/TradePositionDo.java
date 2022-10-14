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
 * 我的持仓表
 * </p>
 *
 * @author 两个蝴蝶飞 自定义的
 * @since 2022-01-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("trade_position")
public class TradePositionDo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 股票编号
     */
    @TableField("code")
    private String code;

    /**
     * 股票的名称
     */
    @TableField("name")
    private String name;

    /**
     * 总数量
     */
    @TableField("all_amount")
    private Integer allAmount;

    /**
     * 可用数量
     */
    @TableField("use_amount")
    private Integer useAmount;

    /**
     * 成本价
     */
    @TableField("avg_price")
    private BigDecimal avgPrice;

    /**
     * 当前价
     */
    @TableField("price")
    private BigDecimal price;

    /**
     * 总的市值
     */
    @TableField("all_money")
    private BigDecimal allMoney;

    /**
     * 浮动盈亏
     */
    @TableField("float_money")
    private BigDecimal floatMoney;

    /**
     * 盈亏比例
     */
    @TableField("float_proportion")
    private BigDecimal floatProportion;

    /**
     * 盈亏金额
     */
    @TableField("today_money")
    private BigDecimal todayMoney;

    /**
     * 员工编号
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 类型,1为虚拟 0为正式
     */
    @TableField("mock_type")
    private Integer mockType;


}
