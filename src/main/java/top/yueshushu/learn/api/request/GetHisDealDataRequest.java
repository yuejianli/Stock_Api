package top.yueshushu.learn.api.request;

import lombok.Data;
import top.yueshushu.learn.enumtype.TradeMethodType;

/**
 * 历史成交
 */
@Data
public class GetHisDealDataRequest extends BaseTradeRequest {

    private String st;
    private String et;

    public GetHisDealDataRequest(int userId) {
        super(userId);
    }

    @Override
    public String getMethod() {
        return TradeMethodType.GetHisDealDataRequest.getCode();
    }
}
