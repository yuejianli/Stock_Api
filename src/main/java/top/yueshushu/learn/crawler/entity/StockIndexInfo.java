package top.yueshushu.learn.crawler.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 股票指数信息
 *
 * @author yuejianli
 * @date 2023-02-21
 */
@Data
public class StockIndexInfo implements Serializable {
    private String code;
    private String name;
    /**
     * 最新价
     * 涨幅度
     * 差价
     */
    private String nowPrice;
    private String nowProportion;
    private String subPrice;

    /**
     * 类型  1为上证 2为泸深  3为 创业板  4. 沪深300
     */
    private Integer type;
}
