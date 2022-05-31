package top.yueshushu.learn.service.cache.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.yueshushu.learn.common.Const;
import top.yueshushu.learn.enumtype.TradeRealValueType;
import top.yueshushu.learn.service.cache.TradeCacheService;
import top.yueshushu.learn.util.RedisUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Description 交易相关的缓存实现
 * @Author yuejianli
 * @Date 2022/5/20 23:38
 **/
@Service
@Slf4j
public class TradeCacheServiceImpl implements TradeCacheService {
    @Resource
    private RedisUtil redisUtil;


    @Override
    public boolean needSyncReal(TradeRealValueType tradeRealValueType, Integer userId) {
        if (userId == null){
            return true;
        }
        return redisUtil.setIfAbsent(buildRealPositionKey(tradeRealValueType,userId).get(0),1,30, TimeUnit.MINUTES);
    }

    @Override
    public void immediatelySyncReal(TradeRealValueType tradeRealValueType,Integer userId) {
        if(userId ==null){
            redisUtil.delete(buildRealPositionKey(tradeRealValueType,null));
        }else{
            redisUtil.delByKey(buildRealPositionKey(tradeRealValueType,userId).get(0));
        }
    }
    private List<String> buildRealPositionKey(TradeRealValueType tradeRealValueType,Integer userId){
        String keyPrefix = Const.CACHE_PUBLIC_KEY_PREFIX+"trade:"+tradeRealValueType.getCode()+":";
        if (userId !=null){
            return Collections.singletonList(keyPrefix+userId);
        }
        return new ArrayList<>(redisUtil.keys(keyPrefix));
    }
}
