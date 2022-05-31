package top.yueshushu.learn.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import top.yueshushu.learn.assembler.TradeRuleStockAssembler;
import top.yueshushu.learn.common.ResultCode;
import top.yueshushu.learn.domainservice.TradeRuleStockDomainService;
import top.yueshushu.learn.entity.TradeRuleStock;
import top.yueshushu.learn.enumtype.DealType;
import top.yueshushu.learn.mode.dto.StockRuleDto;
import top.yueshushu.learn.mode.dto.TradeRuleStockQueryDto;
import top.yueshushu.learn.mode.ro.TradeRuleStockRo;
import top.yueshushu.learn.mode.vo.StockRuleVo;
import top.yueshushu.learn.mode.vo.StockSelectedVo;
import top.yueshushu.learn.mode.vo.TradeRuleStockVo;
import top.yueshushu.learn.domain.TradeRuleDo;
import top.yueshushu.learn.domain.TradeRuleStockDo;
import top.yueshushu.learn.mapper.TradeRuleStockDoMapper;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.service.StockSelectedService;
import top.yueshushu.learn.service.TradeRuleService;
import top.yueshushu.learn.service.TradeRuleStockService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
                                    n.getStockCode(),""
                            )
                    );
                    otherApplyList.add(stockSelectedVo);
                }
        );
        return otherApplyList;
    }
}
