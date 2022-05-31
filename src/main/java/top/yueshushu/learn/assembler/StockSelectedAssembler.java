package top.yueshushu.learn.assembler;

import org.mapstruct.Mapper;
import top.yueshushu.learn.domain.StockSelectedDo;
import top.yueshushu.learn.entity.StockSelected;
import top.yueshushu.learn.mode.vo.StockSelectedVo;

/**
 * @Description stock 自选股票转换器
 * @Author yuejianli
 * @Date 2022/5/20 23:01
 **/
@Mapper(componentModel = "spring" )
public interface StockSelectedAssembler {
    /**
     * 自选股票 domain 转换成实体entity
     * @param stockSelectedDo 自选股票Do
     * @return 自选股票 domain 转换成实体entity
     */
    StockSelected doToEntity(StockSelectedDo stockSelectedDo);

    /**
     * 自选股票 entity 转换成 domain
     * @param stock 自选股票
     * @return 自选股票 entity 转换成 domain
     */
    StockSelectedDo entityToDo(StockSelected stock);

    /**
     * 自选股票 entity 转换成 vo
     * @param stock 自选股票
     * @return 自选股票 entity 转换成 vo
     */
    StockSelectedVo entityToVo(StockSelected stock);
}
