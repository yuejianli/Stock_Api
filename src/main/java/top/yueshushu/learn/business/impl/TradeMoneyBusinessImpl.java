package top.yueshushu.learn.business.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.yueshushu.learn.assembler.TradeMoneyAssembler;
import top.yueshushu.learn.business.TradeMoneyBusiness;
import top.yueshushu.learn.entity.TradeMoney;
import top.yueshushu.learn.enumtype.TradeRealValueType;
import top.yueshushu.learn.mode.ro.TradeMoneyRo;
import top.yueshushu.learn.mode.vo.TradeMoneyVo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.service.TradeMoneyService;
import top.yueshushu.learn.service.cache.TradeCacheService;

import javax.annotation.Resource;

/**
 * 持仓
 *
 * @author Yue Jianli
 * @date 2022-05-26
 */
@Service
@Slf4j
public class TradeMoneyBusinessImpl implements TradeMoneyBusiness {
    @Resource
    private TradeMoneyService tradeMoneyService;

    @Resource
    private TradeCacheService tradeCacheService;

    @Resource
    private TradeMoneyAssembler tradeMoneyAssembler;

    @Override
    public OutputResult mockInfo(TradeMoneyRo tradeMoneyRo) {
       return tradeMoneyService.mockInfo(tradeMoneyRo);
    }

    @Override
    public OutputResult realInfo(TradeMoneyRo tradeMoneyRo) {
        if (!tradeCacheService.needSyncReal(TradeRealValueType.TRADE_MONEY, tradeMoneyRo.getUserId())) {
             return mockInfo(tradeMoneyRo);
        }
        log.info(">>>此次员工{}查询需要同步真实的持仓数据",tradeMoneyRo.getUserId());
        OutputResult<TradeMoneyVo> tradeMoneyVoOutputResult = tradeMoneyService.realInfo(tradeMoneyRo);
        if (!tradeMoneyVoOutputResult.getSuccess()){
            return tradeMoneyVoOutputResult;
        }
        //获取数据
        TradeMoneyVo tradeMoneyVo = tradeMoneyVoOutputResult.getData();
        TradeMoney tradeMoney = tradeMoneyAssembler.voToEntity(tradeMoneyVo);
        //进行更新
        tradeMoneyService.updateMoney(tradeMoney);
        return tradeMoneyVoOutputResult;
    }

}
