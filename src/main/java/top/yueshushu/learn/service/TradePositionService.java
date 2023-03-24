package top.yueshushu.learn.service;

import top.yueshushu.learn.entity.TradePosition;
import top.yueshushu.learn.enumtype.MockType;
import top.yueshushu.learn.exception.TradeUserException;
import top.yueshushu.learn.mode.ro.TradePositionRo;
import top.yueshushu.learn.mode.vo.TradePositionVo;
import top.yueshushu.learn.response.OutputResult;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 我的持仓表 自定义的
 * </p>
 *
 * @author 两个蝴蝶飞
 * @since 2022-01-03
 */
public interface TradePositionService {
    /**
     * 获取某个股票的持仓信息
     * @param userId 用户编号
     * @param mockType 股票类型
     * @param code 股票编码
     * @return 获取某个股票的持仓信息
     */
    TradePosition getPositionByCode(
            Integer userId,
            Integer mockType,
            String code
    );

    /**
     * 同步可用数量
     */
    void syncUseAmountByXxlJob();

    /**
     * 保存持仓记录的历史信息
     *
     * @param userId      用户编号
     * @param mock        类型
     * @param currentDate 要改变的日期
     */
    void savePositionHistory(Integer userId, MockType mock, Date currentDate);

    /**
     * 查询虚拟的股票持仓记录
     * @param tradePositionRo 股票持仓记录对象
     * @return 查询虚拟的股票持仓记录
     */
    OutputResult<List<TradePositionVo>> mockList(TradePositionRo tradePositionRo);

    /**
     * 查询真实的股票持仓记录
     *
     * @param tradePositionRo 股票持仓记录对象
     * @return 查询真实的股票持仓记录
     */
    OutputResult<List<TradePositionVo>> realList(TradePositionRo tradePositionRo) throws TradeUserException;

    /**
     * 删除真实的股票持仓记录信息
     * @param userId 用户编号
     */
    void deleteRealByUserId(Integer userId);

    /**
     * 通过员工编号，同步最新的持仓记录
     *
     * @param userId              员工编号
     * @param tradePositionVoList 查询到的持仓列表
     */
    void syncRealPositionByUserId(Integer userId, List<TradePositionVo> tradePositionVoList);

    /**
     * 同步东财的数据，并保存到数据库里面。
     *
     * @param userId   用户编号
     * @param mockType 类型
     */
    void syncEasyMoneyToDB(Integer userId, MockType mockType);

    /**
     * 更新持仓数量
     *
     * @param tradePosition 持仓数量对象
     */
    void updateById(TradePosition tradePosition);

    /**
     * 清仓
     *
     * @param id 持仓id编号
     */
    void clearById(Integer id);

    /**
     * 更新持仓信息
     *
     * @param tradePosition 更新持仓信息
     */
    void operatePosition(TradePosition tradePosition);
}
