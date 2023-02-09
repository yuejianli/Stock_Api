package top.yueshushu.learn.crawler.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 打版的股票处理
 *
 * @author yuejianli
 * @date 2023-02-09
 */
@Data
public class DBStockInfo implements Serializable {
    /**
     * 股票编码
     * 名称
     * 幅度
     * 当前价格
     * 涨停价格
     */
    private String code;
    private String name;
    private Integer amplitude;
    private Integer nowPrice;
    private Integer limitPrice;
}
