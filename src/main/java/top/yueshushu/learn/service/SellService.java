package top.yueshushu.learn.service;

import top.yueshushu.learn.mode.ro.BuyRo;
import top.yueshushu.learn.mode.ro.SellRo;
import top.yueshushu.learn.response.OutputResult;

/**
 * @InterfaceName SellService
 * @Description 卖出股票的Service
 * @Author 岳建立
 * @Date 2022/1/8 11:17
 * @Version 1.0
 **/
public interface SellService {
    /**
     * 卖出股票信息
     * @param sellRo
     * @return
     */
    OutputResult sell(SellRo sellRo);
}
