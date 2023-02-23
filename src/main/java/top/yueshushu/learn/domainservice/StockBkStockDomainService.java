package top.yueshushu.learn.domainservice;

import com.baomidou.mybatisplus.extension.service.IService;
import top.yueshushu.learn.domain.StockBkStockDo;
import top.yueshushu.learn.mode.dto.StockBkCodeQueryDto;

import java.util.List;

/**
 * @author Yue Jianli
 * @date 2023-02-09
 */

public interface StockBkStockDomainService extends IService<StockBkStockDo> {
    /**
     * 根据股票code 和版块code 查询信息
     *
     * @param stockCode 股票code
     * @param bkCode    版块code
     */
    List<StockBkStockDo> listByStockCode(String stockCode, String bkCode);

    /**
     * 查询所有的股票 code 集合
     */
    List<String> listAllStockCode();

    /**
     * 查询 该股票 的股票 code 集合
     */
    List<String> listAllStockCodeByCode(String stockCode);

    /**
     * 根据条件进行多查询
     *
     * @param stockBkCodeQueryDto 查询对象
     */
    List<StockBkStockDo> listByCondition(StockBkCodeQueryDto stockBkCodeQueryDto);
}
