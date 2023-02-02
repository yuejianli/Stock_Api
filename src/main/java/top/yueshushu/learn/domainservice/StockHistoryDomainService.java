package top.yueshushu.learn.domainservice;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.extension.service.IService;
import top.yueshushu.learn.domain.StockHistoryDo;
import top.yueshushu.learn.mode.dto.StockHistoryQueryDto;
import top.yueshushu.learn.mode.dto.StockPriceCacheDto;

import java.util.Date;
import java.util.List;

/**
 * @Description 股票的操作
 * @Author yuejianli
 * @Date 2022/5/20 23:23
 **/
public interface StockHistoryDomainService extends IService<StockHistoryDo> {
    /**
     * 根据股票的编码和时间范围搜索对应的历史记录
     *
     * @param code      股票编码
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 根据股票的编码和时间范围搜索对应的历史记录
     */
    List<StockHistoryDo> listStockHistoryAndDate(String code, DateTime startDate, DateTime endDate);

    /**
     * 查询该股票最近十天的交易信息记录
     *
     * @param stockCode 股票编码
     * @param lastDay   最近的标识时间
     * @return 查询该股票最近十天的交易信息记录
     */
    List<StockHistoryDo> limit10Desc(String stockCode, DateTime lastDay);

    /**
     * 查询股票列表昨天的收盘价
     *
     * @param codeList 股票编码列表
     * @param yesDate  昨天的日期
     * @return 查询股票列表昨天的收盘价
     */
    List<StockPriceCacheDto> listYesterdayClosePrice(List<String> codeList, Date yesDate);

    /**
     * 根据股票的编码和日期获取当时的历史记录
     *
     * @param code     股票编码
     * @param currDate 日期
     * @return 返回当前的历史记录
     */
    StockHistoryDo getByCodeAndCurrDate(String code, Date currDate);

    /**
     * 根据股票的编码和日期，获取距离这一天最近的股票历史记录数据。
     *
     * @param code    股票编码
     * @param endDate 最近的记录日期
     * @return 根据股票的编码和日期，获取距离这一天最近的股票历史记录数据。
     */
    StockHistoryDo getRecentyHistoryBeforeDate(String code, DateTime endDate);

    /**
     * 根据股票的编码和时间范围搜索对应的历史记录,按照日期升序.
     * @param code 股票编码
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 根据股票的编码和时间范围搜索对应的历史记录
     */
    List<StockHistoryDo> listStockHistoryAndDateAsc(String code, DateTime startDate, DateTime endDate);

    /**
     * 查询历史按照天范围统计的记录
     * @param stockHistoryQueryDto 历史天范围对象
     * @return 查询历史按照天范围统计的记录
     */
    List<StockHistoryDo> listDayRange(StockHistoryQueryDto stockHistoryQueryDto);

    /**
     * 删除该股票这期间内的历史数据
     * @param code 股票编码
     * @param startDate 开始日期
     * @param endDate 结束日期
     */
    void deleteAsyncRangeDateData(String code, String startDate, String endDate);

    /**
     * 查询该股票这期间内的历史日期数据
     *
     * @param code      股票编码
     * @param startDate 开始日期
     * @param endDate   结束日期
     */
    List<String> listDate(String code, String startDate, String endDate);

    /**
     * @param codeList    股票编码集合
     * @param currentDate 日期
     */
    void deleteHasAsyncData(List<String> codeList, DateTime currentDate);

    /**
     * 获取最大的日期信息
     */
    Date getMaxCurrentDate();
}
