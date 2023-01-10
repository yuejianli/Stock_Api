package top.yueshushu.learn.api.request;

import lombok.Data;
import top.yueshushu.learn.enumtype.TradeMethodType;

/**
 * 提交挂单
 */
@Data
public class SubmitRequest extends BaseTradeRequest {
    /**
     * 买卖类别-买
     */
    public static final String B = "B";
    /**
     * 买卖类别-卖
     */
    public static final String S = "S";

    private String stockCode;
    private double price;
    private int amount;
    private String zqmc = "unknow";
    private String market;

    /**
     * 买卖类别
     *
     * @see #B
     * @see #S
     */
    private String tradeType;


    @Override
    public String getMethod() {
        return TradeMethodType.SubmitRequest.getCode();
    }
}
