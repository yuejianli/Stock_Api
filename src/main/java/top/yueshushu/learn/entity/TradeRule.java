package top.yueshushu.learn.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 交易规则表
 * </p>
 *
 * @author 两个蝴蝶飞 自定义的
 * @since 2022-01-26
 */
@Data
public class TradeRule implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private String conditionCode;
    private Integer conditionType;
    private Integer ruleValueType;
    private BigDecimal ruleValue;
    private Integer tradeNum;
    private Integer tradeValueType;
    private BigDecimal tradePrice;
    private Integer ruleType;
    private Integer status;
    private Integer userId;
    private Integer mockType;
    private Date createTime;
    private Date updateTime;
    private Integer flag;
}
