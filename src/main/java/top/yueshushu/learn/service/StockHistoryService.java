package top.yueshushu.learn.service;

import top.yueshushu.learn.entity.StockHistory;
import top.yueshushu.learn.mode.dto.StockPriceCacheDto;
import top.yueshushu.learn.mode.ro.StockDayStatRo;
import top.yueshushu.learn.mode.ro.StockRo;
import top.yueshushu.learn.mode.vo.StockHistoryVo;
import top.yueshushu.learn.response.OutputResult;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 股票的历史交易记录表 自定义的
 * </p>
 *
 * @author 岳建立
 * @since 2022-01-02
 */
public interface StockHistoryService{
    /**
     * 分页查询股票的历史记录
     * @param stockRo 股票历史的搜索对象信息
     * @return 分页查询股票的历史记录
     */
    OutputResult pageHistory(StockRo stockRo);

    /**
     * 获取股票昨天对应的收盘价信息
     *
     * @param codeList
     * @return
     */
    List<StockPriceCacheDto> listClosePrice(List<String> codeList);


    /**
     * 获取股票昨天的历史记录信息
     *
     * @param stockCode 股票编码
     * @return
     */
    StockHistory getLastHistory(String stockCode);

    /**
     * 查询最近十天的股票交易信息
     *
     * @param stockCode 股票编码
     * @param lastDay   最大的日期天数
     * @return 查询最近十天的股票交易信息
     */
    List<StockHistory> limit10Desc(String stockCode, Date lastDay);

    /**
     * 根据股票的编码和当前日期获取对应的历史记录信息
     *
     * @param code     股票编码
     * @param currDate 股票记录日期
     * @return 根据股票的编码和当前日期获取对应的历史记录信息
     */
    StockHistoryVo getVoByCodeAndCurrDate(String code, Date currDate);

    /**
     * 根据股票的编码和日期，获取距离这一天最近的股票历史记录数据。
     *
     * @param code    股票编码
     * @param endDate 最近的记录日期
     * @return 根据股票的编码和日期，获取距离这一天最近的股票历史记录数据。
     */
    StockHistoryVo getRecentyHistoryBeforeDate(String code, Date endDate);

    /**
     * 根据股票的编码和时间范围，获取这期间内的股票历史记录数据
     *
     * @param code      股票编码
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 根据股票的编码和时间范围，获取这期间内的股票历史记录数据
     */
    List<StockHistoryVo> getStockHistoryByCodeAndRangeDate(String code, Date startDate, Date endDate);

    /**
     * 查询股票天范围内的历史记录信息
     *
     * @param stockDayStatRo 天范围股票对象
     * @return 查询股票天范围内的历史记录信息
     */
    OutputResult pageDayRange(StockDayStatRo stockDayStatRo);

    /**
     * 获取目前最大的历史记录信息
     */
    Date getMaxCurrentDate();
}
