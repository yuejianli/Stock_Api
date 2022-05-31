package top.yueshushu.learn.api.request;

import lombok.Data;
import top.yueshushu.learn.enumtype.TradeMethodType;

/**
 * 查询可申购新债列表
 */
@Data
public class GetConvertibleBondListV2Request extends BaseTradeRequest {

    public GetConvertibleBondListV2Request(int userId) {
        super(userId);
    }

    @Override
    public String getMethod() {
        return TradeMethodType.GetConvertibleBondListV2.getCode();
    }

}
