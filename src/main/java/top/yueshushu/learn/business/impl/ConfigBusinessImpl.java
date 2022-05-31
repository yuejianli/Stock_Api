package top.yueshushu.learn.business.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.yueshushu.learn.business.ConfigBusiness;
import top.yueshushu.learn.business.UserBusiness;
import top.yueshushu.learn.entity.User;
import top.yueshushu.learn.mode.ro.ConfigRo;
import top.yueshushu.learn.mode.ro.UserRo;
import top.yueshushu.learn.mode.vo.MenuVo;
import top.yueshushu.learn.mode.vo.UserVo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.service.ConfigService;
import top.yueshushu.learn.service.MenuService;
import top.yueshushu.learn.service.UserService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description 配置信息实现
 * @Author 岳建立
 * @Date 2022/5/20 22:33
 **/
@Service
@Slf4j
public class ConfigBusinessImpl implements ConfigBusiness {
    @Resource
    private ConfigService configService;


    @Override
    public OutputResult listConfig(ConfigRo configRo) {
        return configService.listConfig(configRo);
    }

    @Override
    public OutputResult update(ConfigRo configRo) {
        return configService.update(configRo);
    }

    @Override
    public OutputResult reset(ConfigRo configRo) {
        return configService.reset(configRo);
    }
}
