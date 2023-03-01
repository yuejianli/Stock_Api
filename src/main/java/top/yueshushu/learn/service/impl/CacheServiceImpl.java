package top.yueshushu.learn.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.yueshushu.learn.common.Const;
import top.yueshushu.learn.mode.ro.CacheRo;
import top.yueshushu.learn.mode.vo.CacheVo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.response.PageResponse;
import top.yueshushu.learn.service.CacheService;
import top.yueshushu.learn.util.PageUtil;
import top.yueshushu.learn.util.RedisUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @ClassName:CacheServiceImpl
 * @Description TODO
 * @Author 岳建立
 * @Date 2022/1/3 12:01
 * @Version 1.0
 **/
@Service
@Slf4j
public class CacheServiceImpl implements CacheService {
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public OutputResult listCache(CacheRo cacheRo) {
        //如果没有关键字，就查询全部
        String keyPrefix = getKeyPrefix(cacheRo.getUserId(),cacheRo.getType());
        //如果有关键字匹配
        Set<String> keys = redisUtil.getKeys(keyPrefix + "*");
        String keyword = cacheRo.getKey();
        boolean isPattern = StringUtils.hasText(keyword) ? true : false;
        //获取所有的key 信息
        List<CacheVo> cacheVoList = new ArrayList<>();
        for (String key : keys) {
            //如果有关键字，就匹配一下,截取后面的值信息.
            String subKey = key.substring(keyPrefix.length());
            if (isPattern) {
                //不包含的话，不查询
                if (!subKey.contains(keyword)) {
                    continue;
                }
            }
            //获取相关的信息
            try{
                String value = redisUtil.get(key).toString();

                CacheVo cacheVo = new CacheVo();
                cacheVo.setKey(subKey);
                cacheVo.setValue(value);
                cacheVoList.add(cacheVo);
            }catch (Exception e){
                log.error(">>>查询时，出现异常:",e);
                continue;
            }

        }
        List<CacheVo> list = PageUtil.startPage(cacheVoList, cacheRo.getPageNum(),
                cacheRo.getPageSize());
        return OutputResult.buildSucc(new PageResponse<>((long) cacheVoList.size(),
                list));
    }

    private String getKeyPrefix(Integer userId, Integer type) {
        //查询的是公共的
        if (type == 0){
            return Const.CACHE_PUBLIC_KEY_PREFIX;
        }else{
            return Const.CACHE_PUBLIC_KEY_PREFIX;
        }
    }

    @Override
    public OutputResult update(CacheRo cacheRo) {
        String keyPrefix = getKeyPrefix(cacheRo.getUserId(),cacheRo.getType() );
        String key = keyPrefix + cacheRo.getKey();
        //设置值
        redisUtil.set(key, cacheRo.getValue());
        return OutputResult.buildSucc();
    }

    @Override
    public OutputResult delete(CacheRo cacheRo) {
        String keyPrefix = getKeyPrefix(cacheRo.getUserId(),cacheRo.getType() );
        String key = keyPrefix + cacheRo.getKey();
        redisUtil.remove(key);
        return OutputResult.buildSucc();
    }
}
