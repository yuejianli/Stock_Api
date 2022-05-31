package top.yueshushu.learn.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import top.yueshushu.learn.domain.TradePositionDo;

/**
 * <p>
 * 我的持仓表 Mapper 接口
 * </p>
 *
 * @author 两个蝴蝶飞  自定义的
 * @since 2022-01-03
 */
public interface TradePositionDoMapper extends BaseMapper<TradePositionDo> {
    /**
     * 持仓表里面，更新可用的股票数量
     */
    void syncUseAmount();

    /**
     * 删除当天的已经保存的历史记录信息
     *
     * @param userId   用户编号
     * @param mockType 交易类型
     */
    void deleteByUserIdAndMockType(@Param("userId") Integer userId, @Param("mockType") Integer mockType);
}
