package top.yueshushu.learn.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.yueshushu.learn.common.Const;
import top.yueshushu.learn.util.RedisUtil;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * 用途描述
 *
 * @author yuejianli
 * @date 2022-10-13
 */
@Component
@Slf4j
public class InitDataMethods {
    @Resource
    private RedisUtil redisUtil;

    @PostConstruct
    public void initUserId() {
        redisUtil.set(Const.KEY_AUTH_USER_ID, 1);
        log.info(">>>>初始化用户编号信息 成功");
    }
}
