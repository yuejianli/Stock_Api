package top.yueshushu.learn.business.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import top.yueshushu.learn.business.TradeEntrustBusiness;
import top.yueshushu.learn.enumtype.TradeRealValueType;
import top.yueshushu.learn.mode.ro.TradeEntrustRo;
import top.yueshushu.learn.mode.vo.TradeEntrustVo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.response.PageResponse;
import top.yueshushu.learn.service.TradeEntrustService;
import top.yueshushu.learn.service.cache.TradeCacheService;
import top.yueshushu.learn.util.PageUtil;

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

public class TradeEntrustBusinessImpl implements TradeEntrustBusiness {
    @Resource
    private TradeEntrustService tradeEntrustService;
    @Resource
    private TradeCacheService tradeCacheService;

    @Override
    public OutputResult<List<TradeEntrustVo>> mockList(TradeEntrustRo tradeEntrustRo) {
        return tradeEntrustService.mockList(tradeEntrustRo);
    }

    @Override
    public OutputResult<List<TradeEntrustVo>> realList(TradeEntrustRo tradeEntrustRo) {
        Object realEasyMoneyCache = tradeCacheService.getRealEasyMoneyCache(TradeRealValueType.TRADE_ENTRUST, tradeEntrustRo.getUserId());
        if (!ObjectUtils.isEmpty(realEasyMoneyCache)) {
            return OutputResult.buildSucc((List<TradeEntrustVo>) realEasyMoneyCache);
        }
        log.info(">>>此次员工{}查询需要同步真实的今日委托数据", tradeEntrustRo.getUserId());
        OutputResult<List<TradeEntrustVo>> outputResult = tradeEntrustService.realList(tradeEntrustRo);
        if (!outputResult.getSuccess()) {
            return outputResult;
        }
        //获取到最新的持仓信息，更新到相应的数据库中.
        List<TradeEntrustVo> tradePositionVoList = outputResult.getData();
        // 将数据保存下来
        tradeCacheService.buildRealEasyMoneyCache(TradeRealValueType.TRADE_ENTRUST, tradeEntrustRo.getUserId(), tradePositionVoList);
        return outputResult;
    }

    @Override
    public OutputResult<PageResponse<TradeEntrustVo>> mockHistoryList(TradeEntrustRo tradeEntrustRo) {
        return tradeEntrustService.mockHistoryList(tradeEntrustRo);
    }

    @Override
    public OutputResult<PageResponse<TradeEntrustVo>> realHistoryList(TradeEntrustRo tradeEntrustRo) {
        List<TradeEntrustVo> tradeEntrustVoList = tradeEntrustService.realHistoryList(tradeEntrustRo);
        if (CollectionUtils.isEmpty(tradeEntrustVoList)) {
            return OutputResult.buildSucc(PageResponse.emptyPageResponse());
        }
        return PageUtil.pageResult(tradeEntrustVoList, tradeEntrustRo.getPageNum(), tradeEntrustRo.getPageSize());
    }
}
