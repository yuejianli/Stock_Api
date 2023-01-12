package top.yueshushu.learn.service.impl;

import cn.hutool.core.date.DateUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.yueshushu.learn.assembler.TradeRuleConditionAssembler;
import top.yueshushu.learn.common.ResultCode;
import top.yueshushu.learn.domain.TradeRuleConditionDo;
import top.yueshushu.learn.domainservice.TradeRuleConditionDomainService;
import top.yueshushu.learn.domainservice.TradeRuleDomainService;
import top.yueshushu.learn.entity.TradeRuleCondition;
import top.yueshushu.learn.enumtype.DataFlagType;
import top.yueshushu.learn.mode.dto.StockRuleDto;
import top.yueshushu.learn.mode.dto.TradeRuleStockQueryDto;
import top.yueshushu.learn.mode.ro.TradeRuleConditionRo;
import top.yueshushu.learn.mode.vo.TradeRuleConditionVo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.service.TradeRuleConditionService;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 交易规则可使用的条件表 自定义的
 * </p>
 *
 * @author 两个蝴蝶飞
 * @since 2022-01-26
 */
@Service
public class TradeRuleConditionServiceImpl implements TradeRuleConditionService {
    @Resource
    private TradeRuleConditionDomainService tradeRuleConditionDomainService;
    @Resource
    private TradeRuleConditionAssembler tradeRuleConditionAssembler;
    @Resource
    private TradeRuleDomainService tradeRuleDomainService;

    @Override
    public OutputResult<List<TradeRuleConditionVo>> listCondition() {
        List<TradeRuleCondition> tradeRuleConditionList = listAll();
        return OutputResult.buildSucc(tradeRuleConditionAssembler.entityToVoList(tradeRuleConditionList));
    }

    @Override
    public OutputResult updateCondition(TradeRuleConditionRo tradeRuleConditionRo) {
        //根据id 查询信息
        Integer id = tradeRuleConditionRo.getId();
        //查询是否有此信息
        TradeRuleConditionDo dbCondtion = tradeRuleConditionDomainService.getById(id);
        if (dbCondtion == null) {
            return OutputResult.buildAlert(ResultCode.RULE_CONDITION_ID_NOT_EXIST);
        }
        //进行修改, 不对 code 进行修改。
        dbCondtion.setName(
                tradeRuleConditionRo.getName()
        );
        dbCondtion.setDescription(
                tradeRuleConditionRo.getDescription()
        );
        dbCondtion.setUpdateTime(
                DateUtil.date()
        );
        tradeRuleConditionDomainService.updateById(dbCondtion);
        return OutputResult.buildSucc();
    }

    @Override
    public List<TradeRuleCondition> listAll() {
        List<TradeRuleConditionDo> tradeRuleConditionDoList = tradeRuleConditionDomainService.list();
        return tradeRuleConditionDoList.stream().
                map(
                        n -> tradeRuleConditionAssembler.doToEntity(n)
                ).collect(Collectors.toList());
    }

    @Override
    public OutputResult addCondition(TradeRuleConditionRo tradeRuleConditionRo) {
        TradeRuleConditionDo tradeRuleConditionDo = tradeRuleConditionDomainService.getByCode(tradeRuleConditionRo.getCode());
        if (tradeRuleConditionDo != null) {
            return OutputResult.buildAlert(ResultCode.RULE_CONDITION_CODE_EXIST);
        }
        // 进行保存
        TradeRuleCondition tradeRuleCondition = new TradeRuleCondition();
        tradeRuleCondition.setCode(tradeRuleConditionRo.getCode());
        tradeRuleCondition.setName(tradeRuleConditionRo.getName());
        tradeRuleCondition.setDescription(tradeRuleConditionRo.getDescription());
        tradeRuleCondition.setCreateTime(DateUtil.date());
        tradeRuleCondition.setUpdateTime(DateUtil.date());
        tradeRuleCondition.setFlag(DataFlagType.NORMAL.getCode());
        tradeRuleConditionDomainService.save(tradeRuleConditionAssembler.entityToDo(tradeRuleCondition));
        return OutputResult.buildSucc();
    }

    @Override
    public OutputResult deleteCondition(Integer id) {
        TradeRuleStockQueryDto tradeRuleStockQueryDto = new TradeRuleStockQueryDto();
        tradeRuleStockQueryDto.setRuleConditionId(id);
        List<StockRuleDto> list = tradeRuleDomainService.listStockRuleByQuery(tradeRuleStockQueryDto);
        if (!CollectionUtils.isEmpty(list)) {
            return OutputResult.buildAlert(ResultCode.RULE_CONDITION_USE);
        }
        tradeRuleConditionDomainService.removeById(id);
        return OutputResult.buildSucc();
    }
}
