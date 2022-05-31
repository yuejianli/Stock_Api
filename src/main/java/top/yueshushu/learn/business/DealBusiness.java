package top.yueshushu.learn.business;

import top.yueshushu.learn.mode.ro.DealRo;
import top.yueshushu.learn.response.OutputResult;

/**
 * @Description 成交委托单
 * @Author yuejianli
 * @Date 2022/5/28 21:57
 **/
public interface DealBusiness {
    /**
     * 成交委托单
     * @param dealRo 成交委托单对象
     * @return 返回成交委托单
     */
    OutputResult deal(DealRo dealRo);

    /**
     * 虚拟成交，通过 xxlJob调用触发
     * @param dealRo 成交单对象
     */
    void mockDealXxlJob(DealRo dealRo);
}
