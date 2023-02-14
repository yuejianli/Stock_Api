package top.yueshushu.learn.business.impl;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import top.yueshushu.learn.business.TradePositionBusiness;
import top.yueshushu.learn.common.Const;
import top.yueshushu.learn.common.ResultCode;
import top.yueshushu.learn.common.SystemConst;
import top.yueshushu.learn.domain.UserDo;
import top.yueshushu.learn.domainservice.TradeEntrustDomainService;
import top.yueshushu.learn.domainservice.UserDomainService;
import top.yueshushu.learn.entity.TradeMoney;
import top.yueshushu.learn.entity.TradePosition;
import top.yueshushu.learn.entity.TradePositionHistoryCache;
import top.yueshushu.learn.entity.User;
import top.yueshushu.learn.enumtype.EntrustStatusType;
import top.yueshushu.learn.enumtype.MockType;
import top.yueshushu.learn.enumtype.SelectedType;
import top.yueshushu.learn.enumtype.TradeRealValueType;
import top.yueshushu.learn.helper.DateHelper;
import top.yueshushu.learn.mode.dto.TradeEntrustQueryDto;
import top.yueshushu.learn.mode.ro.TradePositionRo;
import top.yueshushu.learn.mode.vo.AddPositionVo;
import top.yueshushu.learn.mode.vo.StockSelectedVo;
import top.yueshushu.learn.mode.vo.TradePositionVo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.service.StockSelectedService;
import top.yueshushu.learn.service.StockService;
import top.yueshushu.learn.service.TradeMoneyService;
import top.yueshushu.learn.service.TradePositionService;
import top.yueshushu.learn.service.cache.StockCacheService;
import top.yueshushu.learn.service.cache.TradeCacheService;
import top.yueshushu.learn.util.BigDecimalUtil;
import top.yueshushu.learn.util.StockUtil;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 持仓
 *
 * @author Yue Jianli
 * @date 2022-05-26
 */
@Service
@Slf4j

public class TradePositionBusinessImpl implements TradePositionBusiness {
    @Resource
    private TradePositionService tradePositionService;
    @Resource
    private StockCacheService stockCacheService;
    @Resource
    private TradeCacheService tradeCacheService;
    @Resource
    private StockSelectedService stockSelectedService;
    @Resource
    private TradeMoneyService tradeMoneyService;
    @Resource
    private UserDomainService userDomainService;
    @Resource
    private TradeEntrustDomainService tradeEntrustDomainService;
    @Resource
    private DateHelper dateHelper;
    @Resource
    private StockService stockService;

    @SuppressWarnings("all")
    @Resource(name = Const.ASYNC_SERVICE_EXECUTOR_BEAN_NAME)
    private AsyncTaskExecutor executor;

    @Override
    public OutputResult mockList(TradePositionRo tradePositionRo) {
        OutputResult<List<TradePositionVo>> outputResult = tradePositionService.mockList(tradePositionRo);
        if (!outputResult.getSuccess()) {
            return outputResult;
        }
        return parseFillInfo(outputResult.getData(), tradePositionRo);
    }

    @Override
    public OutputResult realList(TradePositionRo tradePositionRo) {
        // 对数据进行处理。
        Object realEasyMoneyCache = tradeCacheService.getRealEasyMoneyCache(TradeRealValueType.TRADE_POSITION, tradePositionRo.getUserId());
        List<TradePositionVo> tradePositionVoList = new ArrayList<>();
        if (!ObjectUtils.isEmpty(realEasyMoneyCache)) {
            tradePositionVoList = (List<TradePositionVo>) realEasyMoneyCache;
        } else {
            log.info(">>>此次员工{}查询需要同步真实的持仓数据", tradePositionRo.getUserId());
            OutputResult<List<TradePositionVo>> outputResult = tradePositionService.realList(tradePositionRo);
            if (!outputResult.getSuccess()) {
                return outputResult;
            }
            //获取到最新的持仓信息，更新到相应的数据库中.
            tradePositionVoList = outputResult.getData();
            // 将数据保存下来
            tradeCacheService.buildRealEasyMoneyCache(TradeRealValueType.TRADE_POSITION, tradePositionRo.getUserId(), tradePositionVoList, null);
        }
        fillSelectedStockList(tradePositionRo, tradePositionVoList);
        return OutputResult.buildSucc(tradePositionVoList);
    }

    @Override
    public void callProfit(Integer userId, MockType mockType) {

        if (MockType.REAL.equals(mockType)) {
            return;
        }
        // 查询一下信息.
        TradePositionRo tradePositionRo = new TradePositionRo();
        tradePositionRo.setUserId(userId);
        tradePositionRo.setMockType(mockType.getCode());
        tradePositionRo.setSelectType(SelectedType.POSITION.getCode());
        List<TradePositionVo> tradePositionVoList = (List<TradePositionVo>) mockList(tradePositionRo).getData();

        // 更新股票的持仓信息.
        if (CollectionUtils.isEmpty(tradePositionVoList)) {
            return;
        }

        // 计算一下,今天的总的亏损金额

        BigDecimal todayMoneySum = new BigDecimal("0");
        for (TradePositionVo tradePositionVo : tradePositionVoList) {
            TradePosition tempPosition = tradePositionService.getPositionByCode(userId, mockType.getCode(), tradePositionVo.getCode());
            //更新这个信息
            tempPosition.setPrice(tradePositionVo.getPrice());
            tempPosition.setAllMoney(tradePositionVo.getAllMoney());
            tempPosition.setFloatMoney(tradePositionVo.getFloatMoney());
            tempPosition.setFloatProportion(tradePositionVo.getFloatProportion());
            tempPosition.setTodayMoney(tradePositionVo.getTodayMoney());
            todayMoneySum = todayMoneySum.add(tradePositionVo.getTodayMoney());

            // 进行一下更新，否则的话，不进行更新。
            if (tempPosition.getAllAmount() > 0) {
                tradePositionService.updateById(tempPosition);
            }
        }
        tradeMoneyService.updateToDayMoney(userId, mockType, todayMoneySum);
    }

    @Override
    public OutputResult addPosition(AddPositionVo addPositionVo, User currentUser) {
        // 添加用户前验证。
        if (currentUser.getId() != 1) {
            return OutputResult.buildFail(ResultCode.NO_AUTH);
        }
        List<TradePosition> positionList = addPositionVo.getPositionList();
        if (CollectionUtils.isEmpty(positionList)) {
            return OutputResult.buildFail(ResultCode.TRADE_NO_MONEY);
        }
        // 先查询一下用户账号信息，使用账号进行处理。
        String account = addPositionVo.getAccount();

        UserDo addUser = userDomainService.getByAccount(account);
        if (null == addUser) {
            return OutputResult.buildFail(ResultCode.ACCOUNT_NOT_EXIST);
        }
        int addUserId = addUser.getId();

        //处理股票自选信息.

        List<String> stockCodeList = positionList.stream().map(TradePosition::getCode).collect(Collectors.toList());
        executor.submit(() -> {
            stockSelectedService.batchSelected(addUserId, stockCodeList);
        });

        MockType mockType = MockType.MOCK;
        // 对金额等信息进行处理。  处理昨天的。
        // 默认是当天的。
        Date currentDate = DateUtil.date();

        if (addPositionVo.getType() == 0) {
            currentDate = dateHelper.getBeforeLastWorking(DateUtil.offsetDay(currentDate, -1));
        }

        // 对 TradeMoney 进行处理。

        TradeMoney tradeMoney = addPositionVo.getMoneyInfo();
        tradeMoney.setId(null);
        tradeMoney.setMockType(mockType.getCode());
        tradeMoney.setUserId(addUserId);
        tradeMoneyService.operateMoney(tradeMoney);

        // 保存历史数据
        tradeMoneyService.saveMoneyHistory(addUserId, mockType, currentDate);
        // 对持仓进行处理。

        Map<String, String> codeNameMap = stockService.mapNameByCodeList(stockCodeList);

        for (TradePosition tradePosition : positionList) {
            tradePosition.setId(null);
            // 根据股票的编码，获取相应的股票记录信息
            tradePosition.setName(codeNameMap.getOrDefault(tradePosition.getCode(), ""));
            tradePosition.setUserId(addUserId);
            tradePosition.setMockType(mockType.getCode());

            // 进行保存。
            tradePositionService.operatePosition(tradePosition);
        }
        tradePositionService.savePositionHistory(addUserId, mockType, currentDate);

        return OutputResult.buildSucc();
    }

    /**
     * 将获取到的持仓股票信息进行填充，返回
     *
     * @param tradePositionVoList 要解析的数据
     * @param tradePositionRo
     * @return 将获取到的持仓股票信息进行填充，返回
     */
    private OutputResult parseFillInfo(List<TradePositionVo> tradePositionVoList, TradePositionRo tradePositionRo) {
        //最后的结果处理
        List<TradePositionVo> result = new ArrayList<>();
        if(!CollectionUtils.isEmpty(tradePositionVoList)){
            //进行获取，补全相关的信息
            for(TradePositionVo tradePositionVo:tradePositionVoList) {
                //进行补充数据
                BigDecimal price = stockCacheService.getNowCachePrice(tradePositionVo.getCode());
                tradePositionVo.setPrice(price);
                tradePositionVo.setSelectType(SelectedType.POSITION.getCode());
                if (tradePositionVo.getAllAmount() == 0) {
                    continue;
                }
                //设置总的市值
                tradePositionVo.setAllMoney(
                        StockUtil.allMoney(
                                tradePositionVo.getAllAmount(),
                                price
                        )
                );
                //设置浮动盈亏
                tradePositionVo.setFloatMoney(
                        StockUtil.floatMoney(
                                tradePositionVo.getAvgPrice(),
                                price,
                                tradePositionVo.getAllAmount()
                        )
                );
                tradePositionVo.setFloatProportion(
                        StockUtil.floatProportion(
                                tradePositionVo.getAvgPrice(),
                                price,
                                tradePositionVo.getAllAmount()
                        )
                );

                // 查询一下，该股票昨天的盈亏数据信息。
                TradePositionHistoryCache tradePositionHistoryCache =
                        stockCacheService.getLastTradePositionByCode(tradePositionRo.getUserId(),
                                tradePositionRo.getMockType(),
                                tradePositionVo.getCode());

                // 查询该股票今日的手续费，如果没有，则返回 0.

                TradeEntrustQueryDto tradeEntrustQueryDto = new TradeEntrustQueryDto();
                tradeEntrustQueryDto.setUserId(tradePositionRo.getUserId());
                tradeEntrustQueryDto.setMockType(tradePositionRo.getMockType());
                tradeEntrustQueryDto.setCode(tradePositionVo.getCode());
                tradeEntrustQueryDto.setEntrustDate(DateUtil.date());
                tradeEntrustQueryDto.setEntrustStatus(EntrustStatusType.SUCCESS.getCode());
                BigDecimal todayHandMoney = tradeEntrustDomainService.getTotalHandMoney(tradeEntrustQueryDto);
                //设置今日盈亏
                if (null == tradePositionHistoryCache || ObjectUtils.isEmpty(tradePositionHistoryCache.getFloatMoney())) {
                    tradePositionVo.setTodayMoney(
                            BigDecimalUtil.subBigDecimal(tradePositionVo.getFloatMoney(), todayHandMoney)
                    );
                } else {
                    tradePositionVo.setTodayMoney(
                            BigDecimalUtil.subBigDecimal(
                                    BigDecimalUtil.subBigDecimal(
                                            tradePositionVo.getFloatMoney(),
                                            tradePositionHistoryCache.getFloatMoney()
                                    ),
                                    todayHandMoney
                            )
                    );
                }

            }
            result.addAll(tradePositionVoList);
        }
        fillSelectedStockList(tradePositionRo, result);
        return OutputResult.buildSucc(result);
    }

    /**
     * 填充自选信息
     *
     * @param tradePositionRo 配置信息
     * @param result          结果
     */
    private void fillSelectedStockList(TradePositionRo tradePositionRo, List<TradePositionVo> result) {
        if (!SelectedType.POSITION.getCode().equals(tradePositionRo.getSelectType())) {
            //持仓的股票信息
            List<String> positionCodeList = result.stream().map(
                    TradePositionVo::getCode
            ).collect(Collectors.toList());

            //查询该员工对应的自选基金
            List<StockSelectedVo> stockInfoList =
                    stockSelectedService.listSelf(tradePositionRo.getUserId(),
                            null);
            //对自选基金进行处理,如果有的话，就不用处理了。
            if(!CollectionUtils.isEmpty(stockInfoList)){
                for(StockSelectedVo stockSelectedVo:stockInfoList){
                    if(positionCodeList.contains(stockSelectedVo.getStockCode())){
                        continue;
                    }
                    //如果没有的话，就进行相关的查询，组装.
                    TradePositionVo tradePositionVo = getTradePositionVoBySelected(
                            stockSelectedVo, tradePositionRo.getMockType()
                    );
                    result.add(tradePositionVo);
                } }
        }
    }

    /**
     * 构建组装成 持仓信息对象
     * @param stockSelectedVo
     * @return
     */
    private TradePositionVo getTradePositionVoBySelected(StockSelectedVo stockSelectedVo,
                                                         int mockType) {
        TradePositionVo tradePositionVo = new TradePositionVo();
        tradePositionVo.setCode(stockSelectedVo.getStockCode());
        tradePositionVo.setName(stockSelectedVo.getStockName());
        tradePositionVo.setAllAmount(0);
        tradePositionVo.setUseAmount(0);
        tradePositionVo.setAvgPrice(
                SystemConst.DEFAULT_EMPTY
        );
        BigDecimal price = stockCacheService.getNowCachePrice(stockSelectedVo.getStockCode());
        tradePositionVo.setPrice(price);
        tradePositionVo.setAllMoney( SystemConst.DEFAULT_EMPTY);
        tradePositionVo.setFloatMoney(SystemConst.DEFAULT_EMPTY);
        tradePositionVo.setTodayMoney(SystemConst.DEFAULT_EMPTY);
        tradePositionVo.setFloatProportion(SystemConst.DEFAULT_EMPTY);
        tradePositionVo.setMockType(mockType);
        tradePositionVo.setSelectType(SelectedType.SELECTED.getCode());
        return tradePositionVo;
    }
}
