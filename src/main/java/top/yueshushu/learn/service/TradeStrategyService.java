package top.yueshushu.learn.service;

import top.yueshushu.learn.mode.ro.BuyRo;

/**
 * @ClassName:TradeStrategyService
 * @Description 交易策略
 * @Author zk_yjl
 * @Date 2022/1/11 20:33
 * @Version 1.0
 * @Since 1.0
 **/
public interface TradeStrategyService {
    /**
     * 交易策略处理
     * @date 2022/1/11 20:34
     * @author zk_yjl
     * @param buyRo
     * @return void
     */
    void mockEntructXxlJob(BuyRo buyRo);
}
