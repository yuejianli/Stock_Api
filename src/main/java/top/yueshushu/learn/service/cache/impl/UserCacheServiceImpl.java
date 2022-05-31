package top.yueshushu.learn.service.cache.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import top.yueshushu.learn.common.Const;
import top.yueshushu.learn.entity.User;
import top.yueshushu.learn.service.cache.UserCacheService;
import top.yueshushu.learn.util.RedisUtil;

import javax.annotation.Resource;

/**
 * @Description 用户相关的缓存实现
 * @Author yuejianli
 * @Date 2022/5/20 23:38
 **/
@Service
@Slf4j
public class UserCacheServiceImpl implements UserCacheService{

    @Resource
    private RedisUtil redisUtil;

    /**
     * 存储两件东西，对应的 key 分别为: token 存储的是 当前的对象， token_account 存储的是最后一次操作的时间
     * @param user 当前的用户对象
     * @param oldToken 以前的老Token
     */
    @Override
    public void refreshToken(User user, String oldToken) {
        Assert.notNull(user,"设置Token的对象不能为空");
        // 有以前的token 值
        if (StringUtils.hasText(oldToken)){
            redisUtil.delByKey(oldToken);
            redisUtil.delByKey(oldToken+"_"+user.getAccount());
        }
        // 进行存储新的token 信息
        redisUtil.set(
                user.getToken(),
                user
        );
        redisUtil.set(
                user.getToken()+"_"+user.getAccount(),
                System.currentTimeMillis()
        );
    }
}
