package top.yueshushu.learn.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.yueshushu.learn.assembler.TradeRuleStockAssembler;
import top.yueshushu.learn.domain.TradeRuleStockDo;
import top.yueshushu.learn.domainservice.TradeRuleStockDomainService;
import top.yueshushu.learn.entity.TradeRuleStock;
import top.yueshushu.learn.mode.dto.TradeRuleStockQueryDto;
import top.yueshushu.learn.mode.vo.StockSelectedVo;
import top.yueshushu.learn.service.TradeRuleStockService;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 规则股票对应信息表 自定义的
 * </p>
 *
 * @author 两个蝴蝶飞
 * @since 2022-01-26
 */
@Service
public class TradeRuleStockServiceImpl  implements TradeRuleStockService {

    @Resource
    private TradeRuleStockDomainService tradeRuleStockDomainService;
    @Resource
    private TradeRuleStockAssembler tradeRuleStockAssembler;

    @Override
    public List<TradeRuleStock> listByRuleId(Integer ruleId) {
        List<TradeRuleStockDo> tradeRuleStockDoList = tradeRuleStockDomainService.listByRuleId(
                ruleId
        );
        return tradeRuleStockDoList.stream().map(
                n-> tradeRuleStockAssembler.doToEntity(n)
        ).collect(Collectors.toList());
    }

    @Override
    public List<TradeRuleStock> listNotInRuleId(TradeRuleStockQueryDto tradeRuleStockQueryDto) {
        List<TradeRuleStockDo> tradeRuleStockDoList = tradeRuleStockDomainService.listNotInRuleId(
                tradeRuleStockQueryDto
        );
        return tradeRuleStockDoList.stream().map(
                n-> tradeRuleStockAssembler.doToEntity(n)
        ).collect(Collectors.toList());
    }
    @Override
    public List<StockSelectedVo> ruleStockToStockVo(Map<String, String> stockNameMap, List<TradeRuleStock> other) {
        List<StockSelectedVo> otherApplyList = new ArrayList<>();
        other.stream().forEach(
                n->{
                    StockSelectedVo stockSelectedVo = new StockSelectedVo();
                    stockSelectedVo.setStockCode(n.getStockCode());
                    stockSelectedVo.setStockName(
                            stockNameMap.getOrDefault(
                                    n.getStockCode(), ""
                            )
                    );
                    otherApplyList.add(stockSelectedVo);
                }
        );
        return otherApplyList;
    }

    @Override
    public Map<String, List<Integer>> listRuleIdByCode(List<String> codeList) {
        List<TradeRuleStockDo> tradeRuleStockDoList = tradeRuleStockDomainService.listByCodeList(codeList);
        if (CollectionUtils.isEmpty(tradeRuleStockDoList)) {
            return Collections.emptyMap();
        }
        // 转换成 map
        Map<String, List<Integer>> result = new HashMap<>(codeList.size(), 1.0f);

        for (TradeRuleStockDo tradeRuleStockDo : tradeRuleStockDoList) {
            if (result.containsKey(tradeRuleStockDo.getStockCode())) {
                result.get(tradeRuleStockDo.getStockCode()).add(tradeRuleStockDo.getRuleId());
            } else {
                List<Integer> tempRuleIdList = new ArrayList<>();
                tempRuleIdList.add(tradeRuleStockDo.getRuleId());
                result.put(tradeRuleStockDo.getStockCode(), tempRuleIdList);
            }
        }
        return result;
    }
}
