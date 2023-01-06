package top.yueshushu.learn.api.request;

import lombok.Data;

/**
 * 抽象类请求
 */
@Data
public abstract class BaseTradeRequest {
    //这个userId 是关联的登录用户id
    // 返回格式类型
    // { Data: [] }
    public static final int VERSION_DATA_LIST = 0;
    // { Data: {} }
    public static final int VERSION_DATA_OBJ = 1;
    // msg...
    public static final int VERSION_MSG = 2;
    // { }
    public static final int VERSION_OBJ = 3;
    private int userId;

    public BaseTradeRequest() {

    }

    public BaseTradeRequest(int userId) {
        this.userId = userId;
    }

    public abstract String getMethod();

    public int responseVersion() {
        return VERSION_DATA_LIST;
    }
}
