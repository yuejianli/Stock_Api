package top.yueshushu.learn.service.impl;

import cn.hutool.core.date.DateUtil;
import top.yueshushu.learn.assembler.TradeRuleConditionAssembler;
import top.yueshushu.learn.common.ResultCode;
import top.yueshushu.learn.domainservice.TradeRuleConditionDomainService;
import top.yueshushu.learn.entity.TradeRuleCondition;
import top.yueshushu.learn.mode.ro.TradeRuleConditionRo;
import top.yueshushu.learn.domain.TradeRuleConditionDo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.service.TradeRuleConditionService;
import org.springframework.stereotype.Service;

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
public class TradeRuleConditionServiceImpl  implements TradeRuleConditionService {
    @Resource
    private TradeRuleConditionDomainService tradeRuleConditionDomainService;
    @Resource
    private TradeRuleConditionAssembler tradeRuleConditionAssembler;
    @Override
    public OutputResult listCondition() {
        return OutputResult.buildSucc(listAll());
    }

    @Override
    public OutputResult updateCondition(TradeRuleConditionRo tradeRuleConditionRo) {
        //根据id 查询信息
        Integer id = tradeRuleConditionRo.getId();
        //查询是否有此信息
        TradeRuleConditionDo dbCondtion = tradeRuleConditionDomainService.getById(id);
        if(dbCondtion==null){
            return OutputResult.buildAlert(ResultCode.RULE_CONDITION_ID_NOT_EXIST);
        }
        //进行修改
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
                        n-> tradeRuleConditionAssembler.doToEntity(n)
                ).collect(Collectors.toList());
    }
}
