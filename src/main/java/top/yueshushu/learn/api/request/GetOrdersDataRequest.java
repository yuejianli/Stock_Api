package top.yueshushu.learn.api.request;

import lombok.Data;
import top.yueshushu.learn.enumtype.TradeMethodType;

/**
 * 当日委托
 */
@Data
public class GetOrdersDataRequest extends BaseTradeRequest {

    public GetOrdersDataRequest(int userId) {
        super(userId);
    }

    @Override
    public String getMethod() {
        return TradeMethodType.GetOrdersDataRequest.getCode();
    }

}
