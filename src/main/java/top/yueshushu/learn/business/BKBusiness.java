package top.yueshushu.learn.business;

import top.yueshushu.learn.mode.ro.StockBKMoneyStatRo;
import top.yueshushu.learn.mode.vo.StockBKVo;
import top.yueshushu.learn.response.OutputResult;

import java.util.List;

/**
 * 版块
 *
 * @author Yue Jianli
 * @date 2023-02-07
 */

public interface BKBusiness {
    /**
     * 同步版块信息
     */
    void syncBK();

    /**
     * 同步版块的金额流向信息
     */
    void syncBKMoney();

    OutputResult<List<StockBKVo>> listBk();

    /**
     * 获取某个版块的历史记录信息
     *
     * @param stockBKMoneyStatRo 版块记录
     */
    OutputResult getCharStat(StockBKMoneyStatRo stockBKMoneyStatRo);

    /**
     * 查询所有的版块资金类型
     */
    OutputResult<List<StockBKVo>> listMoneyType();
}
