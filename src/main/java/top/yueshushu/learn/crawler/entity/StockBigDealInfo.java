package top.yueshushu.learn.crawler.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 股票大交易信息
 *
 * @author yuejianli
 * @date 2022-11-28
 * <p>
 * {
 * "symbol": "sz001322",
 * "name": "箭牌家居",
 * "ticktime": "15:00:00",
 * "price": "15.390",
 * "volume": "354400",
 * "prev_price": "15.430",
 * "kind": "D"
 * }
 */
@Data
public class StockBigDealInfo implements Serializable {
    private String symbol;
    private String name;
    private String ticktime;
    private String price;
    private String volume;
    private String prev_price;
    private String kind;
}
