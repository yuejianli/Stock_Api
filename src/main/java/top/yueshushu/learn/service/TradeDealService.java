package top.yueshushu.learn.service;

import top.yueshushu.learn.domain.TradeEntrustDo;
import top.yueshushu.learn.enumtype.MockType;
import top.yueshushu.learn.mode.ro.TradeDealRo;
import top.yueshushu.learn.mode.vo.TradeDealVo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.response.PageResponse;

import java.util.List;

/**
 * <p>
 * 成交表 自定义的
 * </p>
 *
 * @author 两个蝴蝶飞
 * @since 2022-01-03
 */
public interface TradeDealService {
    /**
     * 添加一条成交记录到成交表里面
     * @param tradeEntrustDo
     */
    void addDealRecord(TradeEntrustDo tradeEntrustDo);


    /**
     * 查询虚拟的今日成交信息
     * @param tradeDealRo 成交对象
     * @return 查询虚拟的今日成交信息
     */
    OutputResult mockList(TradeDealRo tradeDealRo);

    /**
     * 查询真实的今日成交信息
     * @param tradeDealRo 成交对象
     * @return 查询真实的今日成交信息
     */
    OutputResult<List<TradeDealVo>> realList(TradeDealRo tradeDealRo);

    /**
     * 查询虚拟的历史成交信息
     *
     * @param tradeDealRo 成交对象
     * @return 查询虚拟的历史成交信息
     */
    OutputResult<PageResponse<TradeDealVo>> mockHistoryList(TradeDealRo tradeDealRo);

    /**
     * 查询真实的历史成交信息
     *
     * @param tradeDealRo 成交对象
     * @return 查询真实的历史成交信息
     */
    List<TradeDealVo> realHistoryList(TradeDealRo tradeDealRo);

    /**
     * 同步今日成交记录
     *
     * @param userId          用户编号
     * @param tradeDealVoList 今日成交对象
     */
    void syncRealDealByUserId(Integer userId, List<TradeDealVo> tradeDealVoList);

    /**
     * 同步成交信息到数据库
     *
     * @param userId   用户编号
     * @param mockType 类型
     */
    void syncEasyMoneyToDB(Integer userId, MockType mockType);
}
