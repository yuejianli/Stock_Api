package top.yueshushu.learn.domainservice;

import com.baomidou.mybatisplus.extension.service.IService;
import top.yueshushu.learn.domain.StockBkStockDo;

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
}
