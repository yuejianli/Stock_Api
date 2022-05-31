package top.yueshushu.learn.business;

import top.yueshushu.learn.mode.ro.MoneyRo;
import top.yueshushu.learn.response.OutputResult;

/**
 * @Description 股票小工具编排层
 * @Author yuejianli
 * @Date 2022/5/22 9:35
 **/
public interface MoneyBusiness {
    /**
     * 股票清仓的响应信息
     * @param moneyRo 股票清仓对象
     * @return 返回股票清仓
     */
    OutputResult clearMoney(MoneyRo moneyRo);
    /**
     * 股票补仓的响应信息
     * @param moneyRo 股票补仓对象
     * @return 股票补仓的响应信息
     */
    OutputResult coverMoney(MoneyRo moneyRo);
    /**
     * 股票减仓的响应信息
     * @param moneyRo 股票减仓对象
     * @return 股票减仓的响应信息
     */
    OutputResult reduceMoney(MoneyRo moneyRo);
}
