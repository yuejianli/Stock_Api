package top.yueshushu.learn.assembler;

import org.mapstruct.Mapper;
import top.yueshushu.learn.crawler.entity.StockPoolInfo;
import top.yueshushu.learn.domain.StockPoolHistoryDo;

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
}
