package top.yueshushu.learn.business;

import top.yueshushu.learn.crawler.entity.BKInfo;
import top.yueshushu.learn.crawler.entity.BKMoneyInfo;
import top.yueshushu.learn.enumtype.BKType;
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

    /**
     * 同步概念信息
     */
    void syncGN();

    /**
     * 同步 概念的金额流向信息
     */
    void syncGNMoney();


    /**
     * 同步地域信息
     */
    void syncDY();

    /**
     * 同步地域的金额流向信息
     */
    void syncDYMoney();

    /**
     * 根据类型找数据
     *
     * @param bkType 类型
     */
    List<BKInfo> crawlerBKInfoByType(BKType bkType);

    /**
     * 根据类型查询金额信息
     *
     * @param bkType 类型
     */
    List<BKMoneyInfo> crawlerMoneyInfoByType(BKType bkType);


    OutputResult<List<StockBKVo>> listBk();

    OutputResult<List<StockBKVo>> listGn();

    OutputResult<List<StockBKVo>> listDy();


    List<StockBKVo> listByType(BKType bkType);

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

    /**
     * 同步 版块,概念 和地域,及其今日的资金流动情况
     */
    void syncBkAndMoney();

    /**
     * 同步该股票的概念信息
     *
     * @param code 股票编码
     */
    void syncRelationCode(String code);
}
