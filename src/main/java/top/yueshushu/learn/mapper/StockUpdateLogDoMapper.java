package top.yueshushu.learn.mapper;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import top.yueshushu.learn.domain.StockUpdateLogDo;

import java.util.List;

/**
 * @Description 股票更新历史
 * @Author yuejianli
 * @Date 2022/6/4 10:07
 **/
public interface StockUpdateLogDoMapper extends BaseMapper<StockUpdateLogDo> {
    /**
     * 查询股票的更新日志记录
     *
     * @param code      股票编码
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 查询股票的更新日志记录
     */
    List<StockUpdateLogDo> listStockLogAndDate(@Param("code") String code, @Param("startDate") DateTime startDate,
                                               @Param("endDate") DateTime endDate);
}
