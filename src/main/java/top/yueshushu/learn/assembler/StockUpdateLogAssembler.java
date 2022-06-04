package top.yueshushu.learn.assembler;

import org.mapstruct.Mapper;
import top.yueshushu.learn.domain.StockDo;
import top.yueshushu.learn.domain.StockUpdateLogDo;
import top.yueshushu.learn.entity.StockUpdateLog;
import top.yueshushu.learn.mode.vo.StockUpdateLogVo;

/**
 * @Description 股票更新历史
 * @Author yuejianli
 * @Date 2022/5/20 23:01
 **/
@Mapper(componentModel = "spring" )
public interface StockUpdateLogAssembler {
    /**
     * 股票更新日志 domain 转换成实体entity
     * @param stockUpdateLogDo 股票更新日志Do
     * @return 股票更新日志 domain 转换成实体entity
     */
    StockUpdateLog doToEntity(StockUpdateLogDo stockUpdateLogDo);

    /**
     * 股票更新日志 entity 转换成 domain
     * @param stockUpdateLog 股票更新日志
     * @return 股票更新日志 entity 转换成 domain
     */
    StockUpdateLogDo entityToDo(StockUpdateLog stockUpdateLog);

    /**
     * 股票更新日志 entity 转换成 vo
     * @param stockUpdateLog 股票更新日志
     * @return 股票更新日志 entity 转换成 vo
     */
    StockUpdateLogVo entityToVo(StockUpdateLog stockUpdateLog);

    /**
     * 股票更新日志 stockDo 转换成 domain
     * @param stockDo 股票更新日志
     * @return 股票更新日志 entity 转换成 domain
     */
    StockUpdateLogDo stockEntityToDo(StockDo stockDo);
}
