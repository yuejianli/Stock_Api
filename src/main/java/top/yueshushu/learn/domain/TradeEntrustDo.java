package top.yueshushu.learn.domain;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 委托表
 * </p>
 *
 * @author 两个蝴蝶飞 自定义的
 * @since 2022-01-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("trade_entrust")
public class TradeEntrustDo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 股票编号
     */
    @TableField("code")
    private String code;

    /**
     * 股票名称
     */
    @TableField("name")
    private String name;

    /**
     * 交易时间
     */
    @TableField("entrust_date")
    private Date entrustDate;

    /**
     * 交易类型 1为买  2为卖
     */
    @TableField("deal_type")
    private Integer dealType;

    /**
     * 交易数量
     */
    @TableField("entrust_num")
    private Integer entrustNum;

    /**
     * 交易价格
     */
    @TableField("entrust_price")
    private BigDecimal entrustPrice;

    /**
     * 交易的状态 1 进行中 2 成交 3 撤回
     */
    @TableField("entrust_status")
    private Integer entrustStatus;

    /**
     * 委托编号
     */
    @TableField("entrust_code")
    private String entrustCode;

    /**
     * 可用金额
     */
    @TableField("use_money")
    private BigDecimal useMoney;
    /**
     * 可取金额
     */
    @TableField("takeout_money")
    private BigDecimal takeoutMoney;

    /**
     * 委托费用，即交易费用
     */
    @TableField("entrust_money")
    private BigDecimal entrustMoney;
    /**
     * 手续费
     */
    @TableField("hand_money")
    private BigDecimal handMoney;

    /**
     * 总的金额
     */
    @TableField("total_money")
    private BigDecimal totalMoney;
    /**
     * 关联用户
     */
    @TableField("user_id")
    private Integer userId;
    /**
     * 委托方式 1 手动 0 自动
     */
    @TableField("entrust_type")
    private Integer entrustType;

    /**
     * 类型 1为虚拟 0为正式
     */
    @TableField("mock_type")
    private Integer mockType;

    /**
     * 1正常 0 删除
     */
    @TableField("flag")
    private Integer flag;


}
