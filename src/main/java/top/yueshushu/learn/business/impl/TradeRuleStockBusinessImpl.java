package top.yueshushu.learn.business.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.yueshushu.learn.business.TradeRuleStockBusiness;
import top.yueshushu.learn.common.ResultCode;
import top.yueshushu.learn.domain.TradeRuleStockDo;
import top.yueshushu.learn.domainservice.TradeRuleStockDomainService;
import top.yueshushu.learn.entity.TradeRule;
import top.yueshushu.learn.entity.TradeRuleStock;
import top.yueshushu.learn.enumtype.DealType;
import top.yueshushu.learn.mode.dto.StockRuleDto;
import top.yueshushu.learn.mode.dto.TradeRuleStockQueryDto;
import top.yueshushu.learn.mode.ro.TradeRuleStockRo;
import top.yueshushu.learn.mode.vo.StockRuleVo;
import top.yueshushu.learn.mode.vo.StockSelectedVo;
import top.yueshushu.learn.mode.vo.TradeRuleStockVo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.response.PageResponse;
import top.yueshushu.learn.service.StockSelectedService;
import top.yueshushu.learn.service.TradeRuleService;
import top.yueshushu.learn.service.TradeRuleStockService;
import top.yueshushu.learn.util.PageUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description 交易规则条件的信息实现
 * @Author 岳建立
 * @Date 2022/5/20 22:33
 **/
@Service
@Slf4j
public class TradeRuleStockBusinessImpl implements TradeRuleStockBusiness {
    @Resource
    private TradeRuleStockService tradeRuleStockService;
    @Resource
    private StockSelectedService stockSelectedService;
    @Resource
    private TradeRuleService tradeRuleService;
    @Resource
    private TradeRuleStockDomainService tradeRuleStockDomainService;


    @Override
    public OutputResult applyList(TradeRuleStockRo tradeRuleStockRo) {
        TradeRule dbTradeRule = tradeRuleService.getById(tradeRuleStockRo.getId());
        if(null== dbTradeRule){
            return OutputResult.buildAlert(ResultCode.RULE_ID_NOT_EXIST);
        }
        if(!dbTradeRule.getUserId().equals(tradeRuleStockRo.getUserId())){
            return OutputResult.buildAlert(ResultCode.NO_AUTH);
        }
        //查询所有的自选股票信息.
        List<StockSelectedVo> allSelectedStockList = stockSelectedService.listSelf(
                dbTradeRule.getUserId(),""
        );
        //进行对应
        if(CollectionUtils.isEmpty(allSelectedStockList)){
            return OutputResult.buildSucc(
                    new TradeRuleStockVo()
            );
        }
        Map<String, String> stockNameMap = allSelectedStockList.stream().collect(
                Collectors.toMap(
                        StockSelectedVo::getStockCode,
                        StockSelectedVo::getStockName
                )
        );
        //查询当前自选的股票信息
        List<TradeRuleStock> ruleStockList = tradeRuleStockService.listByRuleId(tradeRuleStockRo.getId());
        //处理成相应的 Vo 形式
        List<StockSelectedVo> applyList= tradeRuleStockService.ruleStockToStockVo(stockNameMap, ruleStockList);
        TradeRuleStockQueryDto tradeRuleStockQueryDto = new TradeRuleStockQueryDto();
        tradeRuleStockQueryDto.setUserId(tradeRuleStockRo.getUserId());
        tradeRuleStockQueryDto.setRuleId(dbTradeRule.getId());
        tradeRuleStockQueryDto.setRuleType(dbTradeRule.getRuleType());
        tradeRuleStockQueryDto.setMockType(dbTradeRule.getMockType());
        List<TradeRuleStock> other = tradeRuleStockService.listNotInRuleId(
                tradeRuleStockQueryDto
        );
        List<StockSelectedVo> otherApplyList =  tradeRuleStockService.ruleStockToStockVo(stockNameMap, other);
        //设置信息
        TradeRuleStockVo tradeRuleStockVo = new TradeRuleStockVo();
        tradeRuleStockVo.setAllList(allSelectedStockList);
        if(!CollectionUtils.isEmpty(applyList)){
            tradeRuleStockVo.setApplyList(applyList);
        }
        if(!CollectionUtils.isEmpty(otherApplyList)){
            tradeRuleStockVo.setOtherApplyList(otherApplyList);
        }
        return OutputResult.buildSucc(tradeRuleStockVo);
    }

    @Override
    public OutputResult apply(TradeRuleStockRo tradeRuleStockRo) {
        TradeRule dbTradeRule = tradeRuleService.getById(tradeRuleStockRo.getId());
        if(null== dbTradeRule){
            return OutputResult.buildAlert(ResultCode.RULE_ID_NOT_EXIST);
        }
        if(!dbTradeRule.getUserId().equals(tradeRuleStockRo.getUserId())){
            return OutputResult.buildAlert(ResultCode.NO_AUTH);
        }
        //先将以前的该规则配置的股票全部删除
        LambdaQueryWrapper<TradeRuleStockDo> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(TradeRuleStockDo::getRuleId,tradeRuleStockRo.getId());
        tradeRuleStockDomainService.remove(lambdaQueryWrapper);

        //如果有其他的移除的，就将移除的去掉.
        if(!CollectionUtils.isEmpty(tradeRuleStockRo.getRemoveCodeList())){
            //进行移除。
            tradeRuleStockDomainService.removeOtherStock(
                    dbTradeRule.getUserId(),
                    dbTradeRule.getMockType(),
                    tradeRuleStockRo.getRemoveCodeList()
            );
        }
        Date now = DateUtil.date();
        //进行添加操作
        if(!CollectionUtils.isEmpty(tradeRuleStockRo.getApplyCodeList())){
            List<TradeRuleStockDo> tradeRuleStockDoList = new ArrayList<>();
            for(String stockCode:tradeRuleStockRo.getApplyCodeList()){

                TradeRuleStockDo tradeRuleStockDo = new TradeRuleStockDo();
                //进行设置
                tradeRuleStockDo.setRuleId(tradeRuleStockRo.getId());
                tradeRuleStockDo.setStockCode(stockCode);
                tradeRuleStockDo.setCreateTime(now);
                tradeRuleStockDoList.add(tradeRuleStockDo);
            }
            tradeRuleStockDomainService.saveBatch(tradeRuleStockDoList);
        }
        return OutputResult.buildSucc();
    }

    @Override
    public OutputResult stockRuleList(TradeRuleStockRo tradeRuleStockRo) {
        //查询相应的股票信息信息
        List<StockSelectedVo> stockSelectedVoList =
                stockSelectedService.listSelf(tradeRuleStockRo.getUserId(), tradeRuleStockRo.getKeyword());
        if(CollectionUtils.isEmpty(stockSelectedVoList)){
            return OutputResult.buildSucc(PageResponse.emptyPageResponse());
        }
        //每一个股票，去查询对应的信息
        List<StockRuleVo> stockRuleVoList = new ArrayList<>();
        for(StockSelectedVo stockSelectedVo:stockSelectedVoList){
            StockRuleVo  stockRuleVo = new StockRuleVo();
            stockRuleVo.setStockCode(stockSelectedVo.getStockCode());
            stockRuleVo.setStockName(stockSelectedVo.getStockName());
            //设置买入的规则
            TradeRuleStockQueryDto tradeRuleStockQueryDto = new TradeRuleStockQueryDto();
            tradeRuleStockQueryDto.setUserId(tradeRuleStockRo.getUserId());
            tradeRuleStockQueryDto.setMockType(tradeRuleStockRo.getMockType());
            tradeRuleStockQueryDto.setRuleType(DealType.BUY.getCode());
            tradeRuleStockQueryDto.setStockCode(stockSelectedVo.getStockCode());
            List<StockRuleDto> stockRuleDtoList = tradeRuleService.getRuleByQuery(tradeRuleStockQueryDto);
            //如果不为空的话，获取第一个
            if(!CollectionUtils.isEmpty(stockRuleDtoList)){
                StockRuleDto stockRuleDto = stockRuleDtoList.get(0);
                //处理信息
                stockRuleVo.setBuyRuleId(stockRuleDto.getRuleId());
                stockRuleVo.setBuyRuleName(stockRuleDto.getRuleName());
                stockRuleVo.setBuyCreateTime(stockRuleDto.getCreateTime());
                stockRuleVo.setBuyRuleStatus(stockRuleDto.getStatus());
            }
            tradeRuleStockQueryDto.setRuleType(DealType.SELL.getCode());
            stockRuleDtoList = tradeRuleService.getRuleByQuery(tradeRuleStockQueryDto);
            //如果不为空的话，获取第一个
            if(!CollectionUtils.isEmpty(stockRuleDtoList)){
                StockRuleDto stockRuleDto = stockRuleDtoList.get(0);
                //处理信息
                stockRuleVo.setSellRuleId(stockRuleDto.getRuleId());
                stockRuleVo.setSellRuleName(stockRuleDto.getRuleName());
                stockRuleVo.setSellCreateTime(stockRuleDto.getCreateTime());
                stockRuleVo.setSellRuleStatus(stockRuleDto.getStatus());
            }
            //设置卖出的规则
            stockRuleVoList.add(stockRuleVo);
        }
        return PageUtil.pageResult(stockRuleVoList, tradeRuleStockRo.getPageNum(), tradeRuleStockRo.getPageSize());
    }
}
