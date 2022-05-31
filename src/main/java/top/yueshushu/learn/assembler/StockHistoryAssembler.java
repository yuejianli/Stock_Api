package top.yueshushu.learn.assembler;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import top.yueshushu.learn.crawler.entity.StockHistoryCsvInfo;
import top.yueshushu.learn.domain.StockHistoryDo;
import top.yueshushu.learn.entity.StockHistory;
import top.yueshushu.learn.mode.vo.StockHistoryVo;

/**
 * @Description stockHistory 股票历史转换器
 * @Author yuejianli
 * @Date 2022/5/20 23:01
 **/
@Mapper(componentModel = "spring" )
public interface StockHistoryAssembler {
    /**
     * 股票历史 domain 转换成实体entity
     * @param stockHistoryDo 股票历史Do
     * @return 股票历史 domain 转换成实体entity
     */
    StockHistory doToEntity(StockHistoryDo stockHistoryDo);

    /**
     * 股票历史 entity 转换成 domain
     * @param stockHistory 股票历史
     * @return 股票历史 entity 转换成 domain
     */
    StockHistoryDo entityToDo(StockHistory stockHistory);

    /**
     * 股票历史 entity 转换成 vo
     * @param stockHistory 股票历史
     * @return 股票历史 entity 转换成 vo
     */
    StockHistoryVo entityToVo(StockHistory stockHistory);

    /**
     * 股票历史 stockHistoryCsvInfo 转换成 domain
     *
     * @param stockHistoryCsvInfo 股票历史csv记录
     * @return 股票历史 stockHistoryCsvInfo 转换成 domain
     */
    @Mappings({
            @Mapping(target = "currDate",ignore = true),
    })
    StockHistoryDo csvInfoToDo(StockHistoryCsvInfo stockHistoryCsvInfo);
}
