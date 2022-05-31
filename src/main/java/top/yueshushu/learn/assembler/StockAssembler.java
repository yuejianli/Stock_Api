package top.yueshushu.learn.assembler;

import org.mapstruct.Mapper;
import top.yueshushu.learn.crawler.entity.DownloadStockInfo;
import top.yueshushu.learn.domain.StockDo;
import top.yueshushu.learn.entity.Stock;
import top.yueshushu.learn.mode.vo.StockVo;

/**
 * @Description stock 股票转换器
 * @Author yuejianli
 * @Date 2022/5/20 23:01
 **/
@Mapper(componentModel = "spring" )
public interface StockAssembler {
    /**
     * 股票 domain 转换成实体entity
     * @param stockDo 股票Do
     * @return 股票 domain 转换成实体entity
     */
    Stock doToEntity(StockDo stockDo);

    /**
     * 股票 entity 转换成 domain
     * @param stock 股票
     * @return 股票 entity 转换成 domain
     */
    StockDo entityToDo(Stock stock);

    /**
     * 股票 entity 转换成 vo
     * @param stock 股票
     * @return 股票 entity 转换成 vo
     */
    StockVo entityToVo(Stock stock);

    /**
     * 同步的股票信息 转换成 domain
     *
     * @param downloadStockInfo 同步的股票对象
     * @return 返回 domain
     */
    StockDo downInfoToDO(DownloadStockInfo downloadStockInfo);
}
