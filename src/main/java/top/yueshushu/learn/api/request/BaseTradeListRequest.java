package top.yueshushu.learn.api.request;

import java.util.List;

/**
 * 基本交易的请求信息
 */
public abstract class BaseTradeListRequest extends BaseTradeRequest {
    public BaseTradeListRequest(){

    }
    public BaseTradeListRequest(int userId) {
        super(userId);
    }

    public abstract List<?> getList();

}
