package top.yueshushu.learn.api.request;

import lombok.Data;
import top.yueshushu.learn.enumtype.TradeMethodType;

/**
 * 查询可申购新股列表
 */
@Data
public class GetCanBuyNewStockListV3Request extends BaseTradeRequest {

    public GetCanBuyNewStockListV3Request(int userId) {
        super(userId);
    }

    @Override
    public String getMethod() {
        return TradeMethodType.GetCanBuyNewStockListV3.getCode();
    }

}
