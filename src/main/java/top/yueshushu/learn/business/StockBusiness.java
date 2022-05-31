package top.yueshushu.learn.business;

import top.yueshushu.learn.mode.info.StockInfo;
import top.yueshushu.learn.mode.ro.StockRo;
import top.yueshushu.learn.mode.vo.StockVo;
import top.yueshushu.learn.response.OutputResult;

/**
 * @Description stock 股票 的编排层处理
 * @Author 岳建立
 * @Date 2022/5/20 22:33
 **/
public interface StockBusiness {
    /**
     * 股票查询列表
     * @param stockRo 股票查询的对象
     * @return 返回分页后的股票信息
     */
    OutputResult<StockInfo> listStock(StockRo stockRo);

    /**
     * 根据股票的编码获取股票的信息
     * @param code 股票编码
     * @return 根据股票的编码获取股票的信息
     */
    OutputResult<StockVo> getStockInfo(String code);
}
