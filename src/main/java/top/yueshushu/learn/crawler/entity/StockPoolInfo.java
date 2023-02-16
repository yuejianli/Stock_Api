package top.yueshushu.learn.crawler.entity;

import lombok.Data;
import top.yueshushu.learn.enumtype.StockPoolType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 股票池接收数据
 *
 * @author yuejianli
 * @date 2023-02-10
 */
@Data
public class StockPoolInfo implements Serializable {
    private String code;
    private String name;
    private Date currDate;
    private StockPoolType stockPoolType;
    private Integer type;
    /**
     * @param nowPrice 最新价
     * @param amplitude 涨跌幅度
     */
    private BigDecimal nowPrice;
    private BigDecimal amplitude;
    /**
     * 成交金额
     */
    private BigDecimal tradingValue;

    /**
     * 流通市值
     */
    private BigDecimal floatMarket;

    /**
     * 总市值
     */
    private BigDecimal totalMarket;

    /**
     * 换手率
     */
    private BigDecimal changingProportion;


    /**
     * 封板资金
     */
    private BigDecimal sealingMoney;

    /**
     * 开始封板时间
     */
    private Date startTime;

    /**
     * 最后封板时间
     */
    private Date endTime;
    /**
     * 炸板数
     */
    private Integer zbCount;

    /**
     * 连板数
     */
    private Integer lbCount;

    /**
     * 统计数
     */
    private Integer statCount;
    /**
     * 统计天数
     */
    private Integer statDay;
    /**
     * 所属行业
     */
    private String bkName;


    /**
     针对跌停板进行的操作
     */
    /**
     * 跌了几天
     */
    private Integer days;
    /**
     * 开板次数
     */
    private Integer ocCount;

    /**
     针对 昨日涨停板进行的操作
     */

    /**
     * 今日振幅比
     */
    private BigDecimal zfProportion;

    /**
     针对 强势股进行的操作
     */

    /**
     * 新高
     */
    private Integer ng;
    /**
     * 量比
     */
    private BigDecimal lb;
    /**
     * 原因
     */
    private String reason;

    /**
     针对 次新股进行的操作
     */

    /**
     * 上市几日
     */
    private Integer ods;
    /**
     * 上市日期
     */
    private String startDate;
    /**
     * 开板日期
     */
    private String openDate;

}
