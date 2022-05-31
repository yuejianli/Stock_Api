package top.yueshushu.learn.service.impl;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import top.yueshushu.learn.assembler.TradePositionAssembler;
import top.yueshushu.learn.domain.TradeEntrustDo;
import top.yueshushu.learn.entity.TradePosition;
import top.yueshushu.learn.enumtype.*;
import top.yueshushu.learn.mode.ro.SellRo;
import top.yueshushu.learn.mode.vo.ConfigVo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.service.ConfigService;
import top.yueshushu.learn.service.SellService;
import top.yueshushu.learn.service.TradeEntrustService;
import top.yueshushu.learn.service.TradePositionService;
import top.yueshushu.learn.common.SystemConst;
import top.yueshushu.learn.util.BigDecimalUtil;
import top.yueshushu.learn.util.StockUtil;

import javax.annotation.Resource;

/**
 * @ClassName:SellServiceImpl
 * @Description TODO
 * @Author 岳建立
 * @Date 2022/1/8 11:17
 * @Version 1.0
 **/
@Service
@Slf4j(topic="卖出股票:")
@Transactional
public class SellServiceImpl implements SellService {
    @Autowired
    private TradeEntrustService tradeEntrustService;
    @Autowired
    private TradePositionService tradePositionService;
    @Autowired
    private ConfigService configService;
    @Resource
    private TradePositionAssembler tradePositionAssembler;
    @Override
    public OutputResult sell(SellRo sellRo) {
        //对非空的验证信息
        if(!StringUtils.hasText(sellRo.getCode())){
            return OutputResult.buildAlert("请传入卖出的股票信息");
        }
        if(sellRo.getAmount()==null){
            return OutputResult.buildAlert("请传入卖出的股票股数信息");
        }
        if(sellRo.getPrice()==null){
            return OutputResult.buildAlert("请传入卖出的股票的价格信息");
        }
        //获取当前该股票的持仓数和可用数.
        TradePosition tradePosition = tradePositionService.getPositionByCode(
                sellRo.getUserId(),
                sellRo.getMockType(),
               sellRo.getCode()
        );
        if(tradePosition ==null){
            return OutputResult.buildAlert("没有持仓信息，无法卖出");
        }
        if(tradePosition.getUseAmount()<sellRo.getAmount()){
            return OutputResult.buildAlert("份额不足，请检查目前持仓数量");
        }
        tradePosition.setUseAmount(
                tradePosition.getUseAmount()
                - sellRo.getAmount()
        );
        //更新
       // tradePositionService.updateById(tradePositionAssembler.entityToDo(tradePosition));
        //获取对应的金额
        ConfigVo priceConfigVo = configService.getConfigByCode(
                sellRo.getUserId(),
                ConfigCodeType.TRAN_PRICE.getCode()
        );
        TradeEntrustDo tradeEntrustDo = new TradeEntrustDo();
        tradeEntrustDo.setCode(sellRo.getCode());
        tradeEntrustDo.setName(sellRo.getName());
        tradeEntrustDo.setEntrustDate(DateUtil.date());
        tradeEntrustDo.setDealType(DealType.SELL.getCode());
        tradeEntrustDo.setEntrustNum(sellRo.getAmount());
        tradeEntrustDo.setEntrustPrice(BigDecimalUtil.convertFour(sellRo.getPrice()));
        tradeEntrustDo.setEntrustStatus(EntrustStatusType.ING.getCode());
        tradeEntrustDo.setEntrustCode(StockUtil.generateEntrustCode());
        tradeEntrustDo.setUseMoney(SystemConst.DEFAULT_EMPTY);
        tradeEntrustDo.setTakeoutMoney(SystemConst.DEFAULT_EMPTY);

        tradeEntrustDo.setEntrustMoney(
                StockUtil.allMoney(
                        sellRo.getAmount(),
                        sellRo.getPrice()
                )
        );
        tradeEntrustDo.setHandMoney(
                StockUtil.getSellHandMoney(
                        sellRo.getAmount(),
                        sellRo.getPrice(),
                        BigDecimalUtil.toBigDecimal(priceConfigVo.getCodeValue())
                )
        );
        tradeEntrustDo.setTotalMoney(
                StockUtil.getSellMoney(
                        sellRo.getAmount(),
                        sellRo.getPrice(),
                        BigDecimalUtil.toBigDecimal(priceConfigVo.getCodeValue())
                )
        );
        tradeEntrustDo.setUserId(sellRo.getUserId());
        tradeEntrustDo.setEntrustType(EntrustType.HANDLER.getCode());
        tradeEntrustDo.setMockType(sellRo.getMockType());
        tradeEntrustDo.setFlag(DataFlagType.NORMAL.getCode());
        //放入一条记录到委托信息里面.
       // tradeEntrustService.save(tradeEntrustDo);
        return OutputResult.buildSucc("卖出股票委托成功");
    }
}
