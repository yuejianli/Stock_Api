package top.yueshushu.learn.business;

import top.yueshushu.learn.entity.TradeMethod;
import top.yueshushu.learn.enumtype.TradeMethodType;
import top.yueshushu.learn.mode.ro.TradeMethodRo;
import top.yueshushu.learn.response.OutputResult;

/**
 * @Description 股票交易方法的编排层处理
 * @Author 岳建立
 * @Date 2022/5/20 22:33
 **/
public interface TradeMethodBusiness {
    /**
     * 根据方法类型,获取相应的方法信息
     *
     * @param tradeMethodType
     * @return 根据方法类型, 获取相应的方法信息
     */
    TradeMethod getMethod(TradeMethodType tradeMethodType);

    /**
     * 查询交易方法列表
     *
     * @param tradeMethodRo 交易方法筛选条件
     * @return 查询交易方法列表
     */
    OutputResult list(TradeMethodRo tradeMethodRo);

    /**
     * 处理验证码
     *
     * @param userId 用户编号
     */
    OutputResult<String> yzm(Integer userId);
}
