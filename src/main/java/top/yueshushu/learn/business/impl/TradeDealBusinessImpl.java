package top.yueshushu.learn.business.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.yueshushu.learn.business.TradeDealBusiness;
import top.yueshushu.learn.mode.ro.TradeDealRo;
import top.yueshushu.learn.mode.vo.TradeDealVo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.service.TradeDealService;
import top.yueshushu.learn.service.cache.TradeCacheService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 委托信息
 *
 * @author Yue Jianli
 * @date 2022-05-26
 */
@Service
@Slf4j

public class TradeDealBusinessImpl implements TradeDealBusiness {
    @Resource
    private TradeDealService tradeDealService;
    @Resource
    private TradeCacheService tradeCacheService;

    @Override
    public OutputResult mockList(TradeDealRo tradeDealRo) {
        return tradeDealService.mockList(tradeDealRo);
    }

    @Override
    public OutputResult realList(TradeDealRo tradeDealRo) {
//        if (!tradeCacheService.needSyncReal(TradeRealValueType.TRADE_DEAL, tradeDealRo.getUserId())) {
//            return mockList(tradeDealRo);
//        }
        log.info(">>>此次员工{}查询需要同步真实的今日成交数据", tradeDealRo.getUserId());
        OutputResult<List<TradeDealVo>> outputResult = tradeDealService.realList(tradeDealRo);
        if (!outputResult.getSuccess()) {
            return outputResult;
        }
        //获取到最新的持仓信息，更新到相应的数据库中.
        List<TradeDealVo> tradeDealVoList = outputResult.getData();
        // 将数据保存下来
        tradeDealService.syncRealDealByUserId(tradeDealRo.getUserId(), tradeDealVoList);
        return outputResult;
    }

    @Override
    public OutputResult mockHistoryList(TradeDealRo tradeDealRo) {
        return tradeDealService.mockHistoryList(tradeDealRo);
    }

    @Override
    public OutputResult realHistoryList(TradeDealRo tradeDealRo) {
        return tradeDealService.realHistoryList(tradeDealRo);
    }



}
