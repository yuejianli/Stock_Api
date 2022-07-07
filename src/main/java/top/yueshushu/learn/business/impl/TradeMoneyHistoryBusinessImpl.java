package top.yueshushu.learn.business.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;
import top.yueshushu.learn.business.TradeMoneyHistoryBusiness;
import top.yueshushu.learn.mode.ro.TradeMoneyRo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.service.TradeMoneyHistoryService;

/**
 * 持仓历史记录表
 *
 * @author Yue Jianli
 * @date 2022-05-26
 */
@Service
@Slf4j
public class TradeMoneyHistoryBusinessImpl implements TradeMoneyHistoryBusiness {
    @Resource
    private TradeMoneyHistoryService tradeMoneyHistoryService;
    
    @Override
    public OutputResult listHistory(TradeMoneyRo tradeMoneyRo) {
        return tradeMoneyHistoryService.pageHistory(
                tradeMoneyRo
        );
    }
}
