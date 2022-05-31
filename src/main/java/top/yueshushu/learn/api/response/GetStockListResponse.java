package top.yueshushu.learn.api.response;

import lombok.Data;

/**
 * 我的持仓响应
 */
@Data
public class GetStockListResponse {
    private String Zqmc;
    private String Zqdm;
    private String Zqsl;
    private String Zxjg;
    private String Kysl;
    private String Cbjg;
    private String Ljyk;
}
