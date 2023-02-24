package top.yueshushu.learn.assembler;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import top.yueshushu.learn.crawler.entity.StockPoolInfo;
import top.yueshushu.learn.domain.StockPoolHistoryDo;
import top.yueshushu.learn.mode.dto.StockPoolQueryDto;
import top.yueshushu.learn.mode.ro.StockPoolRo;

import java.util.List;

/**
 * @Description 配置转换器
 * @Author yuejianli
 * @Date 2022/5/20 23:01
 **/
@Mapper(componentModel = "spring")
public interface StockPoolHistoryAssembler {
    StockPoolHistoryDo toDo(StockPoolInfo stockPoolInfo);

    List<StockPoolHistoryDo> toDoList(List<StockPoolInfo> poolList);


    @Mapping(source = "startDate", target = "startDate", dateFormat = "yyyy-MM-dd")
    @Mapping(source = "endDate", target = "endDate", dateFormat = "yyyy-MM-dd")
    StockPoolQueryDto roToDto(StockPoolRo stockPoolRo);

}
