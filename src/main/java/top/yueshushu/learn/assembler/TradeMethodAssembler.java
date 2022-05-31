package top.yueshushu.learn.assembler;

import org.mapstruct.Mapper;
import top.yueshushu.learn.domain.TradeMethodDo;
import top.yueshushu.learn.entity.TradeMethod;
/**
 * @Description 交易使用的方法转换器
 * @Author yuejianli
 * @Date 2022/5/20 23:01
 **/
@Mapper(componentModel = "spring" )
public interface TradeMethodAssembler {
    /**
     * 交易方法 domain 转换成实体entity
     * @param tradeMethodDo 交易方法Do
     * @return 交易方法 domain 转换成实体entity
     */
    TradeMethod doToEntity(TradeMethodDo tradeMethodDo);

    /**
     * 交易方法 entity 转换成 domain
     * @param menu 交易方法
     * @return 交易方法 entity 转换成 domain
     */
    TradeMethodDo entityToDo(TradeMethod menu);
}
