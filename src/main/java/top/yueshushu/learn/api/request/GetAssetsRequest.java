package top.yueshushu.learn.api.request;

import lombok.Data;
import top.yueshushu.learn.enumtype.TradeMethodType;

/**
 * 获取资产的请求信息
 */
@Data
public class GetAssetsRequest extends BaseTradeRequest {

    public GetAssetsRequest(int userId) {
        super(userId);
    }
    @Override
    public String getMethod() {
        return TradeMethodType.GetAssertsRequest.getCode();
    }

}
