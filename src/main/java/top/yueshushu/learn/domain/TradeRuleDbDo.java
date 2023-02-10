package top.yueshushu.learn.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 打板交易规则配置
 * </p>
 *
 * @author 岳建立
 * @since 2023-02-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("trade_rule_db")
public class TradeRuleDbDo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Integer id;

    /**
     * 名称
     */
    @TableField("name")
    private String name;

    /**
     * 打板的股票类型
     */
    @TableField("code_type")
    private Integer codeType;

    /**
     * 每日最多买入次数
     */
    @TableField("buy_num")
    private Integer buyNum;

    /**
     * 买入的参数配置信息
     */
    @TableField("buy_param")
    private String buyParam;

    /**
     * 已打板买入的股票列表
     */
    @TableField("buy_stock_code")
    private String buyStockCode;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;

    /**
     * 用户编号
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 1为模拟0为实际
     */
    @TableField("mock_type")
    private Integer mockType;

    /**
     * 类型 1为正常 0为删除
     */
    @TableField("flag")
    private Integer flag;


}
