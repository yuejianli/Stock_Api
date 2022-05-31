package top.yueshushu.learn.service;

import top.yueshushu.learn.mode.ro.CacheRo;
import top.yueshushu.learn.response.OutputResult;

/**
 * @ClassName:CacheService
 * @Description 缓存信息
 * @Author 岳建立
 * @Date 2022/1/3 12:01
 * @Version 1.0
 **/
public interface CacheService {
    /**
     * 查询缓存信息
     * @param cacheRo
     * @return
     */
    OutputResult listCache(CacheRo cacheRo);

    /**
     * 更新缓存信息
     * @param cacheRo
     * @return
     */
    OutputResult update(CacheRo cacheRo);

    /**
     * 删除缓存信息
     * @param cacheRo
     * @return
     */
    OutputResult delete(CacheRo cacheRo);
}
