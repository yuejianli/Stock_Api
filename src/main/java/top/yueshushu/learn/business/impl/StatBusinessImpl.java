package top.yueshushu.learn.business.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.yueshushu.learn.business.StatBusiness;
import top.yueshushu.learn.common.Const;
import top.yueshushu.learn.common.ResultCode;
import top.yueshushu.learn.entity.Stock;
import top.yueshushu.learn.enumtype.CharPriceType;
import top.yueshushu.learn.enumtype.WeekStatType;
import top.yueshushu.learn.helper.DateHelper;
import top.yueshushu.learn.mode.ro.StockStatRo;
import top.yueshushu.learn.mode.vo.StockHistoryVo;
import top.yueshushu.learn.mode.vo.charinfo.LineSeriesVo;
import top.yueshushu.learn.mode.vo.charinfo.LineVo;
import top.yueshushu.learn.mode.vo.stock.StockWeekStatInfoVo;
import top.yueshushu.learn.mode.vo.stock.StockWeekStatVo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.service.StockHistoryService;
import top.yueshushu.learn.service.StockService;
import top.yueshushu.learn.util.BigDecimalUtil;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description 菜单实现编排层
 * @Author yuejianli
 * @Date 2022/5/20 23:54
 **/
@Service
@Slf4j
public class StatBusinessImpl implements StatBusiness {
    @Resource
    private StockHistoryService stockHistoryService;
    @Resource
    private StockService stockService;
    @Resource
    private DateHelper dateHelper;
    @Override
    public OutputResult getWeekStat(StockStatRo stockStatRo) {
        Stock stock = stockService.selectByCode(stockStatRo.getCode());
        if (stock == null){
            return OutputResult.buildAlert(
                    ResultCode.STOCK_CODE_NO_EXIST
            );
        }

        //查询当前范围内的记录信息
        int offset = -1;
        DateTime endDate = DateUtil.date().offsetNew(
                DateField.DAY_OF_YEAR, offset - 0
        );
        List<DateTime> searchDateList = new ArrayList<>();
        // 一个星期
        searchDateList.add(DateUtil.date().offsetNew(
                DateField.DAY_OF_YEAR, offset - 7
        ));
        // 两个星期
        searchDateList.add(DateUtil.date().offsetNew(
                DateField.DAY_OF_YEAR, offset - 14
        ));
        // 三个星期
        searchDateList.add(DateUtil.date().offsetNew(
                DateField.DAY_OF_YEAR, offset - 21
        ));
        // 一个月
        searchDateList.add(DateUtil.date().offsetNew(
                DateField.MONTH, -1
        ));
        //对记录进行处理
        StockWeekStatVo stockWeekStatVo = new StockWeekStatVo();

        //先查询一下，最近的一天的值.
        StockHistoryVo lastVo = stockHistoryService.getRecentyHistoryBeforeDate(
                stockStatRo.getCode(), endDate
        );
        List<StockWeekStatInfoVo> stockWeekStatInfoVoList = new ArrayList<>();
        int weekIndex = 1;
        for (DateTime dateTime : searchDateList) {
            StockHistoryVo tempVo = stockHistoryService.getRecentyHistoryBeforeDate(
                    stockStatRo.getCode(), dateTime
            );
            //比较，放置值
            StockWeekStatInfoVo stockWeekStatInfoVo = new StockWeekStatInfoVo();
            stockWeekStatInfoVo.setType(weekIndex);
            stockWeekStatInfoVo.setTypeName(
                    WeekStatType.getExchangeType(
                            weekIndex
                    ).getDesc()
            );
            BigDecimal bigDecimal = BigDecimalUtil.subBigDecimal(
                    lastVo.getClosingPrice(),
                    tempVo.getClosingPrice()
            );
            stockWeekStatInfoVo.setRangePrice(
                    BigDecimalUtil.toString(bigDecimal)
            );
            String proportion = BigDecimalUtil.divPattern(
                    bigDecimal, tempVo.getClosingPrice()
            );
            stockWeekStatInfoVo.setRangeProportion(proportion);
            weekIndex = weekIndex + 1;
            stockWeekStatInfoVoList.add(stockWeekStatInfoVo);
        }
        stockWeekStatVo.setWeekStatInfoList(stockWeekStatInfoVoList);
        return OutputResult.buildSucc(stockWeekStatVo);

    }

    @Override
    public OutputResult getCharStat(StockStatRo stockStatRo) {
        Stock stock = stockService.selectByCode(stockStatRo.getCode());
        if (stock == null){
            return OutputResult.buildAlert(
                    ResultCode.STOCK_CODE_NO_EXIST
            );
        }

        CharPriceType[] values = CharPriceType.values();
        List<String> legendList = new ArrayList<>();
        for (CharPriceType charPriceType : values) {
            legendList.add(charPriceType.getDesc());
        }
        //获取范围
        List<String> xaxisData = new ArrayList<>();
        //获取开始日期和结束日期
        String startDate = stockStatRo.getStartDate();
        String endDate = stockStatRo.getEndDate();
        //计算开始日期和结束日期相差多少天，就是后面的计算值.
        DateTime startDateDate = DateUtil.parse(startDate, Const.SIMPLE_DATE_FORMAT);
        DateTime endDateDate = DateUtil.parse(endDate, Const.SIMPLE_DATE_FORMAT);
        //计算一下，相差多少天
        long day = DateUtil.betweenDay(startDateDate, endDateDate, true);
        //进行计算
        for (int i = 0; i <= day; i++) {
            DateTime tempDate = DateUtil.offsetDay(startDateDate, i);
            if (dateHelper.isWorkingDay(tempDate)) {
                xaxisData.add(DateUtil.format(tempDate, Const.SIMPLE_DATE_FORMAT));
            }
        }
        LineVo lineVo = new LineVo();
        lineVo.setLegend(legendList);
        lineVo.setXaxisData(xaxisData);

        //计算拼接信息.
        List<StockHistoryVo> stockHistoryVoList = stockHistoryService.getStockHistoryByCodeAndRangeDate(
                stockStatRo.getCode(),
                startDateDate,
                endDateDate
        );
        if (CollectionUtils.isEmpty(stockHistoryVoList)) {
            return OutputResult.buildSucc(lineVo);
        }
        //进行处理.
        List<LineSeriesVo> lineSeriesVoList = historyConvertLine(stockHistoryVoList);
        lineVo.setSeries(lineSeriesVoList);
        return OutputResult.buildSucc(lineVo);
    }

    /**
     * 将历史数据转换成 图表数据
     * @param stockHistoryVoList 历史数据
     * @return  将历史数据转换成 图表数据
     */
    private List<LineSeriesVo> historyConvertLine(List<StockHistoryVo> stockHistoryVoList) {
        List<LineSeriesVo> result=new ArrayList<>();

        LineSeriesVo openingPrice=new LineSeriesVo();
        openingPrice.setName(CharPriceType.OPENINGPRICE.getDesc());
        LineSeriesVo closingPrice=new LineSeriesVo();
        closingPrice.setName(CharPriceType.CLOSINGPRICE.getDesc());
        LineSeriesVo highestPrice=new LineSeriesVo();
        highestPrice.setName(CharPriceType.HIGHESTPRICE.getDesc());
        LineSeriesVo lowestPrice=new LineSeriesVo();
        lowestPrice.setName(CharPriceType.LOWESTPRICE.getDesc());
        LineSeriesVo amplitudeproportion=new LineSeriesVo();
        amplitudeproportion.setName(CharPriceType.AMPLITUDEPROPORTION.getDesc());

        LineSeriesVo amplitude=new LineSeriesVo();
        amplitude.setName(CharPriceType.AMPLITUDE.getDesc());

        //处理信息
        for(StockHistoryVo stockHistoryVo:stockHistoryVoList){
            openingPrice.getData().add(BigDecimalUtil.toDouble(stockHistoryVo.getOpeningPrice()));
            closingPrice.getData().add(BigDecimalUtil.toDouble(stockHistoryVo.getClosingPrice()));
            highestPrice.getData().add(BigDecimalUtil.toDouble(stockHistoryVo.getHighestPrice()));
            lowestPrice.getData().add(BigDecimalUtil.toDouble(stockHistoryVo.getLowestPrice()));
            amplitudeproportion.getData().add(BigDecimalUtil.toDouble(stockHistoryVo.getAmplitudeProportion()));
            amplitude.getData().add(BigDecimalUtil.toDouble(stockHistoryVo.getAmplitude()));
        }
        result.add(openingPrice);
        result.add(closingPrice);
        result.add(highestPrice);
        result.add(lowestPrice);
        result.add(amplitudeproportion);
        result.add(amplitude);
        return result;
    }

    /**
     * 将历史进行转换
     * @param stockHistoryList
     */
    private StockWeekStatInfoVo historyListConvertStatVo(List<StockHistoryVo> stockHistoryList) {
        StockWeekStatInfoVo stockWeekStatInfoVo=new StockWeekStatInfoVo();
        if(CollectionUtils.isEmpty(stockHistoryList)){
            return stockWeekStatInfoVo;
        }
        //获取第一个值.
        StockHistoryVo first=stockHistoryList.get(0);
        StockHistoryVo last=stockHistoryList.get(stockHistoryList.size()-1);

        BigDecimal bigDecimal = BigDecimalUtil.subBigDecimal(
                last.getClosingPrice(),
                first.getClosingPrice()
        );
        stockWeekStatInfoVo.setRangePrice(
                BigDecimalUtil.toString(bigDecimal)
        );
        String proportion = BigDecimalUtil.divPattern(
                bigDecimal, first.getClosingPrice()
        );
        stockWeekStatInfoVo.setRangeProportion(proportion);
        return stockWeekStatInfoVo;
    }
}
