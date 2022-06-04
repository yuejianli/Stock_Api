package top.yueshushu.learn.mapper;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import top.yueshushu.learn.domain.StockHistoryDo;
import top.yueshushu.learn.mode.dto.StockHistoryQueryDto;
import top.yueshushu.learn.mode.dto.StockPriceCacheDto;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 股票的历史交易记录表 Mapper 接口
 * </p>
 *
 * @author 岳建立  自定义的
 * @since 2022-01-02
 */
public interface StockHistoryDoMapper extends BaseMapper<StockHistoryDo> {
    /**
     * 根据股票的code 查询股票的相关信息
     *
     * @param code
     * @return
     */
    List<StockHistoryDo> getStockHistory(@Param("code") String code);

    void deleteAsyncData(@Param("code") String code, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    /**
     * 查询股票的历史交易记录,按照时间降序
     *
     * @param code      股票编码
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 查询股票的历史交易记录
     */
    List<StockHistoryDo> listStockHistoryAndDateDesc(@Param("code") String code,
                                                     @Param("startDate") DateTime startDate,
                                                     @Param("endDate") DateTime endDate);

    /**
     * 查询股票的历史交易记录,按照时间升序.
     *
     * @param code      股票编码
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 查询股票的历史交易记录
     */
    List<StockHistoryDo> listStockHistoryAndDateAsc(@Param("code") String code,
                                                    @Param("startDate") DateTime startDate,
                                                    @Param("endDate") DateTime endDate);

    /**
     * 获取股票那一天的信息
     *
     * @param code
     * @param currDate
     * @return
     */
    StockHistoryDo getStockForDate(@Param("code") String code, @Param("currDate") DateTime currDate);

    /**
     * 查询股票对应的最近收盘价信息
     *
     * @param codeList 股票编码列表
     * @param currDate 日期
     * @return 查询股票对应的最近收盘价信息
     */
    List<StockPriceCacheDto> listClosePrice(@Param("codeList") List<String> codeList,
                                            @Param("currDate") Date currDate);

    /**
     * 根据股票的编码和日期，获取距离这一天最近的股票历史记录数据。
     *
     * @param code    股票编码
     * @param endDate 最近的记录日期
     * @return 根据股票的编码和日期，获取距离这一天最近的股票历史记录数据。
     */
    StockHistoryDo getRecentyHistoryBeforeDate(@Param("code") String code, @Param("endDate") DateTime endDate);

    /**
     * 查询天范围统计的历史记录对象
     *
     * @param stockHistoryQueryDto 天范围历史记录
     * @return 查询天范围统计的历史记录对象
     */
    List<StockHistoryDo> listDayRange(@Param("stockHistoryQueryDto") StockHistoryQueryDto stockHistoryQueryDto);

    /**
     * 删除该股票这期间内的历史数据
     *
     * @param code      股票编码
     * @param startDate 开始日期
     * @param endDate   结束日期
     */
    void deleteAsyncRangeDateData(@Param("code") String code,
                                  @Param("startDate") Date startDate,
                                  @Param("endDate") Date endDate);

    /**
     * 查询 该股票这期间内的历史数据
     *
     * @param code      股票编码
     * @param startDate 开始日期
     * @param endDate   结束日期
     */
    List<String> listDate(@Param("code") String code, @Param("startDate") DateTime startDate,
                          @Param("endDate") DateTime endDate);
}
