package top.yueshushu.learn.crawler.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 版块资金流
 *
 * @author yuejianli
 * @date 2023-02-02
 */
@Data
public class BKMoneyInfo implements Serializable {
    private String bkCode;
    private String bkName;
    private Date currentDate;
    /**
     * 最新价
     */
    private String bkNowPrice;
    private String bkNowProportion;
    /**
     * 市场
     */
    private Integer market;

    /**
     * 今日主力净注入净额
     * 今日主力净注入净额 占比
     */
    private String todayMainInflow;
    private String todayMainInflowProportion;

    /**
     * 今日 超大净注入净额
     * 今日 超大净注入净额 占比
     */
    private String todaySuperInflow;
    private String todaySuperInflowProportion;

    /**
     * 今日 大单净注入净额
     * 今日 大单净注入净额 占比
     */
    private String todayMoreInflow;
    private String todayMoreInflowProportion;

    /**
     * 今日 中单净注入净额
     * 今日 中单净注入净额 占比
     */
    private String todayMiddleInflow;
    private String todayMiddleInflowProportion;

    /**
     * 今日 小单净注入净额
     * 今日 小单净注入净额 占比
     */
    private String todaySmallInflow;
    private String todaySmallInflowProportion;


    /**
     * 今日主力净注入股票编码
     * 今日主力净注入股票名称
     */
    private String todayMainInflowCode;
    private String todayMainInflowName;
}
