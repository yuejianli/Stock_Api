package top.yueshushu.learn.business.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import top.yueshushu.learn.business.StatBusiness;
import top.yueshushu.learn.common.Const;
import top.yueshushu.learn.common.ResultCode;
import top.yueshushu.learn.common.SystemConst;
import top.yueshushu.learn.entity.Stock;
import top.yueshushu.learn.entity.StockHistory;
import top.yueshushu.learn.entity.User;
import top.yueshushu.learn.enumtype.AmplitudeType;
import top.yueshushu.learn.enumtype.CharPriceType;
import top.yueshushu.learn.enumtype.WeekStatType;
import top.yueshushu.learn.enumtype.message.VelocityTemplateType;
import top.yueshushu.learn.helper.DateHelper;
import top.yueshushu.learn.message.dingtalk.DingTalkService;
import top.yueshushu.learn.message.email.EmailService;
import top.yueshushu.learn.message.weixin.service.WeChatService;
import top.yueshushu.learn.mode.dto.StockTenToEmailDto;
import top.yueshushu.learn.mode.ro.StatTen10Ro;
import top.yueshushu.learn.mode.ro.StockSelectedRo;
import top.yueshushu.learn.mode.ro.StockStatRo;
import top.yueshushu.learn.mode.vo.StockHistoryVo;
import top.yueshushu.learn.mode.vo.StockSelectedVo;
import top.yueshushu.learn.mode.vo.charinfo.LineSeriesVo;
import top.yueshushu.learn.mode.vo.charinfo.LineVo;
import top.yueshushu.learn.mode.vo.stock.StockWeekStatInfoVo;
import top.yueshushu.learn.mode.vo.stock.StockWeekStatVo;
import top.yueshushu.learn.mode.vo.ten10stat.HistoryRelationVo;
import top.yueshushu.learn.mode.vo.ten10stat.StockRelationVo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.response.PageResponse;
import top.yueshushu.learn.service.StockHistoryService;
import top.yueshushu.learn.service.StockSelectedService;
import top.yueshushu.learn.service.UserService;
import top.yueshushu.learn.service.cache.StockCacheService;
import top.yueshushu.learn.util.BigDecimalUtil;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

/**
 * @Description 菜单实现编排层
 * @Author yuejianli
 * @Date 2022/5/20 23:54
 **/
@Service
@Slf4j

public class StatBusinessImpl implements StatBusiness {
    public static final int TEN_10 = 10;
    @Resource
    private StockHistoryService stockHistoryService;
    @Resource
    private StockCacheService stockCacheService;
    @Resource
    private DateHelper dateHelper;
    @Resource
    private StockSelectedService stockSelectedService;
    @SuppressWarnings("all")
    @Resource(name = Const.ASYNC_SERVICE_EXECUTOR_BEAN_NAME)
    private AsyncTaskExecutor executor;
    @Resource
    private UserService userService;
    @Resource
    private EmailService emailService;
    @Resource
    private WeChatService weChatService;
    @Resource
    private DingTalkService dingTalkService;

    @Override
    public OutputResult getWeekStat(StockStatRo stockStatRo) {
        Stock stock = stockCacheService.selectByCode(stockStatRo.getCode());
        if (stock == null) {
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
        Stock stock = stockCacheService.selectByCode(stockStatRo.getCode());
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

    @Override
    public OutputResult<List<String>> getTenTradeDay() {
        // 获取当前的日期
        DateTime now = DateUtil.date();
        List<String> lastTen10DateList = new ArrayList<>();
        for (int i = 0; i > -50; i--) {
            DateTime tempDate = DateUtil.offsetDay(now, i);
            //是交易日
            if (dateHelper.isWorkingDay(tempDate)) {
                lastTen10DateList.add(DateUtil.format(tempDate, Const.SIMPLE_DATE_FORMAT));
            }
            if (lastTen10DateList.size() >= TEN_10) {
                break;
            }
        }
        //将集合反转
        Collections.reverse(lastTen10DateList);
        return OutputResult.buildSucc(lastTen10DateList);
    }

    @Override
    public OutputResult<List<String>> getTradeDay(StockStatRo stockStatRo) {
        handlerDate(stockStatRo);
        // 总共是 size 个工作日。
        List<String> dateList = dateHelper.betweenWorkDay(stockStatRo.getStartDate(), stockStatRo.getEndDate());
        return OutputResult.buildSucc(dateList);
    }

    /**
     * 处理日期
     *
     * @param stockStatRo 历史记录 日期对象调整
     */
    private void handlerDate(StockStatRo stockStatRo) {
        Date now = DateUtil.date();
        if (!StringUtils.hasText(stockStatRo.getEndDate())) {
            Date endDate = DateUtil.offsetDay(now, 0);
            stockStatRo.setEndDate(DateUtil.format(endDate, Const.SIMPLE_DATE_FORMAT));
        }

        if (!StringUtils.hasText(stockStatRo.getStartDate())) {
            Date startDate = DateUtil.offsetDay(now, -13);
            stockStatRo.setStartDate(DateUtil.format(startDate, Const.SIMPLE_DATE_FORMAT));
        }
    }


    @Override
    public OutputResult<PageResponse<StockRelationVo>> getTenTradeData(StatTen10Ro statTen10Ro) {
        // 先查询一下，分页，自选表信息.
        StockSelectedRo stockSelectedRo = new StockSelectedRo();
        stockSelectedRo.setUserId(statTen10Ro.getUserId());
        stockSelectedRo.setPageNum(statTen10Ro.getPageNum());
        stockSelectedRo.setPageSize(statTen10Ro.getPageSize());
        OutputResult<PageResponse<StockSelectedVo>> stockSelectedResult =
                stockSelectedService.pageSelected(stockSelectedRo);
        List<StockSelectedVo> stockSelectedVoList = stockSelectedResult.getData().getList();
        if (CollectionUtils.isEmpty(stockSelectedVoList)) {
            return OutputResult.buildSucc(PageResponse.emptyPageResponse());
        }
        //处理信息
        List<StockRelationVo> stockRelationVoList = convertTen10VoBySelectedVo(stockSelectedVoList);
        PageInfo pageInfo = new PageInfo<>(stockRelationVoList);
        return OutputResult.buildSucc(new PageResponse<>(
                stockSelectedResult.getData().getTotal(), pageInfo.getList()
        ));
    }

    @Override
    public void ten5ToMail(Integer userId) {
        //查询一下，当前用户的信息.
        User user = userService.getById(userId);
        if (user == null) {
            return;
        }
        if (StringUtils.isEmpty(user.getEmail())) {
            log.info(">>>用户 {} 未配置邮件，不发送最近10 天的交易日信息", user.getAccount());
            return;
        }
        // 获取信息
        StatTen10Ro statTen10Ro = new StatTen10Ro();
        statTen10Ro.setUserId(userId);
        statTen10Ro.setPageSize(30);
        statTen10Ro.setPageNum(1);
        OutputResult<PageResponse<StockRelationVo>> tenDataResult = getTenTradeData(statTen10Ro);
        // 获取数据
        List<StockRelationVo> stockTen10List = tenDataResult.getData().getList();
        List<StockRelationVo> convertTen10VoList = stockTen10List.stream().map(
                n -> {
                    StockRelationVo last5DaVo = new StockRelationVo();
                    List<HistoryRelationVo> historyRelationVoList = n.getDetailList();
                    historyRelationVoList.forEach(
                            vo -> {
                                vo.setAmplitudeProportion(
                                        vo.getAmplitudeProportion().substring(0, vo.getAmplitudeProportion().length() - 2)
                                );
                            }
                    );
                    last5DaVo.setCode(n.getCode());
                    last5DaVo.setName(n.getName().length() > 4 ? n.getName().substring(0, 4) : n.getName());
                    last5DaVo.setDetailList(historyRelationVoList);
                    return last5DaVo;
                }
        ).collect(Collectors.toList());

        // 定义一个 DTO 对象
        List<String> currDateList = getTenTradeDay().getData();
        List<String> convertDateList = currDateList.stream().map(
                n -> n.substring(8)
        ).collect(Collectors.toList());
        StockTenToEmailDto stockTenToEmailDto = new StockTenToEmailDto();
        stockTenToEmailDto.setAccount(user.getAccount());
        stockTenToEmailDto.setName(user.getName());
        stockTenToEmailDto.setDataList(convertTen10VoList);
        stockTenToEmailDto.setCurrDateList(convertDateList);
        stockTenToEmailDto.setLine("<br/>");

        Map<String, Object> modelMap = BeanUtil.beanToMap(stockTenToEmailDto);
        boolean emailFlag = emailService.sendVelocityMail(
                new String[]{user.getEmail()}, "自选股票十个交易日内涨跌记录",
                VelocityTemplateType.TEN10, modelMap
        );
        if (!emailFlag) {
            log.error("发送最近十天的自选股票涨跌记录 给用户{} 失败 ", user.getName());
            weChatService.sendTextMessage(Const.DEFAULT_USER_ID, "发送最近十天的自选股票涨跌记录 给用户" + user.getName() + "失败");
            dingTalkService.sendTextMessage(Const.DEFAULT_USER_ID, "发送最近十天的自选股票涨跌记录 给用户" + user.getName() + "失败");
        }
    }

    /**
     * 将股票信息进行转换，转换成相应的涨跌信息
     *
     * @param stockSelectedVoList 股票自选信息
     * @return 将股票信息进行转换，转换成相应的涨跌信息
     */
    private List<StockRelationVo> convertTen10VoBySelectedVo(List<StockSelectedVo> stockSelectedVoList) {
        List<StockRelationVo> ten10VoList = Collections.synchronizedList(new ArrayList<>(stockSelectedVoList.size()));
        CountDownLatch countDownLatch = new CountDownLatch(stockSelectedVoList.size());
        // 股票信息展示
        for (StockSelectedVo stockSelectedVo : stockSelectedVoList) {
            executor.submit(
                    () -> {
                        try {
                            StockRelationVo stockRelationVo = singleToTen10Vo(stockSelectedVo);
                            ten10VoList.add(stockRelationVo);
                        } catch (Exception e) {

                        } finally {
                            countDownLatch.countDown();
                        }
                    }
            );
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<StockRelationVo> resultList =
                ten10VoList.stream().sorted(Comparator.comparing(StockRelationVo::getCode)).collect(Collectors.toList());
        return resultList;
    }

    /**
     * 单个信息处理
     *
     * @param stockSelectedVo 股票自选信息
     * @return 返回最近10天的交易构建对象信息
     */
    private StockRelationVo singleToTen10Vo(StockSelectedVo stockSelectedVo) {
        StockRelationVo stockRelationVo = new StockRelationVo();
        stockRelationVo.setCode(stockSelectedVo.getStockCode());
        stockRelationVo.setName(stockSelectedVo.getStockName());
        // 查询该股票最近十天的交易记录对象。
        //获取最近的十天交易信息
        OutputResult<List<String>> tenTradeDayOutput = getTenTradeDay();
        List<String> ten10DayList = tenTradeDayOutput.getData();

        List<StockHistory> stockHistoryList =
                stockHistoryService.limit10Desc(stockSelectedVo.getStockCode(), DateUtil.parse(ten10DayList.get(0),
                        Const.SIMPLE_DATE_FORMAT));
        //将其转换时间，转换成相应的map值。
        Map<String, StockHistory> currDateMap = new HashMap<>();
        stockHistoryList.forEach(
                n -> {
                    //将信息转换成相应的map
                    String currDate = DateUtil.format(n.getCurrDate(), Const.SIMPLE_DATE_FORMAT);
                    currDateMap.put(currDate, n);
                }
        );
        List<HistoryRelationVo> historyRelationVoList = new ArrayList<>(ten10DayList.size());
        int i = 0;
        for (String date : ten10DayList) {
            HistoryRelationVo historyRelationVo = new HistoryRelationVo();
            historyRelationVo.setCurrDate(date);
            StockHistory tempStockHistory = currDateMap.get(date);
            i++;
            if (tempStockHistory == null) {
                historyRelationVo.setType(AmplitudeType.NODATA.getCode());
                historyRelationVo.setAmplitudeProportion("未同步");
                //如果是最后一次的话，需要用缓存里面的信息进行替换.
                if (i == ten10DayList.size()) {
                    //最后的一个.
                    BigDecimal nowPrice = stockCacheService.getNowCachePrice(stockSelectedVo.getStockCode());
                    //获取昨天的价格
                    BigDecimal yesterdayCloseCachePrice = stockCacheService.getYesterdayCloseCachePrice(stockSelectedVo.getStockCode());
                    if (yesterdayCloseCachePrice.compareTo(SystemConst.DEFAULT_EMPTY) == 1) {
                        //有昨天的值.
                        if (nowPrice.compareTo(yesterdayCloseCachePrice) == 1) {
                            historyRelationVo.setType(AmplitudeType.ROSE.getCode());
                            historyRelationVo.setAmplitudeProportion(BigDecimalUtil.divPattern(
                                    (BigDecimalUtil.subBigDecimal(nowPrice, yesterdayCloseCachePrice)), yesterdayCloseCachePrice));
                        } else {
                            historyRelationVo.setType(AmplitudeType.WANE.getCode());
                            historyRelationVo.setAmplitudeProportion("-" + BigDecimalUtil.divPattern(
                                    (BigDecimalUtil.subBigDecimal(yesterdayCloseCachePrice, nowPrice)), yesterdayCloseCachePrice));
                        }
                        historyRelationVo.setClosePrice(BigDecimalUtil.toString(nowPrice));
                    }
                }
            } else {
                BigDecimal amplitudeProportion = tempStockHistory.getAmplitudeProportion();
                if (amplitudeProportion.compareTo(SystemConst.DEFAULT_EMPTY) > 0) {
                    historyRelationVo.setType(AmplitudeType.ROSE.getCode());
                } else {
                    historyRelationVo.setType(AmplitudeType.WANE.getCode());
                }
                //转换成字符串
                historyRelationVo.setAmplitudeProportion(BigDecimalUtil.toString(amplitudeProportion));
                historyRelationVo.setClosePrice(BigDecimalUtil.toString(tempStockHistory.getClosingPrice()));
            }
            historyRelationVoList.add(historyRelationVo);
        }
        stockRelationVo.setDetailList(historyRelationVoList);
        return stockRelationVo;
    }

    /**
     * 将历史数据转换成 图表数据
     *
     * @param stockHistoryVoList 历史数据
     * @return 将历史数据转换成 图表数据
     */
    private List<LineSeriesVo> historyConvertLine(List<StockHistoryVo> stockHistoryVoList) {
        List<LineSeriesVo> result = new ArrayList<>();

        LineSeriesVo openingPrice = new LineSeriesVo();
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
