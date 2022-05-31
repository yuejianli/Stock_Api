package top.yueshushu.learn.domain;

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
 * 成交表
 * </p>
 *
 * @author 两个蝴蝶飞 自定义的
 * @since 2022-01-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("trade_deal")
public class TradeDealDo implements Serializable {

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
     * 名称
     */
    @TableField("name")
    private String name;

    /**
     * 成交时间
     */
    @TableField("deal_date")
    private Date dealDate;

    /**
     * 成交类型 1为买 2为卖
     */
    @TableField("deal_type")
    private Integer dealType;

    /**
     * 成交数量
     */
    @TableField("deal_num")
    private Integer dealNum;

    /**
     * 成交价格
     */
    @TableField("deal_price")
    private BigDecimal dealPrice;

    /**
     * 成交金额
     */
    @TableField("deal_money")
    private BigDecimal dealMoney;

    /**
     * 成交编号
     */
    @TableField("deal_code")
    private String dealCode;

    /**
     * 委托编号
     */
    @TableField("entrust_code")
    private String entrustCode;

    /**
     * 成交方式 1 手动 0 自动
     */
    @TableField("entrust_type")
    private Integer entrustType;

    /**
     * 关联用户
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 类型 1为虚拟 0为正式
     */
    @TableField("mock_type")
    private Integer mockType;

    /**
     * 类型 1为正常 0为删除
     */
    @TableField("flag")
    private Integer flag;


}
