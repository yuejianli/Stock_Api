package top.yueshushu.learn.service;

import top.yueshushu.learn.entity.TradeUser;
import top.yueshushu.learn.mode.ro.TradeUserRo;
import top.yueshushu.learn.response.OutputResult;

/**
 * <p>
 * 交易用户信息 自定义的
 * </p>
 *
 * @author 岳建立
 * @since 2022-01-02
 */
public interface TradeUserService {
    /**
     * 交易用户登录信息
     *
     * @param tradeUserRo 交易用户请求
     * @return 返回交易用户登录的响应信息
     */
    OutputResult login(TradeUserRo tradeUserRo);

    /**
     * 更新交易用户信息
     *
     * @param tradeUser 交易用户信息
     * @param userId    用户编号
     */
    void operateTradeUser(TradeUser tradeUser, Integer userId);
}
