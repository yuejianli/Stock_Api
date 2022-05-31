package top.yueshushu.learn.api.request;

import lombok.Data;
import top.yueshushu.learn.enumtype.TradeMethodType;

/**
 * 登录验证的请求
 */
@Data
public class AuthenticationRequest extends BaseTradeRequest {
    /**
     * @param password 密码
     * @param randNumber 随机数
     * @param identifyCode 验证码
     * @param duration 方向
     * @param type 类型 Z
     */
    private String password;
    private String randNumber = "0.9033461201665647";
    private String identifyCode;
    private String duration = "1800";
    private String type = "Z";

    public AuthenticationRequest(){
        this(1);
    }
    public AuthenticationRequest(int userId) {
        super(userId);
    }
    @Override
    public String getMethod() {
        return TradeMethodType.AuthenticationRequest.getCode();
    }
}
