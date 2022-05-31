package top.yueshushu.learn.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import top.yueshushu.learn.domain.TradeDealDo;
import top.yueshushu.learn.mode.dto.TradeDealQueryDto;

import java.util.List;

/**
 * <p>
 * 成交表 Mapper 接口
 * </p>
 *
 * @author 两个蝴蝶飞  自定义的
 * @since 2022-01-03
 */
public interface TradeDealDoMapper extends BaseMapper<TradeDealDo> {
    /**
     * 查询当前用户股票类型下的委托信息
     */
    List<TradeDealDo> listByQuery(@Param("tradeDealQueryDto") TradeDealQueryDto tradeDealQueryDto);

    /**
     * 删除今日的真实交易信息
     *
     * @param tradeDealQueryDto 今日的交易信息对象
     */
    void deleteToDayByQuery(@Param("tradeDealQueryDto") TradeDealQueryDto tradeDealQueryDto);

    /**
     * 根据用户编号和交易类型，查询历史交易记录信息
     *
     * @param tradeDealQueryDto 成交交易查询对象，包含用户编号和类型
     * @return 根据用户编号和交易类型，查询历史14天的交易记录信息
     */
    List<TradeDealDo> listHistoryByQuery(@Param("tradeDealQueryDto") TradeDealQueryDto tradeDealQueryDto);
}
