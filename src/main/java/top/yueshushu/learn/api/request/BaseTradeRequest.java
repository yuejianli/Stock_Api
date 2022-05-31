package top.yueshushu.learn.api.request;

import lombok.Data;

/**
 * 抽象类请求
 */
@Data
public abstract class BaseTradeRequest {
    //这个userId 是关联的登录用户id
    private int userId;
    public BaseTradeRequest(){

    }
    public BaseTradeRequest(int userId) {
        this.userId = userId;
    }
    public abstract String getMethod();
}
