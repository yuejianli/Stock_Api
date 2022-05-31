package top.yueshushu.learn.business;

import top.yueshushu.learn.mode.ro.BuyRo;
import top.yueshushu.learn.response.OutputResult;

/**
 * @Description 委托买入
 * @Author yuejianli
 * @Date 2022/5/28 19:43
 **/
public interface BuyBusiness {
    /**
     * 委托买入处理
     * @param buyRo 买入对象
     * @return 返回委托买入的处理结果
     */
    OutputResult buy(BuyRo buyRo);
}
