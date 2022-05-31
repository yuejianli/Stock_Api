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
 * 交易规则表
 * </p>
 *
 * @author 两个蝴蝶飞 自定义的
 * @since 2022-01-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("trade_rule")
public class TradeRuleDo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 规则的名称
     */
    @TableField("name")
    private String name;

    /**
     * 规则条件编号
     */
    @TableField("condition_code")
    private String conditionCode;

    /**
     * 规则计算 1为小于 2为大于
     */
    @TableField("condition_type")
    private Integer conditionType;

    /**
     * 比较类型 1为金额 2为比例
     */
    @TableField("rule_value_type")
    private Integer ruleValueType;

    /**
     * 规则对应值
     */
    @TableField("rule_value")
    private BigDecimal ruleValue;

    /**
     * 交易股票数
     */
    @TableField("trade_num")
    private Integer tradeNum;

    /**
     * 交易值类型 1为金额 2为比例
     */
    @TableField("trade_value_type")
    private Integer tradeValueType;

    /**
     * 交易差值
     */
    @TableField("trade_price")
    private BigDecimal tradePrice;

    /**
     * 规则类型 1为买入 2为卖出
     */
    @TableField("rule_type")
    private Integer ruleType;

    /**
     * 状态 1为正常 0为禁用
     */
    @TableField("status")
    private Integer status;

    /**
     * 所属用户
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 1为模拟0为实际
     */
    @TableField("mock_type")
    private Integer mockType;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField("update_time")
    private Date updateTime;

    /**
     * 类型 1为正常 0为删除
     */
    @TableField("flag")
    private Integer flag;


}
