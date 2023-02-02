package top.yueshushu.learn.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.yueshushu.learn.business.BuyBusiness;
import top.yueshushu.learn.business.RevokeBusiness;
import top.yueshushu.learn.business.SellBusiness;
import top.yueshushu.learn.domain.TradeRuleDo;
import top.yueshushu.learn.domainservice.StockSelectedDomainService;
import top.yueshushu.learn.domainservice.TradeRuleDomainService;
import top.yueshushu.learn.entity.Stock;
import top.yueshushu.learn.entity.TradeEntrust;
import top.yueshushu.learn.entity.TradeRuleCondition;
import top.yueshushu.learn.enumtype.EntrustType;
import top.yueshushu.learn.enumtype.RuleConditionType;
import top.yueshushu.learn.mode.dto.TradeRuleStockQueryDto;
import top.yueshushu.learn.mode.ro.BuyRo;
import top.yueshushu.learn.mode.ro.RevokeRo;
import top.yueshushu.learn.service.*;
import top.yueshushu.learn.service.cache.StockCacheService;
import top.yueshushu.learn.strategy.bs.base.BaseStrategyHandler;
import top.yueshushu.learn.strategy.bs.model.TradeStockRuleDto;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName:TradeStrategyServiceImpl
 * @Description TODO
 * @Author zk_yjl
 * @Date 2022/1/11 20:33
 * @Version 1.0
 * @Since 1.0
 **/
@Service
@Slf4j
public class TradeStrategyServiceImpl implements TradeStrategyService {
    @Resource
    private StockSelectedDomainService stockSelectedDomainService;
    @Resource
    private BuyBusiness buyBusiness;
    @Resource
    private SellBusiness sellBusiness;
    @Resource
    private StockCacheService stockCacheService;
    @Resource
    private ConfigService configService;
    @Resource
    private TradeEntrustService tradeEntrustService;
    @Resource
    private RevokeBusiness revokeBusiness;
    @Resource
    private TradeRuleStockService tradeRuleStockService;
    @Resource
    private TradeRuleDomainService tradeRuleDomainService;
    @Resource
    private TradeRuleConditionService tradeRuleConditionService;

    @Override
    public void mockEntrustXxlJob(BuyRo buyRo) {

        //1. 获取该员工目前的自选股票编码 ----> 后续可以改成分组。
        List<String> codeList = stockSelectedDomainService.findCodeList(buyRo.getUserId());
        if (CollectionUtils.isEmpty(codeList)) {
            return;
        }

        // 对 codeList 进行处理，如果已经没有交易次数了， 那么就不处理这个股票信息。

        List<String> canBSStockList = new ArrayList<>();

        for (String code : codeList) {

            Long todayBuySurplusNum = stockCacheService.getTodayBuySurplusNum(buyRo.getUserId(), buyRo.getMockType(), code);
            Long todaySellSurplusNum = stockCacheService.getTodaySellSurplusNum(buyRo.getUserId(), buyRo.getMockType(), code);
            if (todayBuySurplusNum <= 0 && todaySellSurplusNum <= 0) {
                continue;
            }
            canBSStockList.add(code);
        }


        if (CollectionUtils.isEmpty(canBSStockList)) {
            return;
        }


        //2. 对每一个股票进行处理， 获取相应的规则。   如果没有规则，则不自动委托。
        Map<String, List<Integer>> stockRelationRuleIdMap = tradeRuleStockService.listRuleIdByCode(canBSStockList);
        if (CollectionUtils.isEmpty(stockRelationRuleIdMap)) {
            return;
        }
        // 获取所有的规则 id 集合。
        List<Integer> allRuleIdList = new ArrayList<>();
        stockRelationRuleIdMap.forEach((stockCode, ruleIdList) -> allRuleIdList.addAll(ruleIdList));
        // 根据规则 id 获取对应的数据信息.
        TradeRuleStockQueryDto tradeRuleStockQueryDto = new TradeRuleStockQueryDto();
        tradeRuleStockQueryDto.setRuleIdList(allRuleIdList);
        tradeRuleStockQueryDto.setMockType(buyRo.getMockType());
        tradeRuleStockQueryDto.setUserId(buyRo.getUserId());
        List<TradeRuleDo> tradeRuleDoList = tradeRuleDomainService.listByQuery(tradeRuleStockQueryDto);
        // 转换成对应的 Map
        Map<Integer, TradeRuleDo> tradeRuleIdMap = tradeRuleDoList.stream().collect(Collectors.toMap(TradeRuleDo::getId, n -> n));
        //查询该员工最开始的收盘价
        List<TradeRuleCondition> tradeRuleConditions = tradeRuleConditionService.listAll();
        Map<Integer, TradeRuleCondition> tradeRuleConditionMap = tradeRuleConditions.stream().collect(Collectors.toMap(TradeRuleCondition::getId, n -> n));
        for (String code : canBSStockList) {
            // 获取对应的规则
            List<Integer> ruleIdList = stockRelationRuleIdMap.get(code);
            if (CollectionUtils.isEmpty(ruleIdList) || ruleIdList.size() > 2) {
                continue;
            }
            Stock stock = stockCacheService.selectByCode(code);
            for (Integer ruleId : ruleIdList) {
                TradeRuleDo tradeRuleDo = tradeRuleIdMap.get(ruleId);
                if (tradeRuleDo == null) {
                    continue;
                }
                // 获取对应的规则编码集合.
                TradeRuleCondition tradeRuleCondition = tradeRuleConditionMap.get(tradeRuleDo.getConditionId());
                if (null == tradeRuleCondition) {
                    continue;
                }

                // 获取条件编码对应的策略。
                RuleConditionType ruleConditionType = RuleConditionType.getByConditionCode(tradeRuleCondition.getCode(), tradeRuleDo.getRuleType());
                if (null == ruleConditionType) {
                    continue;
                }
                // 执行该程序。
                BaseStrategyHandler baseStrategyHandler =
                        SpringUtil.getBean(ruleConditionType.getBeanName(), BaseStrategyHandler.class);

                TradeStockRuleDto tradeStockRuleDto = new TradeStockRuleDto();
                BeanUtils.copyProperties(tradeRuleDo, tradeStockRuleDto);
                tradeStockRuleDto.setStockCode(code);
                tradeStockRuleDto.setStockName(stock.getName());
                // 执行策略
                baseStrategyHandler.handle(tradeStockRuleDto);
            }
        }
    }

    @Override
    public void revokeEntrustJob(Integer userId, Integer mockType) {
        //获取当前所有的今日委托单信息，正在委托的.
        List<TradeEntrust> tradeEntrustDoList = tradeEntrustService.listNowRunEntrust(userId, mockType);
        if (CollectionUtils.isEmpty(tradeEntrustDoList)) {
            return;
        }
        //进行处理.
        for (TradeEntrust tradeEntrustDo : tradeEntrustDoList) {
            RevokeRo revokeRo = new RevokeRo();
            revokeRo.setUserId(userId);
            revokeRo.setMockType(mockType);
            revokeRo.setId(tradeEntrustDo.getId());
            revokeRo.setEntrustType(EntrustType.AUTO.getCode());
            revokeBusiness.revoke(revokeRo);
        }
    }
}
