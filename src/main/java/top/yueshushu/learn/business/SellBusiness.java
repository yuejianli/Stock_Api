package top.yueshushu.learn.business;

import top.yueshushu.learn.mode.ro.SellRo;
import top.yueshushu.learn.response.OutputResult;

/**
 * @Description 委托卖出
 * @Author yuejianli
 * @Date 2022/5/28 19:43
 **/
public interface SellBusiness {
    /**
     * 卖出股票
     * @param sellRo 卖出股票
     * @return 卖出股票
     */
    OutputResult sell(SellRo sellRo);
}
