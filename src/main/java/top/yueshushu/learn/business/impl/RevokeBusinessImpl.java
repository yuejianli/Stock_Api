package top.yueshushu.learn.business.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.yueshushu.learn.assembler.TradePositionAssembler;
import top.yueshushu.learn.business.RevokeBusiness;
import top.yueshushu.learn.common.ResultCode;
import top.yueshushu.learn.domain.TradeEntrustDo;
import top.yueshushu.learn.domainservice.TradeEntrustDomainService;
import top.yueshushu.learn.entity.TradeMoney;
import top.yueshushu.learn.entity.TradePosition;
import top.yueshushu.learn.enumtype.DealType;
import top.yueshushu.learn.enumtype.EntrustStatusType;
import top.yueshushu.learn.mode.ro.RevokeRo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.service.TradeEntrustService;
import top.yueshushu.learn.service.TradeMoneyService;
import top.yueshushu.learn.service.TradePositionService;
import top.yueshushu.learn.util.BigDecimalUtil;

import javax.annotation.Resource;

/**
 * @Description 委托撤销
 * @Author yuejianli
 * @Date 2022/5/28 21:50
 **/
@Slf4j
@Service
public class RevokeBusinessImpl implements RevokeBusiness {

    @Resource
    private TradeMoneyService tradeMoneyService;
    @Resource
    private TradeEntrustService tradeEntrustService;
    @Resource
    private TradePositionService tradePositionService;
    @Resource
    private TradePositionAssembler tradePositionAssembler;
    @Resource
    private TradeEntrustDomainService tradeEntrustDomainService;

    @Override
    public OutputResult revoke(RevokeRo revokeRo) {
        //查询单号信息
        TradeEntrustDo tradeEntrustDo = tradeEntrustDomainService.getById(revokeRo.getId());
        if(null== tradeEntrustDo){
            return OutputResult.buildAlert(ResultCode.TRADE_ENTRUST_ID_EMPTY);
        }
        if(!tradeEntrustDo.getUserId().equals(revokeRo.getUserId())){
            return OutputResult.buildAlert(ResultCode.NO_AUTH);
        }
        if(!EntrustStatusType.ING.getCode().equals(tradeEntrustDo.getEntrustStatus())){
            return OutputResult.buildAlert(ResultCode.TRADE_ENTRUST_STATUS_ERROR);
        }
        //获取委托的类型
        Integer dealType = tradeEntrustDo.getDealType();
        if(DealType.BUY.getCode().equals(dealType)){
            return buyRevoke(tradeEntrustDo);
        }else{
            return sellRevoke(tradeEntrustDo);
        }
    }

    private OutputResult sellRevoke(TradeEntrustDo tradeEntrustDo) {
        //取消的话，改变这个记录的状态。
        tradeEntrustDo.setEntrustStatus(EntrustStatusType.REVOKE.getCode());
        //更新
        tradeEntrustDomainService.updateById(tradeEntrustDo);

        TradePosition tradePosition = tradePositionService.getPositionByCode(
                tradeEntrustDo.getUserId(),
                tradeEntrustDo.getMockType(),
                tradeEntrustDo.getCode()
        );
        if(tradePosition ==null){
            return OutputResult.buildAlert(ResultCode.TRADE_POSITION_NO);
        }
        tradePosition.setUseAmount(
                tradePosition.getUseAmount()
                        + tradeEntrustDo.getEntrustNum()
        );
        //更新
       tradePositionService.updateById(tradePosition);
        return OutputResult.buildSucc();
    }

    private OutputResult buyRevoke(TradeEntrustDo tradeEntrustDo) {
        //取消的话，改变这个记录的状态。
        tradeEntrustDo.setEntrustStatus(EntrustStatusType.REVOKE.getCode());
        //更新
        tradeEntrustDomainService.updateById(tradeEntrustDo);

        //将金额回滚
        TradeMoney tradeMoney =  tradeMoneyService.getByUserIdAndMockType(
                tradeEntrustDo.getUserId(),
                tradeEntrustDo.getMockType()
        );
        if(null== tradeMoney){
            return OutputResult.buildAlert(ResultCode.TRADE_NO_MONEY);
        }
        //获取到后，需要进行更新
        tradeMoney.setUseMoney(
                BigDecimalUtil.addBigDecimal(
                        tradeMoney.getUseMoney(),
                        tradeEntrustDo.getUseMoney()
                )
        );
        tradeMoney.setTakeoutMoney(
                BigDecimalUtil.addBigDecimal(
                        tradeMoney.getTakeoutMoney(),
                        tradeEntrustDo.getTakeoutMoney()
                )
        );
        tradeMoneyService.updateMoney(tradeMoney);
        return OutputResult.buildSucc();
    }
}
