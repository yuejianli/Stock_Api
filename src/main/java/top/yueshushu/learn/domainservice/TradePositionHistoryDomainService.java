package top.yueshushu.learn.domainservice;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

import cn.hutool.core.date.DateTime;
import top.yueshushu.learn.domain.TradePositionHistoryDo;

/**
 * @Description 历史持仓记录的操作
 * @Author yuejianli
 * @Date 2022/5/20 23:23
 **/
public interface TradePositionHistoryDomainService extends IService<TradePositionHistoryDo> {
    /**
     * 根据股票的编码和时间范围搜索对应的持仓历史记录
     *
     * @param userId    用户编号
     * @param mockTpe   股票类型
     * @param code      股票编码
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 根据股票的编码和时间范围搜索对应的持仓历史记录
     */
    List<TradePositionHistoryDo> listPositionHistoryAndDate(Integer userId, Integer mockTpe, String code, DateTime startDate, DateTime endDate);
    
    
    /**
     * 删除当天的已经保存的历史记录信息
     *
     * @param userId   用户编号
     * @param mockType 交易类型
     * @param currDate 当前时间
     */
    void deleteByUserIdAndMockTypeAndDate(Integer userId, Integer mockType, DateTime currDate);
}
