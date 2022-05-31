package top.yueshushu.learn.service.cache;

import top.yueshushu.learn.entity.User;

/**
 * @Description 用户相关的缓存信息
 * @Author yuejianli
 * @Date 2022/5/20 23:36
 **/
public interface UserCacheService {
    /**
     * 刷新用户的token 信息
     * @param user 当前的用户对象
     * @param oldToken 以前的老Token
     */
    void refreshToken(User user, String oldToken);
}
