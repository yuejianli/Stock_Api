package top.yueshushu.learn.api.response;

import lombok.Data;

/**
 * 当日委托响应
 */
@Data
public class GetOrdersDataResponse {

    public static final String YIBAO = "已报";
    public static final String YICHENG = "已成";
    public static final String YICHE = "已撤";

    /**
     * 买卖类别-买
     */
    public static final String B = "B";
    /**
     * 买卖类别-卖
     */
    public static final String S = "S";

    /**
     * 证券名称
     */
    private String Zqmc;
    /**
     * 委托编号
     */
    private String Wtbh;
    /**
     * 委托时间
     * HHmmss
     */
    private String Wtsj;
    /**
     * 证券代码
     */
    private String Zqdm;
    /**
     * 委托数量
     */
    private String Wtsl;
    /**
     * 委托价格
     */
    private String Wtjg;
    /**
     * 委托状态
     *
     * @see #YIBAO
     * @see #YICHENG
     * @see #YICHE
     */
    private String Wtzt;
    /**
     * 买卖类别
     *
     * @see #B
     * @see #S
     */
    private String Mmlb;
}
