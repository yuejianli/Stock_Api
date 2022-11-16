package top.yueshushu.learn.service;

import top.yueshushu.learn.entity.StockSelected;
import top.yueshushu.learn.mode.ro.IdRo;
import top.yueshushu.learn.mode.ro.StockSelectedRo;
import top.yueshushu.learn.mode.vo.StockSelectedVo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.response.PageResponse;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 股票自选表,是用户自己选择的 自定义的
 * </p>
 *
 * @author 岳建立
 * @since 2022-01-02
 */
public interface StockSelectedService {
    /**
     * 添加到自选表里面,返回添加的自选对象信息.
     * @param stockSelectedRo 股票自选对象
     * @param stockName 股票名称
     * @return 添加到自选表里面,返回添加的自选对象信息.
     */
    StockSelected add(StockSelectedRo stockSelectedRo,String stockName);

    /**
     * 单个移除自选数据信息
     * @param idRo 编号对象，里面有单条记录的id
     * @param userId 用户编号
     * @return 如果删除成功，返回 这个记录的 job_id
     */
    OutputResult delete(IdRo idRo, int userId);

    /**
     * 根据股票的code 进行移除
     * @param stockSelectedRo
     * @return
     */
    OutputResult deleteByCode(StockSelectedRo stockSelectedRo);

    /**
     * 更新历史表记录信息，
     * 从自选表里面拿出数据。
     */
    void syncDayHistory();

    /**
     * 处理股票的收盘价信息
     */
    void cacheClosePrice();

    /**
     * 查询所有的股票信息
     * @date 2022/1/27 14:16
     * @author zk_yjl
     * @param userId 用户编号
     * @param keyword 关键字
     * @return java.util.List<top.yueshushu.learn.mode.vo.StockSelectedVo>
     */
    List<StockSelectedVo> listSelf(Integer userId,String keyword);

    /**
     * 分页查询自选表信息
     *
     * @param stockSelectedRo 自选股票对象
     * @return 分页查询自选表信息
     */
    OutputResult<PageResponse<StockSelectedVo>> pageSelected(StockSelectedRo stockSelectedRo);

    /**
     * 股票自选时，进行添加验证操作
     * @param stockSelectedRo 股票自选对象
     * @param maxSelectedNum 目前的最大选中数量
     * @return 股票自选时，进行添加验证操作
     */
    OutputResult validateAdd(StockSelectedRo stockSelectedRo, int maxSelectedNum);

    /**
     * 更新用户自选股票记录信息
     * @param stockSelected 用户股票自选记录
     */
    void updateSelected(StockSelected stockSelected);

    /**
     * 根据用户获取当前正常可用的股票编码 Map集合 key为股票编号， value为股票名称
     * @param userId 当前用户
     * @return 根据用户获取当前正常可用的股票编码集合
     */
    Map<String, String> listStockCodeByUserId(Integer userId);

    /**
     * 更新自选表里面的股票代码的价格
     * @param code 股票编码
     */
    void updateSelectedCodePrice(String code);

    /**
     * 编辑笔记
     *
     * @param stockSelectedRo 笔记对象
     * @return 编辑笔记
     */
    OutputResult editNotes(StockSelectedRo stockSelectedRo);

    /**
     * 批量保存自选股票信息
     *
     * @param addUserId     要添加的用户id
     * @param stockCodeList 股票编码集合
     */
    List<String> batchSelected(int addUserId, List<String> stockCodeList);

    /**
     * 同步股票编码
     *
     * @param stockCode 股票编码
     */
    void syncCodeInfo(String stockCode);
}
