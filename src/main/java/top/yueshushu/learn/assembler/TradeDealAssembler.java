package top.yueshushu.learn.assembler;

import org.mapstruct.Mapper;
import top.yueshushu.learn.domain.TradeDealDo;
import top.yueshushu.learn.entity.TradeDeal;
import top.yueshushu.learn.mode.vo.TradeDealVo;

/**
 * @Description 成交转换
 * @Author yuejianli
 * @Date 2022/5/20 23:01
 **/
@Mapper(componentModel = "spring" )
public interface TradeDealAssembler {
    /**
     * 成交 domain 转换成实体entity
     * @param tradeDealDo 成交Do
     * @return 成交 domain 转换成实体entity
     */
    TradeDeal doToEntity(TradeDealDo tradeDealDo);

    /**
     * 成交 entity 转换成 domain
     * @param tradeEntrust 成交
     * @return 成交 entity 转换成 domain
     */
    TradeDealDo entityToDo(TradeDeal tradeEntrust);

    /**
     * 成交 entity 转换成 vo
     * @param tradeEntrust 成交
     * @return 成交 entity 转换成 vo
     */
    TradeDealVo entityToVo(TradeDeal tradeEntrust);
    /**
     * 成交  vo 转换成  entity
     * @param TradeDealVo 成交vo
     * @return 成交  vo 转换成  entity
     */
    TradeDeal voToEntity(TradeDealVo TradeDealVo);
}
