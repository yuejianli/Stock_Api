package top.yueshushu.learn.business;

import top.yueshushu.learn.enumtype.MockType;
import top.yueshushu.learn.mode.ro.TradeUserRo;
import top.yueshushu.learn.response.OutputResult;

/**
 * @Description 交易用户的编排层
 * @Author yuejianli
 * @Date 2022/5/21 6:07
 **/
public interface TradeUserBusiness {
    /**
     * 交易用户登录信息
     *
     * @param tradeUserRo 交易用户登录对象
     * @return 返回交易用户登录成功后的信息
     */
    OutputResult login(TradeUserRo tradeUserRo);

    /**
     * 修改用户的账号和密码信息
     *
     * @param tradeUserRo 交易用户对象
     */
    OutputResult editInfo(TradeUserRo tradeUserRo);

    /**
     * 员工是否进行了配置
     *
     * @param userId   员工id
     * @param mockType 类型
     */
    boolean configTradeUser(Integer userId, MockType mockType);
}
