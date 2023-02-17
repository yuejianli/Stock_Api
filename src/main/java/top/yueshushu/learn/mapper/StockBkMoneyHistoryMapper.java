package top.yueshushu.learn.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import top.yueshushu.learn.domain.StockBkMoneyHistoryDo;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 版块资金注入历史 Mapper 接口
 * </p>
 *
 * @author 岳建立
 * @since 2023-02-07
 */
public interface StockBkMoneyHistoryMapper extends BaseMapper<StockBkMoneyHistoryDo> {
    /**
     * 删除当前日期的数据
     *
     * @param currDate 当前日期
     */
    void deleteByDate(@Param("currDate") Date currDate, @Param("type") Integer type);

    List<StockBkMoneyHistoryDo> listMoneyHistoryByCodeAndRangeDate(@Param("bkCode") String bkCode,
                                                                   @Param("startDate") Date startDate,
                                                                   @Param("endDate") Date endDate);
}
