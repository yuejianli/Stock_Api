package top.yueshushu.learn.business;

import top.yueshushu.learn.mode.ro.CacheRo;
import top.yueshushu.learn.mode.ro.ConfigRo;
import top.yueshushu.learn.response.OutputResult;

/**
 * @Description 缓存的编排层处理
 * @Author 岳建立
 * @Date 2022/5/20 22:33
 **/
public interface CacheBusiness {
    /**
     * 查询缓存的信息
     * @param cacheRo 缓存的Ro对象
     * @return 查询缓存的信息
     */
    OutputResult listCache(CacheRo cacheRo);
    /**
     * 更新缓存的信息
     * @param cacheRo 缓存的Ro对象
     * @return 更新缓存的信息
     */
    OutputResult update(CacheRo cacheRo);
    /**
     * 删除缓存的信息
     * @param cacheRo 缓存的Ro对象
     * @return 删除缓存的信息
     */
    OutputResult delete(CacheRo cacheRo);


}
