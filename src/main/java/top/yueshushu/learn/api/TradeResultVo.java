package top.yueshushu.learn.api;

import lombok.Data;

import java.util.List;

/**
 * 交易结果响应信息
 * @param <T>
 */
@Data
public class TradeResultVo<T> {

    public static final int STATUS_SUCCESS = 0;

    private String Message;
    private int Status;
    private List<T> Data;
    private Boolean success;

    public Boolean getSuccess() {
        return Status == TradeResultVo.STATUS_SUCCESS;
    }
}
