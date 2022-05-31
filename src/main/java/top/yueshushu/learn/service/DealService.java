package top.yueshushu.learn.service;

import top.yueshushu.learn.mode.ro.DealRo;
import top.yueshushu.learn.mode.ro.RevokeRo;
import top.yueshushu.learn.response.OutputResult;

/**
 * @ClassName:DealService
 * @Description 成交委托单
 * @Author 岳建立
 * @Date 2022/1/8 16:56
 * @Version 1.0
 **/
public interface DealService {
    /**
     * 成交委托单
     * @param dealRo
     * @return
     */
    OutputResult deal(DealRo dealRo);
    /**
     * 虚拟成交委托单信息
     * @param dealRo
     */
    void mockDealXxlJob(DealRo dealRo);
}
