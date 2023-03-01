package top.yueshushu.learn.service.impl;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.yueshushu.learn.assembler.TradeRuleDbAssembler;
import top.yueshushu.learn.common.ResultCode;
import top.yueshushu.learn.domain.TradeRuleDbDo;
import top.yueshushu.learn.domainservice.TradeRuleDbDomainService;
import top.yueshushu.learn.entity.TradeRuleDb;
import top.yueshushu.learn.enumtype.ConfigCodeType;
import top.yueshushu.learn.enumtype.DBStockType;
import top.yueshushu.learn.enumtype.DataFlagType;
import top.yueshushu.learn.mode.ro.TradeRuleDbRo;
import top.yueshushu.learn.mode.vo.ConfigVo;
import top.yueshushu.learn.mode.vo.TradeRuleDbVo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.response.PageResponse;
import top.yueshushu.learn.service.ConfigService;
import top.yueshushu.learn.service.TradeRuleDbService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
public class TradeRuleDbServiceImpl implements TradeRuleDbService {

    @Resource
    private TradeRuleDbDomainService tradeRuleDbDomainService;
    @Resource
    private TradeRuleDbAssembler tradeRuleDbAssembler;
    @Resource
    private ConfigService configService;

    @Override
    public OutputResult<PageResponse<TradeRuleDbVo>> listRule(TradeRuleDbRo tradeRuleDbRo) {
        Page<Object> pageGithubResult = PageHelper.startPage(tradeRuleDbRo.getPageNum(), tradeRuleDbRo.getPageSize());
        List<TradeRuleDbDo> tradeRuleDoList = tradeRuleDbDomainService.listByQuery(tradeRuleDbRo.getUserId(), tradeRuleDbRo.getMockType());
        if (CollectionUtils.isEmpty(tradeRuleDoList)) {
            return OutputResult.buildSucc(PageResponse.emptyPageResponse());
        }
        List<TradeRuleDbVo> pageResultList = convertVo(tradeRuleDoList);
        return OutputResult.buildSucc(new PageResponse<>(pageGithubResult.getTotal(), pageResultList));
    }

    /**
     * 数据转换处理
     *
     * @param records 数据存储对象
     * @return 数据转换处理
     */
    private List<TradeRuleDbVo> convertVo(List<TradeRuleDbDo> records) {
        List<TradeRuleDbVo> result = new ArrayList<>();
        records.stream().forEach(
                n -> {
                    TradeRuleDbVo tradeRuleDbVo = new TradeRuleDbVo();
                    BeanUtils.copyProperties(n, tradeRuleDbVo);
                    tradeRuleDbVo.setCodeTypeStr(DBStockType.getStockType(n.getCodeType()).getDesc());
                    result.add(tradeRuleDbVo);
                }
        );
        return result;
    }

    @Override
    public OutputResult addRule(TradeRuleDbRo tradeRuleDbRo) {

        ConfigVo configVo = configService.getConfig(tradeRuleDbRo.getUserId(), ConfigCodeType.DB_ENABLE);
        if (!String.valueOf(DataFlagType.NORMAL.getCode()).equals(configVo.getCodeValue())) {
            return OutputResult.buildAlert(ResultCode.NO_AUTH);
        }

        // 看是否已经存在
        List<TradeRuleDbDo> tradeRuleDbDos = tradeRuleDbDomainService.listByQuery(tradeRuleDbRo.getUserId(), tradeRuleDbRo.getMockType());
        if (!CollectionUtils.isEmpty(tradeRuleDbDos)) {
            return OutputResult.buildAlert(ResultCode.RULE_DB_ONE);
        }
        TradeRuleDb tradeRuleDb = tradeRuleDbAssembler.roToEntity(tradeRuleDbRo);
        tradeRuleDb.setCreateTime(DateUtil.date());
        tradeRuleDb.setUpdateTime(DateUtil.date());
        tradeRuleDb.setFlag(DataFlagType.NORMAL.getCode());
        //进行添加规则
        tradeRuleDbDomainService.save(tradeRuleDbAssembler.entityToDo(tradeRuleDb));
        return OutputResult.buildSucc();
    }

    @Override
    public OutputResult updateRule(TradeRuleDbRo tradeRuleDbRo) {
        OutputResult outputResult = validRule(tradeRuleDbRo);
        if (!outputResult.getSuccess()) {
            return outputResult;
        }
        TradeRuleDbDo dbRule = (TradeRuleDbDo) outputResult.getData();
        //进行更新
        dbRule.setName(tradeRuleDbRo.getName());
        dbRule.setCodeType(tradeRuleDbRo.getCodeType());
        dbRule.setBuyNum(tradeRuleDbRo.getBuyNum());
        dbRule.setBuyParam(tradeRuleDbRo.getBuyParam());
        dbRule.setUpdateTime(DateUtil.date());
        tradeRuleDbDomainService.updateById(dbRule);
        return OutputResult.buildSucc();
    }

    @Override
    public OutputResult deleteRule(TradeRuleDbRo tradeRuleDbRo) {
        OutputResult outputResult = validRule(tradeRuleDbRo);
        if (!outputResult.getSuccess()) {
            return outputResult;
        }
        TradeRuleDbDo dbRule = (TradeRuleDbDo) outputResult.getData();
        dbRule.setFlag(DataFlagType.DELETE.getCode());
        dbRule.setUpdateTime(DateUtil.date());
        tradeRuleDbDomainService.removeById(dbRule.getId());
        return OutputResult.buildSucc();
    }

    @Override
    public TradeRuleDb getById(Integer id) {
        return tradeRuleDbAssembler.doToEntity(
                tradeRuleDbDomainService.getById(id)
        );
    }

    /**
     * 验证交易规则信息
     *
     * @param tradeRuleDbRo 交易规则对象
     * @return 返回验证的信息
     */
    private OutputResult validRule(TradeRuleDbRo tradeRuleDbRo) {
        if (tradeRuleDbRo.getId() == null) {
            return OutputResult.buildAlert(ResultCode.ID_IS_EMPTY);
        }
        //根据id 去查询对应的信息
        TradeRuleDbDo dbRuleDb = tradeRuleDbDomainService.getById(tradeRuleDbRo.getId());
        if (dbRuleDb == null) {
            return OutputResult.buildAlert(ResultCode.RULE_ID_NOT_EXIST);
        }
        if (!dbRuleDb.getUserId().equals(tradeRuleDbRo.getUserId())) {
            log.error("用户{} 试图修改别人 {} 的交易信息", tradeRuleDbRo.getUserId(), dbRuleDb.getUserId());
            return OutputResult.buildAlert(ResultCode.NO_AUTH);
        }
        return OutputResult.buildSucc(dbRuleDb);
    }
}
