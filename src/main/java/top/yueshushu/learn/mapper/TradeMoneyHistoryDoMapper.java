package top.yueshushu.learn.mapper;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import top.yueshushu.learn.domain.TradeMoneyHistoryDo;

import java.util.List;

/**
 * <p>
 * 资金历史表 Mapper 接口
 * </p>
 *
 * @author 两个蝴蝶飞  自定义的
 * @since 2022-01-03
 */
public interface TradeMoneyHistoryDoMapper extends BaseMapper<TradeMoneyHistoryDo> {
	/**
	 * 查询资金历史记录信息
	 *
	 * @param userId    用户id
	 * @param mockType  类型
	 * @param startDate 开始日期
	 * @param endDate   结束日期
	 */
	List<TradeMoneyHistoryDo> listMoneyHistory(@Param("userId") Integer userId, @Param("mockType") Integer mockType,
                                               @Param("startDate") DateTime startDate, @Param("endDate") DateTime endDate);

    /**
     * 获取该日前前的最近一条记录信息
     *
     * @param userId   用户id
     * @param mockType 类型
     * @param currDate 日期
     */
    TradeMoneyHistoryDo getLastRecord(@Param("userId") Integer userId, @Param("mockType") Integer mockType, @Param("currDate") DateTime currDate);

    /**
     * 删除金额历史信息
     *
     * @param userId   用户编号
     * @param mockType 类型
     * @param currDate 日期
     */
    void deleteByUserIdAndMockTypeAndDate(@Param("userId") Integer userId, @Param("mockType") Integer mockType, @Param("currDate") DateTime currDate);
}
