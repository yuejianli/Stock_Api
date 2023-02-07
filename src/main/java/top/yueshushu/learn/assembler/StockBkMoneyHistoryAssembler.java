package top.yueshushu.learn.assembler;

import org.mapstruct.Mapper;
import top.yueshushu.learn.crawler.entity.BKMoneyInfo;
import top.yueshushu.learn.domain.StockBkDo;
import top.yueshushu.learn.domain.StockBkMoneyHistoryDo;
import top.yueshushu.learn.mode.vo.StockBKVo;

import java.util.List;

/**
 * 股票注入资金
 *
 * @author yuejianli
 * @date 2023-02-07
 */
@Mapper(componentModel = "spring")
public interface StockBkMoneyHistoryAssembler {

    StockBkMoneyHistoryDo toDo(BKMoneyInfo bkMoneyInfo);

    List<StockBkMoneyHistoryDo> toDoList(List<BKMoneyInfo> todayMoneyInfoList);

    StockBKVo bkVo(StockBkDo stockBkDo);

    List<StockBKVo> bkVoList(List<StockBkDo> stockBkDoList);
}
