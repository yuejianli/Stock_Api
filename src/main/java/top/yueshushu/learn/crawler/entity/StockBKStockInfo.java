package top.yueshushu.learn.crawler.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 股票与版块同步处理
 *
 * @author yuejianli
 * @date 2023-02-09
 */
@Data
public class StockBKStockInfo implements Serializable {
    /**
     * 股票编码
     * 版块编码
     * 版块名称
     * 幅度
     */
    private String stockCode;
    private String bkCode;
    private String bkName;
    private Integer amplitude;
}
