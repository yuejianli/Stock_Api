package top.yueshushu.learn.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.yueshushu.learn.common.Const;
import top.yueshushu.learn.domain.UserDo;
import top.yueshushu.learn.util.RedisUtil;
import top.yueshushu.learn.util.ThreadLocalUtils;

import javax.servlet.http.HttpServletRequest;

public abstract class BaseController {
    @Autowired
    private RedisUtil redisUtil;
    /**
     * 根据请求头获取对应的token 信息
     * @return
     */
    protected int getUserId() {
       return ThreadLocalUtils.getUserId();
    }
}
