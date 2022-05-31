package top.yueshushu.learn.domain;

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
 * 交易规则可使用的条件表
 * </p>
 *
 * @author 两个蝴蝶飞 自定义的
 * @since 2022-01-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("trade_rule_condition")
public class TradeRuleConditionDo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 条件id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 规则编号
     */
    @TableField("code")
    private String code;

    /**
     * 规则名称
     */
    @TableField("name")
    private String name;

    /**
     * 规则描述
     */
    @TableField("description")
    private String description;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 更新日期
     */
    @TableField("update_time")
    private Date updateTime;

    /**
     * 是否删除 1为正常 0为删除
     */
    @TableField("flag")
    private Integer flag;


}
