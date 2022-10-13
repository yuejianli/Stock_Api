package top.yueshushu.learn.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import top.yueshushu.learn.common.Const;
import top.yueshushu.learn.util.RedisUtil;

public abstract class BaseController {
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 根据请求头获取对应的token 信息
     *
     * @return 设置当前的用户信息
     */
    protected Integer getUserId() {
        Object redisCacheUserId = redisUtil.get(Const.KEY_AUTH_USER_ID);

        if (ObjectUtils.isEmpty(redisCacheUserId)) {
            redisUtil.set(Const.KEY_AUTH_USER_ID, 1);
            return 1;
        }
        try {
            return Integer.parseInt(redisCacheUserId.toString());
        } catch (Exception e) {
            redisUtil.set(Const.KEY_AUTH_USER_ID, 1);
            return 1;
        }
    }
}
