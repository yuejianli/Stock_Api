package top.yueshushu.learn.business.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.yueshushu.learn.business.CacheBusiness;
import top.yueshushu.learn.business.ConfigBusiness;
import top.yueshushu.learn.mode.ro.CacheRo;
import top.yueshushu.learn.mode.ro.ConfigRo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.service.CacheService;
import top.yueshushu.learn.service.ConfigService;

import javax.annotation.Resource;

/**
 * @Description 缓存的信息实现
 * @Author 岳建立
 * @Date 2022/5/20 22:33
 **/
@Service
@Slf4j
public class CacheBusinessImpl implements CacheBusiness {
    @Resource
    private CacheService cacheService;

    @Override
    public OutputResult listCache(CacheRo cacheRo) {
        return cacheService.listCache(cacheRo);
    }

    @Override
    public OutputResult update(CacheRo cacheRo) {
        return cacheService.update(cacheRo);
    }

    @Override
    public OutputResult delete(CacheRo cacheRo) {
        return cacheService.delete(cacheRo);
    }
}
