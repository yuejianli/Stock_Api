package top.yueshushu.learn.service.impl;
import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import top.yueshushu.learn.assembler.TradeRuleAssembler;
import top.yueshushu.learn.common.ResultCode;
import top.yueshushu.learn.domain.StockDo;
import top.yueshushu.learn.domainservice.TradeRuleDomainService;
import top.yueshushu.learn.entity.TradeRule;
import top.yueshushu.learn.entity.TradeRuleCondition;
import top.yueshushu.learn.entity.TradeRuleStock;
import top.yueshushu.learn.enumtype.ConditionType;
import top.yueshushu.learn.enumtype.DataFlagType;
import top.yueshushu.learn.enumtype.DealType;
import top.yueshushu.learn.mode.dto.StockRuleDto;
import top.yueshushu.learn.mode.dto.TradeRuleStockQueryDto;
import top.yueshushu.learn.mode.ro.TradeRuleRo;
import top.yueshushu.learn.mode.vo.StockVo;
import top.yueshushu.learn.mode.vo.TradeRuleVo;
import top.yueshushu.learn.response.PageResponse;
import top.yueshushu.learn.domain.TradeRuleDo;
import top.yueshushu.learn.mapper.TradeRuleDoMapper;
import top.yueshushu.learn.domain.TradeRuleConditionDo;
import top.yueshushu.learn.domain.TradeRuleStockDo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.service.TradeRuleConditionService;
import top.yueshushu.learn.service.TradeRuleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.yueshushu.learn.service.TradeRuleStockService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 交易规则表 自定义的
 * </p>
 *
 * @author 两个蝴蝶飞
 * @since 2022-01-26
 */
@Service
@Slf4j
public class TradeRuleServiceImpl implements TradeRuleService {
    @Resource
    private TradeRuleStockService tradeRuleStockService;
    @Resource
    private TradeRuleConditionService tradeRuleConditionService;
    @Resource
    private TradeRuleDomainService tradeRuleDomainService;
    @Resource
    private TradeRuleAssembler tradeRuleAssembler;

    @Override
    public OutputResult listRule(TradeRuleRo tradeRuleRo) {


        Page<Object> pageGithubResult = PageHelper.startPage(tradeRuleRo.getPageNum(), tradeRuleRo.getPageSize());
        List<TradeRuleDo> tradeRuleDoList = tradeRuleDomainService.listByQuery(
                tradeRuleRo.getUserId(),
                tradeRuleRo.getMockType(),
                tradeRuleRo.getRuleType()
        );
        if (CollectionUtils.isEmpty(tradeRuleDoList)){
            return OutputResult.buildSucc(
                    PageResponse.emptyPageResponse()
            );
        }

        List<TradeRuleVo> pageResultList = convertVo(tradeRuleDoList);

        PageInfo pageInfo=new PageInfo<>(pageResultList);
        return OutputResult.buildSucc(new PageResponse<TradeRuleVo>(pageGithubResult.getTotal(),
                pageInfo.getList()));
    }

    /**
     * 数据转换处理
     * @param records 数据存储对象
     * @return 数据转换处理
     */
    private List<TradeRuleVo> convertVo(List<TradeRuleDo> records) {
        //查询全部的规则信息.
        List<TradeRuleCondition> tradeRuleConditionList = tradeRuleConditionService.listAll();
        //转换成对应的Map 形式
        Map<String, String> condtionNameMap = tradeRuleConditionList.stream().collect(Collectors.toMap(
                TradeRuleCondition::getCode,
                TradeRuleCondition::getName
        ));
        List<TradeRuleVo> result = new ArrayList<>();
        records.stream().forEach(
                n->{
                    TradeRuleVo tradeRuleVo = new TradeRuleVo();
                    BeanUtils.copyProperties(n,tradeRuleVo);
                    tradeRuleVo.setConditionName(
                            condtionNameMap.getOrDefault(
                                    n.getConditionCode(),""
                            )
                    );
                    result.add(tradeRuleVo);
                }
        );
        return result;
    }

    @Override
    public OutputResult addRule(TradeRuleRo tradeRuleRo) {

        TradeRule tradeRule = tradeRuleAssembler.roToEntity(tradeRuleRo);
        //为禁用状态
        tradeRule.setStatus(DataFlagType.DELETE.getCode());
        //根据不同的类型，设置不同的信息.
        if(DealType.BUY.equals(tradeRuleRo.getRuleType())){
             //是买入规则
            tradeRule.setConditionType(ConditionType.LT.getCode());
        }else{
            //是卖出规则
            tradeRule.setConditionType(ConditionType.GT.getCode());
        }
        tradeRule.setCreateTime(DateUtil.date());
        tradeRule.setFlag(DataFlagType.NORMAL.getCode());
        //进行添加规则
        tradeRuleDomainService.save(
                tradeRuleAssembler.entityToDo(tradeRule)
        );
        return OutputResult.buildSucc();
    }

    @Override
    public OutputResult updateRule(TradeRuleRo tradeRuleRo) {
        OutputResult outputResult = validRule(tradeRuleRo);
        if(!outputResult.getSuccess()){
            return outputResult;
        }
        TradeRuleDo dbRule = (TradeRuleDo) outputResult.getData();
        //进行更新
        dbRule.setName(tradeRuleRo.getName());
        dbRule.setConditionCode(tradeRuleRo.getConditionCode());
        dbRule.setRuleValueType(tradeRuleRo.getRuleValueType());
        dbRule.setRuleValue(tradeRuleRo.getRuleValue());
        dbRule.setTradeNum(tradeRuleRo.getTradeNum());
        dbRule.setTradeValueType(tradeRuleRo.getTradeValueType());
        dbRule.setTradePrice(tradeRuleRo.getTradePrice());
        dbRule.setUpdateTime(DateUtil.date());
        tradeRuleDomainService.updateById(dbRule);
        return OutputResult.buildSucc();
    }

    @Override
    public OutputResult enableRule(TradeRuleRo tradeRuleRo) {
        OutputResult outputResult = validRule(tradeRuleRo);
        if(!outputResult.getSuccess()){
            return outputResult;
        }
        TradeRuleDo dbRule = (TradeRuleDo) outputResult.getData();
        dbRule.setStatus(DataFlagType.NORMAL.getCode());
        dbRule.setUpdateTime(DateUtil.date());
        tradeRuleDomainService.updateById(dbRule);
        return OutputResult.buildSucc();
    }

    @Override
    public OutputResult disableRule(TradeRuleRo tradeRuleRo) {
        OutputResult outputResult = validRule(tradeRuleRo);
        if(!outputResult.getSuccess()){
            return outputResult;
        }
        TradeRuleDo dbRule = (TradeRuleDo) outputResult.getData();
        dbRule.setStatus(DataFlagType.DELETE.getCode());
        dbRule.setUpdateTime(DateUtil.date());
        tradeRuleDomainService.updateById(dbRule);
        return OutputResult.buildSucc();
    }

    @Override
    public OutputResult deleteRule(TradeRuleRo tradeRuleRo) {
        OutputResult outputResult = validRule(tradeRuleRo);
        if(!outputResult.getSuccess()){
            return outputResult;
        }
        TradeRuleDo dbRule = (TradeRuleDo) outputResult.getData();
        if(DataFlagType.NORMAL.getCode().equals(dbRule.getStatus())){
            return OutputResult.buildAlert("禁用状态下，才可以删除规则信息");
        }
        //看是否有对应的适用股票信息
        List<TradeRuleStock> tradeRuleStockDoList = tradeRuleStockService.listByRuleId(tradeRuleRo.getId());
        if(!CollectionUtils.isEmpty(tradeRuleStockDoList)){
            return OutputResult.buildAlert("有正在配置的股票信息，无法删除");
        }
        dbRule.setStatus(DataFlagType.DELETE.getCode());
        dbRule.setFlag(DataFlagType.DELETE.getCode());
        dbRule.setUpdateTime(DateUtil.date());
        tradeRuleDomainService.removeById(dbRule.getId());
        return OutputResult.buildSucc();
    }

    @Override
    public List<StockRuleDto> getRuleByQuery(TradeRuleStockQueryDto tradeRuleStockQueryDto) {
        return tradeRuleDomainService.getRuleByQuery(tradeRuleStockQueryDto);
    }

    @Override
    public TradeRule getById(Integer id) {
        return tradeRuleAssembler.doToEntity(
                tradeRuleDomainService.getById(id)
        );
    }

    /**
     * 验证交易规则信息
     * @param tradeRuleRo 交易规则对象
     * @return 返回验证的信息
     */
    private OutputResult validRule(TradeRuleRo tradeRuleRo){
        if(tradeRuleRo.getId()==null){
            return OutputResult.buildAlert(ResultCode.ID_IS_EMPTY);
        }
        //根据id 去查询对应的信息
        TradeRuleDo dbRule = tradeRuleDomainService.getById(tradeRuleRo.getId());
        if(dbRule==null){
            return OutputResult.buildAlert(ResultCode.RULE_ID_NOT_EXIST);
        }
        if(!dbRule.getUserId().equals(tradeRuleRo.getUserId())){
            log.error("用户{} 试图修改别人 {} 的交易信息",tradeRuleRo.getUserId(),dbRule.getUserId());
            return OutputResult.buildAlert(ResultCode.NO_AUTH);
        }
        return OutputResult.buildSucc(dbRule);
    }
}
