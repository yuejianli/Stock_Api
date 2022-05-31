package top.yueshushu.learn.api.request;

import lombok.Data;
import top.yueshushu.learn.enumtype.TradeMethodType;

/**
 * 当日成交
 */
@Data
public class GetDealDataRequest extends BaseTradeRequest {

    public GetDealDataRequest(int userId) {
        super(userId);
    }

    @Override
    public String getMethod() {
        return TradeMethodType.GetDealDataRequest.getCode();
    }
}
