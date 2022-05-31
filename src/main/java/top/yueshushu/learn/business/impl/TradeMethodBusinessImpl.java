package top.yueshushu.learn.business.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.yueshushu.learn.business.CacheBusiness;
import top.yueshushu.learn.business.TradeMethodBusiness;
import top.yueshushu.learn.entity.TradeMethod;
import top.yueshushu.learn.enumtype.TradeMethodType;
import top.yueshushu.learn.mode.ro.CacheRo;
import top.yueshushu.learn.mode.ro.TradeMethodRo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.service.CacheService;
import top.yueshushu.learn.service.TradeMethodService;

import javax.annotation.Resource;

/**
 * @Description 交易方法信息实现
 * @Author 岳建立
 * @Date 2022/5/20 22:33
 **/
@Service
@Slf4j
public class TradeMethodBusinessImpl implements TradeMethodBusiness {
    @Resource
    private TradeMethodService tradeMethodService;


    @Override
    public TradeMethod getMethod(TradeMethodType tradeMethodType) {
        return tradeMethodService.getMethod(tradeMethodType);
    }

    @Override
    public OutputResult list(TradeMethodRo tradeMethodRo) {
        return tradeMethodService.pageList(
                tradeMethodRo
        );
    }
}
