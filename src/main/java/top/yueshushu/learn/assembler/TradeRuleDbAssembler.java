package top.yueshushu.learn.assembler;

import org.mapstruct.Mapper;
import top.yueshushu.learn.domain.TradeRuleDbDo;
import top.yueshushu.learn.entity.TradeRuleDb;
import top.yueshushu.learn.mode.ro.TradeRuleDbRo;
import top.yueshushu.learn.mode.vo.TradeRuleDbVo;

/**
 * @Description 交易规则转换器
 * @Author yuejianli
 * @Date 2022/5/20 23:01
 **/
@Mapper(componentModel = "spring")
public interface TradeRuleDbAssembler {
    /**
     * 交易规则 domain 转换成实体entity
     *
     * @param tradeRuleDbDo 交易规则 Do
     * @return 交易规则 domain 转换成实体entity
     */
    TradeRuleDb doToEntity(TradeRuleDbDo tradeRuleDbDo);

    /**
     * 交易规则 entity 转换成 domain
     *
     * @param tradeRuleDb 交易规则
     * @return 交易规则 entity 转换成 domain
     */
    TradeRuleDbDo entityToDo(TradeRuleDb tradeRuleDb);

    /**
     * 交易规则 entity 转换成 vo
     *
     * @param tradeRuleDb 交易规则
     * @return 交易规则 entity 转换成 vo
     */
    TradeRuleDbVo entityToVo(TradeRuleDb tradeRuleDb);

    /**
     * 交易规则 ro 转换成实体entity
     *
     * @param tradeRuleDbRo 交易规则 ro
     * @return 交易规则 ro 转换成实体entity
     */
    TradeRuleDb roToEntity(TradeRuleDbRo tradeRuleDbRo);

}
