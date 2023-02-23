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

    /**
     * 查询该日期下 版块的涨幅度排行榜
     *
     * @param date   日期
     * @param bkType 类型
     * @param topNum 数量
     */
    List<StockBkMoneyHistoryDo> listTopByDateOrderByProportionDesc(@Param("date") Date date, @Param("bkType") Integer bkType, @Param("topNum") int topNum);
}
