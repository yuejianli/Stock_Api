package top.yueshushu.learn.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import top.yueshushu.learn.domain.TradeEntrustDo;
import top.yueshushu.learn.mode.dto.TradeEntrustQueryDto;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 委托表 Mapper 接口
 * </p>
 *
 * @author 两个蝴蝶飞  自定义的
 * @since 2022-01-03
 */
public interface TradeEntrustDoMapper extends BaseMapper<TradeEntrustDo> {
    /**
     * 查询当前用户股票类型下的委托信息
     */
    List<TradeEntrustDo> listByQuery(@Param("tradeEntrustQueryDto") TradeEntrustQueryDto tradeEntrustQueryDto);

    /**
     * 删除今日的委托信息
     *
     * @param tradeEntrustQueryDto 今日委托信息
     */
    void deleteToDayByQuery(@Param("tradeEntrustQueryDto") TradeEntrustQueryDto tradeEntrustQueryDto);

    /**
     * 查询当前用户股票类型下的历史委托信息
     */
    List<TradeEntrustDo> listHistoryByQuery(@Param("tradeEntrustQueryDto") TradeEntrustQueryDto tradeEntrustQueryDto);

    /**
     * @param tradeEntrustQueryDto 查询对象
     */
    BigDecimal getTotalHandMoney(@Param("tradeEntrustQueryDto") TradeEntrustQueryDto tradeEntrustQueryDto);

}
