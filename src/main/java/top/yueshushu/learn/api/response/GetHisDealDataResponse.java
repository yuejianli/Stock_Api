package top.yueshushu.learn.api.response;

import lombok.Data;

/**
 * 历史成交响应
 */
@Data
public class GetHisDealDataResponse extends GetDealDataResponse {

    /**
     * 成交序号
     */
    private String Cjxh;
    /**
     * 成交日期
     */
    private String Cjrq;

}
