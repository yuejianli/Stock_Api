package top.yueshushu.learn.api.response;

import lombok.Data;

/**
 * 当日成交响应
 */
@Data
public class GetDealDataResponse {

    /**
     * 买卖类别-买
     */
    public static final String B = "B";
    /**
     * 买卖类别-卖
     */
    public static final String S = "S";

    private String Zqmc;
    /**
     * 委托编号
     */
    private String Wtbh;
    /**
     * 成交编号
     */
    private String Cjbh;
    /**
     * 成交价格
     */
    private String Cjjg;
    /**
     * 成交数量
     */
    private String Cjsl;
    /**
     * 证券代码
     */
    private String Zqdm;
    /**
     * 成交时间 HHmmss
     */
    private String Cjsj;
    /**
     * 委托数量
     */
    private String Wtsl;

    /**
     * 买卖类别
     *
     * @see #B
     * @see #S
     */
    private String Mmlb;
}
