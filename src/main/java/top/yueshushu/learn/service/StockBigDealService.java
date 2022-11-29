package top.yueshushu.learn.service;

import java.math.BigDecimal;
import java.util.List;

/**
 * 大宗交易信息
 *
 * @author yuejianli
 * @date 2022-11-29
 */

public interface StockBigDealService {
    /**
     * 获取大宗交易的手数
     *
     * @param totalMoney 金额
     * @param stockCode  股票编码
     */
    Integer getMinVolume(BigDecimal totalMoney, String stockCode);

    /**
     * 同步大宗交易信息
     *
     * @param codeList 股票编码集合
     */
    void syncBigDeal(List<String> codeList);


}
