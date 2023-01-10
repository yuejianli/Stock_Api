package top.yueshushu.learn.api.response;

import lombok.Data;

/**
 * 我的持仓响应
 */
@Data
public class GetStockListResponse extends BaseTradeResponse {
    /**
     * 股票名称
     */
    private String Zqmc;
    /**
     * 股票编码
     */
    private String Zqdm;
    /**
     * 持仓数量
     */
    private String Zqsl;
    /**
     * 当前价
     */
    private String Zxjg;
    /**
     * 当前可用数量
     */
    private String Kysl;
    /**
     * 成本价
     */
    private String Cbjg;
    /**
     * 浮动盈亏
     */
    private String Ljyk;
    /**
     * 盈亏比例(%)
     */
    private String Ykbl;
    /**
     * 最新市值
     */
    private String Zxsz;

    /**
     * 当日盈亏
     */
    private String Dryk;
}
