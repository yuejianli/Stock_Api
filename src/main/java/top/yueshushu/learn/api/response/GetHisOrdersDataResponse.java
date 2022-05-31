package top.yueshushu.learn.api.response;

import lombok.Data;

/**
 * 历史委托的响应
 */
@Data
public class GetHisOrdersDataResponse extends GetOrdersDataResponse {

    /**
     * 委托日期
     */
    private String Wtrq;
}
