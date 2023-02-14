package top.yueshushu.learn.business.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import top.yueshushu.learn.business.TradeDealBusiness;
import top.yueshushu.learn.enumtype.TradeRealValueType;
import top.yueshushu.learn.mode.ro.TradeDealRo;
import top.yueshushu.learn.mode.vo.TradeDealVo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.response.PageResponse;
import top.yueshushu.learn.service.TradeDealService;
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

public class TradeDealBusinessImpl implements TradeDealBusiness {
    @Resource
    private TradeDealService tradeDealService;
    @Resource
    private TradeCacheService tradeCacheService;

    @Override
    public OutputResult<List<TradeDealVo>> mockList(TradeDealRo tradeDealRo) {
        return tradeDealService.mockList(tradeDealRo);
    }

    @Override
    public OutputResult<List<TradeDealVo>> realList(TradeDealRo tradeDealRo) {
        Object realEasyMoneyCache = tradeCacheService.getRealEasyMoneyCache(TradeRealValueType.TRADE_DEAL, tradeDealRo.getUserId());
        if (!ObjectUtils.isEmpty(realEasyMoneyCache)) {
            return OutputResult.buildSucc((List<TradeDealVo>) realEasyMoneyCache);
        }
        log.info(">>>此次员工{}查询需要同步真实的今日成交数据", tradeDealRo.getUserId());
        OutputResult<List<TradeDealVo>> outputResult = tradeDealService.realList(tradeDealRo);
        if (!outputResult.getSuccess()) {
            return outputResult;
        }
        //获取到最新的持仓信息，更新到相应的数据库中.
        List<TradeDealVo> tradeDealVoList = outputResult.getData();
        // 将数据保存下来
        tradeCacheService.buildRealEasyMoneyCache(TradeRealValueType.TRADE_DEAL, tradeDealRo.getUserId(), tradeDealVoList, null);
        return outputResult;
    }

    @Override
    public OutputResult<PageResponse<TradeDealVo>> mockHistoryList(TradeDealRo tradeDealRo) {
        return tradeDealService.mockHistoryList(tradeDealRo);
    }

    @Override
    public OutputResult<PageResponse<TradeDealVo>> realHistoryList(TradeDealRo tradeDealRo) {
        List<TradeDealVo> tradeDealVoList = tradeDealService.realHistoryList(tradeDealRo);
        if (CollectionUtils.isEmpty(tradeDealVoList)) {
            // 为空
            return OutputResult.buildSucc(PageResponse.emptyPageResponse());
        }
        List<TradeDealVo> list = PageUtil.startPage(tradeDealVoList, tradeDealRo.getPageNum(),
                tradeDealRo.getPageSize());
        return OutputResult.buildSucc(new PageResponse<>((long) list.size(),
                list));
    }
}
