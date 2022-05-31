package top.yueshushu.learn.api.request;

import lombok.Data;
import top.yueshushu.learn.enumtype.TradeMethodType;

/**
 * 撤单
 */
@Data
public class RevokeRequest extends BaseTradeRequest {

    private String revokes;

    public RevokeRequest(int userId) {
        super(userId);
    }

    @Override
    public String getMethod() {
        return TradeMethodType.RevokeRequest.getCode();
    }

}
