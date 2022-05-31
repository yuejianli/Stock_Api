package top.yueshushu.learn.service;

import top.yueshushu.learn.mode.ro.BuyRo;
import top.yueshushu.learn.response.OutputResult;

/**
 * @InterfaceName BuyService
 * @Description 买入股票的Service
 * @Author 岳建立
 * @Date 2022/1/8 11:17
 * @Version 1.0
 **/
public interface BuyService {
    /**
     * 买入股票信息
     * @param buyRo
     * @return
     */
    OutputResult buy(BuyRo buyRo);
}
