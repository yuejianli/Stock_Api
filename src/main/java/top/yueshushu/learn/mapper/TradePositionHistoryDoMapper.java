package top.yueshushu.learn.mapper;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import top.yueshushu.learn.domain.TradePositionHistoryDo;

import java.util.List;

/**
 * <p>
 * 我的持仓历史记录表 Mapper 接口
 * </p>
 *
 * @author 两个蝴蝶飞  自定义的
 * @since 2022-01-03
 */
public interface TradePositionHistoryDoMapper extends BaseMapper<TradePositionHistoryDo> {
    
    /**
     * 查询股票的我的持仓记录,按照时间降序
     *
     * @param userId    用户编号
     * @param mockType  交易类型
     * @param code      股票编码
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 查询股票的我的持仓记录
     */
    List<TradePositionHistoryDo> listPositionHistoryAndDateDesc(
            @Param("userId") Integer userId,
            @Param("mockType") Integer mockType,
            @Param("code") String code,
            @Param("startDate") DateTime startDate,
            @Param("endDate") DateTime endDate);

    /**
     * 删除当天的已经保存的历史记录信息
     *
     * @param userId   用户编号
     * @param mockType 交易类型
     * @param currDate 当前时间
     */
    void deleteByUserIdAndMockTypeAndDate(@Param("userId") Integer userId, @Param("mockType") Integer mockType,
                                          @Param("currDate") DateTime currDate);

    /**
     * 查询股票最后一次的持仓信息
     *
     * @param userId   用户编号
     * @param mockType 交易类型
     * @param code     股票编码
     */
    TradePositionHistoryDo getLastRecordByCode(@Param("userId") Integer userId, @Param("mockType") Integer mockType, @Param("code") String code);


}
