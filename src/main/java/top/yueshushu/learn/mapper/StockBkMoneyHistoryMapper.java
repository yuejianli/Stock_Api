package top.yueshushu.learn.mapper;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import top.yueshushu.learn.domain.StockBkMoneyHistoryDo;

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
    void deleteByDate(DateTime currDate);

    List<StockBkMoneyHistoryDo> listMoneyHistoryByCodeAndRangeDate(@Param("bkCode") String bkCode,
                                                                   @Param("startDate") DateTime startDate, @Param("endDate") DateTime endDate);
}
