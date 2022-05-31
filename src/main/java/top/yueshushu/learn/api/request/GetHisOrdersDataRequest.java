package top.yueshushu.learn.api.request;

import lombok.Data;
import top.yueshushu.learn.enumtype.TradeMethodType;

/**
 * 历史委托
 */
@Data
public class GetHisOrdersDataRequest extends BaseTradeRequest {

    private String st;
    private String et;

    public GetHisOrdersDataRequest(int userId) {
        super(userId);
    }
    @Override
    public String getMethod() {
        return TradeMethodType.GetHisOrdersDataRequest.getCode();
    }
}
