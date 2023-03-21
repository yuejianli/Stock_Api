package top.yueshushu.learn.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import top.yueshushu.learn.assembler.StockAssembler;
import top.yueshushu.learn.assembler.StockUpdateLogAssembler;
import top.yueshushu.learn.common.Const;
import top.yueshushu.learn.crawler.business.CrawlerStockBusiness;
import top.yueshushu.learn.crawler.business.CrawlerStockHistoryBusiness;
import top.yueshushu.learn.crawler.crawler.CrawlerService;
import top.yueshushu.learn.crawler.entity.DownloadStockInfo;
import top.yueshushu.learn.crawler.entity.StockBigDealInfo;
import top.yueshushu.learn.crawler.service.RealTimePriceService;
import top.yueshushu.learn.domain.StockBigDealDo;
import top.yueshushu.learn.domain.StockDo;
import top.yueshushu.learn.domain.StockUpdateLogDo;
import top.yueshushu.learn.domainservice.StockDomainService;
import top.yueshushu.learn.domainservice.StockUpdateLogDomainService;
import top.yueshushu.learn.entity.User;
import top.yueshushu.learn.enumtype.BigDealKindType;
import top.yueshushu.learn.enumtype.DataFlagType;
import top.yueshushu.learn.enumtype.StockUpdateType;
import top.yueshushu.learn.enumtype.SyncStockHistoryType;
import top.yueshushu.learn.message.dingtalk.DingTalkService;
import top.yueshushu.learn.message.weixin.service.WeChatService;
import top.yueshushu.learn.mode.info.StockShowInfo;
import top.yueshushu.learn.mode.ro.StockRo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.service.StockCrawlerService;
import top.yueshushu.learn.service.UserService;
import top.yueshushu.learn.service.cache.StockCacheService;
import top.yueshushu.learn.util.BigDecimalUtil;
import top.yueshushu.learn.util.RedisUtil;
import top.yueshushu.learn.util.StockUtil;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName:StockCrawlerServiceImpl
 * @Description 股票爬虫时使用
 * @Author 岳建立
 * @Date 2021/11/12 23:07
 * @Version 1.0
 **/
@Service
@Slf4j
public class StockCrawlerServiceImpl implements StockCrawlerService {
    @Resource
    private StockCacheService stockCacheService;
    @Resource
    private CrawlerStockBusiness crawlerStockBusiness;
    @Resource
    private CrawlerStockHistoryBusiness crawlerStockHistoryBusiness;
    @Resource
    private StockDomainService stockDomainService;
    @Resource
    private CrawlerService crawlerService;
    @Resource
    private StockUpdateLogDomainService stockUpdateLogDomainService;
    @Resource
    private StockAssembler stockAssembler;
    @Resource
    private StockUpdateLogAssembler stockUpdateLogAssembler;
    @Resource
    private UserService userService;
    @Resource
    private WeChatService weChatService;
    @Resource
    private DingTalkService dingTalkService;
    @Resource
    private RedisUtil redisUtil;

    @Override
    public OutputResult<StockShowInfo> getStockInfo(StockRo stockRo) {
        return crawlerStockBusiness.getStockInfo(stockRo.getCode());
    }

    @Override
    public OutputResult<String> getStockKline(StockRo stockRo) {
        return crawlerStockBusiness.getStockKline(stockRo);
    }

    @Override
    public OutputResult<String> stockAsync(StockRo stockRo) {
        return crawlerStockBusiness.stockAsync(stockRo);
    }

    @Override
    public OutputResult stockHistoryAsync(StockRo stockRo) {
        //处理日期信息
        OutputResult handlerResult = handlerDate(stockRo);
        if (null != handlerResult) {
            return handlerResult;
        }
        return crawlerStockHistoryBusiness.stockHistoryAsync(stockRo);
    }

    /**
     * 历史交易信息同步时，处理日期.
     *
     * @param stockRo
     */
    private OutputResult handlerDate(StockRo stockRo) {
        SyncStockHistoryType syncRangeType = SyncStockHistoryType.getSyncRangeType(stockRo.getType());
        if (syncRangeType == null) {
            return OutputResult.buildAlert("不支持的同步交易范围");
        }
        String Date_formatter = Const.STOCK_DATE_FORMAT;
        Date now = DateUtil.date();
        String startDate = DateUtil.format(
                now, Date_formatter
        );
        String endDate = DateUtil.format(
                now, Date_formatter
        );
        ;
        switch (syncRangeType) {
            case SELF: {
                startDate = DateUtil.format(
                        DateUtil.parse(
                                stockRo.getStartDate(),
                                Const.DATE_FORMAT
                        )
                        , Date_formatter);
                endDate = DateUtil.format(
                        DateUtil.parse(
                                stockRo.getEndDate(),
                                Const.DATE_FORMAT
                        )
                        , Date_formatter);
                break;
            }
            case WEEK: {
                DateTime dateTime = DateUtil.offsetWeek(now, -1);
                startDate = DateUtil.format(dateTime, Date_formatter);
                break;
            }
            case MONTH: {
                DateTime dateTime = DateUtil.offsetMonth(now, -1);
                startDate = DateUtil.format(dateTime, Date_formatter);
                break;
            }
            case YEAR: {
                DateTime dateTime = DateUtil.offsetMonth(now, -1 * 12);
                startDate = DateUtil.format(dateTime, Date_formatter);
                break;
            }
            case THREE_YEAR: {
                DateTime dateTime = DateUtil.offsetMonth(now, -1 * 12 * 3);
                startDate = DateUtil.format(dateTime, Date_formatter);
                break;
            }
            case FIVE_YEAR: {
                DateTime dateTime = DateUtil.offsetMonth(now, -1 * 12 * 5);
                startDate = DateUtil.format(dateTime, Date_formatter);
                break;
            }
            case TEN_YEAR: {
                DateTime dateTime = DateUtil.offsetMonth(now, -1 * 12 * 10);
                startDate = DateUtil.format(dateTime, Date_formatter);
                break;
            }
            case ALL: {
                // 1984年11月18日 中国第一个股票交易
                startDate = "19841118";
                break;
            }
        }
        stockRo.setStartDate(startDate);
        stockRo.setEndDate(endDate);
        return null;
    }

    @Override
    public void updateCodePrice(String code) {
        //生成真实数据。
        generateRealPriceData(code);
    }

    @Override
    public void batchUpdateNowPrice(List<String> codeList) {
        //生成真实数据。
        batchGenerateRealPriceData(codeList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAllStock() {
        // 先将昨天的最近的记录清理一下。
        stockCacheService.cleanLastTradePositionHistory();
        redisUtil.remove(Const.STOCK_TODAY_QS_CODE);
        redisUtil.deleteByPrefix(Const.STOCK_TODAY_DB_CODE);
        //重置计数器
        Const.PRICE_COUNTER = 0;
        //1. 查询当前所有的股票信息
        List<StockDo> dbAllStockList = stockDomainService.list();
        log.info(">>>数据库查询所有的股票记录成功，查询条数为:{}", dbAllStockList.size());
        Map<String, StockDo> dbStockCodeMap = dbAllStockList.stream().collect(Collectors.toMap(StockDo::getCode, n -> n));
        //爬虫查询，目前的股票列表记录.
        List<DownloadStockInfo> webStockList = crawlerService.getStockList();
        log.info(">>> 查询当前网络系统上，共存在的股票条数为:{}", webStockList.size());

        List<StockDo> addStockDoList = new ArrayList<>();
        List<StockDo> updateStockDoList = new ArrayList<>();

        List<StockUpdateLogDo> stockUpdateLogDoList = new ArrayList<>();
        // 股票更新历史记录
        Date now = DateUtil.date();
        for (DownloadStockInfo downloadStockInfo : webStockList) {
            //如果不存在的话，为新增.
            if (!dbStockCodeMap.containsKey(downloadStockInfo.getCode())) {
                //不包含，说明为新增.
                StockDo addStockDo = stockAssembler.downInfoToDO(downloadStockInfo);
                addStockDo.setCreateTime(now);
                addStockDo.setCreateUser("job");
                addStockDo.setFlag(DataFlagType.NORMAL.getCode());
                addStockDoList.add(addStockDo);
                log.info(">>>>>新增加股票:{}", addStockDo);
            } else {
                //如果编码相同，看名称是否相同.
                StockDo updateStockDo = dbStockCodeMap.get(downloadStockInfo.getCode());
                //名称还一样，说明没有变。
                if (updateStockDo.getName().equals(downloadStockInfo.getName())) {
                    continue;
                }
                String oldName = updateStockDo.getName();
                //更新名称.
                updateStockDo.setName(downloadStockInfo.getName());
                updateStockDo.setCanUse(downloadStockInfo.getCanUse());
                boolean newStockFlag = false;
                if (downloadStockInfo.getName().startsWith("N")) {
                    newStockFlag = true;
                    List<User> userList = userService.listNotice();
                    String newStockMessage = "今日新上市股票提醒 " + downloadStockInfo.getCode() + ",股票名称:" + downloadStockInfo.getName() + "今天上市了";
                    userList.forEach(
                            user -> {
                                weChatService.sendTextMessage(user.getId(), newStockMessage);
                            }
                    );
                    dingTalkService.sendTextMessage(null, newStockMessage);
                }

                log.info(">>>>>更新股票:{}", updateStockDo);
                updateStockDoList.add(updateStockDo);

                StockUpdateLogDo stockUpdateLogDo = stockUpdateLogAssembler.stockEntityToDo(updateStockDo);
                stockUpdateLogDo.setUpdateTime(now);
                stockUpdateLogDo.setName(oldName + "--->" + downloadStockInfo.getName());
                //id为空，避免使用的是股票编码的id
                stockUpdateLogDo.setId(null);
                stockUpdateLogDo.setUpdateType(newStockFlag ? StockUpdateType.NEW.getCode() : StockUpdateType.CHANGE.getCode());
                stockUpdateLogDoList.add(stockUpdateLogDo);
            }
        }

        stockDomainService.updateBatchById(updateStockDoList);
        stockDomainService.saveBatch(addStockDoList);

        //对日志处理
        stockUpdateLogDomainService.saveBatch(stockUpdateLogDoList);
        // 清除缓存信息
        stockCacheService.clearStockInfo();
        log.info(">>>> 更新股票记录成功");
    }

    @Override
    public OutputResult<List<StockBigDealDo>> handleBigDeal(String fullCode, Integer minVolume, Date currentDate) {
        if (null == currentDate) {
            currentDate = DateUtil.date();
        }
        // 进行查询
        List<StockBigDealInfo> stockBigDealInfos = crawlerService.parseBigDealByCode(fullCode, minVolume, DateUtil.format(currentDate, DatePattern.NORM_DATE_PATTERN));

        if (CollectionUtils.isEmpty(stockBigDealInfos)) {
            return OutputResult.buildSucc(Collections.emptyList());
        }

        List<StockBigDealDo> result = new ArrayList<>(stockBigDealInfos.size());
        Date finalCurrentDate = currentDate;

        Map<String, Integer> kindMap = new HashMap<>();
        kindMap.put("D", BigDealKindType.D.getCode());
        kindMap.put("U", BigDealKindType.U.getCode());
        kindMap.put("E", BigDealKindType.E.getCode());
        stockBigDealInfos.forEach(
                n -> {
                    StockBigDealDo tempDo = new StockBigDealDo();
                    // 处理放置
                    tempDo.setFullCode(n.getSymbol());
                    tempDo.setName(n.getName());
                    tempDo.setCurrDate(finalCurrentDate);
                    tempDo.setTickTime(n.getTicktime());
                    tempDo.setPrice(BigDecimalUtil.toBigDecimal(n.getPrice()));
                    tempDo.setPrevPrice(BigDecimalUtil.toBigDecimal(n.getPrev_price()));
                    tempDo.setTradingVolume(BigDecimalUtil.toBigDecimal(n.getVolume()));
                    tempDo.setTradingValue(BigDecimalUtil.toBigDecimal(tempDo.getPrice(), tempDo.getTradingVolume()));
                    tempDo.setKind(kindMap.get(n.getKind()));
                    result.add(tempDo);
                }
        );
        return OutputResult.buildSucc(result);
    }

    /**
     * 生成虚拟的股票信息  P(t) = P(t-1) + random(0,1)*1 - 0.5
     *
     * @param code 股票编码
     * @return 返回生成的虚拟的股票价格
     */
    private void generateMockPriceData(String code) {
        //获取当前的价格
        BigDecimal nowCachePrice = stockCacheService.getNowCachePrice(code);
        BigDecimal randPrice = BigDecimalUtil.toBigDecimal(Math.random() * 1 - 0.5);
        BigDecimal newPrice = BigDecimalUtil.addBigDecimal(nowCachePrice, randPrice);
        stockCacheService.setNowCachePrice(
                code, newPrice
        );
    }

    private void generateRealPriceData(String code) {
        StockRo stockRo = new StockRo();
        //获取当前的股票
        String fullCode = StockUtil.getFullCode(code);
        stockRo.setCode(fullCode);
        //获取当前的价格
//        OutputResult outputResult = crawlerStockBusiness.getStockPrice(fullCode);
//        //获取信息
//        BigDecimal price = (BigDecimal) (outputResult.getData());

        int size = SpringUtil.getBeansOfType(RealTimePriceService.class).size();
        int newCount = Const.PRICE_COUNTER++;
        int useCount = newCount % size;
        // 获取 Service
        RealTimePriceService realTimePriceService = SpringUtil.getBean("realTimePriceService" + useCount, RealTimePriceService.class);
        BigDecimal nowPrice = realTimePriceService.getNowPrice(code, fullCode);
        stockCacheService.setNowCachePrice(code, nowPrice);

    }

    // 批量生成 股票价格信息
    private void batchGenerateRealPriceData(List<String> codeList) {
        // 对数据进行处理.
        if (CollectionUtils.isEmpty(codeList)) {
            return;
        }
        List<String> fullCodeList = codeList.stream().map(
                n -> StockUtil.getFullCode(n)
        ).collect(Collectors.toList());

        int size = SpringUtil.getBeansOfType(RealTimePriceService.class).size();
        int newCount = Const.PRICE_COUNTER++;
        int useCount = newCount % size;
        // 获取 Service
        RealTimePriceService realTimePriceService = SpringUtil.getBean("realTimePriceService" + useCount, RealTimePriceService.class);
        Map<String, BigDecimal> batchPriceMap = realTimePriceService.batchGetNowPrice(codeList, fullCodeList);
        for (String code : codeList) {
            stockCacheService.setNowCachePrice(code, batchPriceMap.getOrDefault(code, BigDecimal.ZERO));
        }
    }
}
