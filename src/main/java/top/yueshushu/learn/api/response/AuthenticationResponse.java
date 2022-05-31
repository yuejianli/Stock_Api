package top.yueshushu.learn.api.response;

import lombok.Data;

/**
 * 登录验证的响应
 */
@Data
public class AuthenticationResponse {
    /**
     * cookie cookie
     * validateKey  验证key
     */
    private String cookie;
    private String validateKey;
}
