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
 * 版块资金注入历史
 * </p>
 *
 * @author 岳建立
 * @since 2023-02-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("stock_bk_money_history")
public class StockBkMoneyHistoryDo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 版块编号
     */
    @TableField("bk_code")
    private String bkCode;

    /**
     * 版块名称
     */
    @TableField("bk_name")
    private String bkName;

    /**
     * 当前日期
     */
    @TableField("curr_date")
    private Date currentDate;

    /**
     * 最新价
     */
    @TableField("bk_now_price")
    private BigDecimal bkNowPrice;

    /**
     * 版块涨跌比例
     */
    @TableField("bk_now_proportion")
    private String bkNowProportion;

    /**
     * 市场
     */
    @TableField("market")
    private String market;

    /**
     * 今日主力净注入净额
     */
    @TableField("today_main_inflow")
    private String todayMainInflow;

    /**
     * 今日主力净注入净额 占比
     */
    @TableField("today_main_inflow_proportion")
    private String todayMainInflowProportion;

    /**
     * 今日 超大净注入净额
     */
    @TableField("today_super_inflow")
    private String todaySuperInflow;

    /**
     * 今日 超大净注入净额 占比
     */
    @TableField("today_super_inflow_proportion")
    private String todaySuperInflowProportion;

    /**
     * 今日 大单净注入净额
     */
    @TableField("today_more_inflow")
    private String todayMoreInflow;

    /**
     * 今日 大单净注入净额 占比
     */
    @TableField("today_more_inflow_proportion")
    private String todayMoreInflowProportion;

    /**
     * 今日 中单净注入净额
     */
    @TableField("today_middle_inflow")
    private String todayMiddleInflow;

    /**
     * 今日 中单净注入净额 占比
     */
    @TableField("today_middle_inflow_proportion")
    private String todayMiddleInflowProportion;

    /**
     * 今日 小单净注入净额
     */
    @TableField("today_small_inflow")
    private String todaySmallInflow;

    /**
     * 今日 小单净注入净额 占比
     */
    @TableField("today_small_inflow_proportion")
    private String todaySmallInflowProportion;

    /**
     * 今日主力净注入股票编码
     */
    @TableField("today_main_inflow_code")
    private String todayMainInflowCode;

    /**
     * 今日主力净注入股票名称
     */
    @TableField("today_main_inflow_name")
    private String todayMainInflowName;


}
