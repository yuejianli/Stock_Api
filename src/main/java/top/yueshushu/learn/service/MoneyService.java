package top.yueshushu.learn.service;

import top.yueshushu.learn.mode.ro.MoneyRo;
import top.yueshushu.learn.mode.vo.CalcMoneyVo;
import top.yueshushu.learn.mode.vo.MoneyVo;
import top.yueshushu.learn.response.OutputResult;

/**
 * @ClassName:MoneyService
 * @Description 金额计算服务
 * @Author 岳建立
 * @Date 2021/11/6 16:00
 * @Version 1.0
 **/
public interface MoneyService {
    /**
     * 处理价格信息，清仓时使用
     * @param moneyRo 计算清仓的对象
     * @return 处理价格信息，清仓时使用
     */
    Double handlerClearPrice(MoneyRo moneyRo);

    /**
     * 计算清仓的价格显示相关信息
     * @param moneyRo 计算清仓的对象
     * @return 计算清仓的价格显示相关信息
     */
    MoneyVo assemblyClearMoneyVo(MoneyRo moneyRo);

    /**
     * 处理价格信息，补仓时使用
     * @param moneyRo 计算补仓的对象
     * @return 处理价格信息，补仓时使用
     */
    Double handlerCovertPrice(MoneyRo moneyRo);

    /**
     * 计算补仓的价格显示相关信息
     * @param moneyRo 计算补仓的对象
     * @return 计算补仓的价格显示相关信息
     */
    CalcMoneyVo coverCalcMoneyVo(MoneyRo moneyRo);

    /**
     * 处理价格信息，减仓时使用
     * @param moneyRo 计算减仓的对象
     * @return 处理价格信息，减仓时使用
     */
    Double handlerReducePrice(MoneyRo moneyRo);
    /**
     * 计算减仓的价格显示相关信息
     * @param moneyRo 计算减仓的对象
     * @return 计算减仓的价格显示相关信息
     */
    CalcMoneyVo reduceCalcMoneyVo(MoneyRo moneyRo);
}
