package top.yueshushu.learn.business;

import top.yueshushu.learn.mode.ro.IdRo;
import top.yueshushu.learn.mode.ro.StockSelectedRo;
import top.yueshushu.learn.mode.vo.StockVo;
import top.yueshushu.learn.response.OutputResult;

/**
 * @Description stockSelected 自选股票 的编排层处理
 * @Author 岳建立
 * @Date 2022/5/20 22:33
 **/
public interface StockSelectedBusiness {
    /**
     * 查询自选表列表信息
     * @param stockSelectedRo 查询自选表Ro对象
     * @return 查询自选表列表信息
     */
    OutputResult listSelected(StockSelectedRo stockSelectedRo);

    /**
     * 将股票根据股票编码添加到自选表里面
     * @param stockSelectedRo 自选股票对象
     * @return 将股票根据股票编码添加到自选表里面
     */
    OutputResult add(StockSelectedRo stockSelectedRo);

    /**
     * 单个删除自选表信息
     * @param idRo 序号集合
     * @param userId 用户id
     * @return 单个删除自选表信息
     */
    OutputResult delete(IdRo idRo, int userId);

    /**
     * 根据用户和股票编码移出自选表
     * @param stockSelectedRo 股票自选对象
     * @return 根据用户和股票编码移出自选表
     */
    OutputResult deleteByCode(StockSelectedRo stockSelectedRo);

    /**
     * 查询自选表里面的昨日历史记录的信息
     * @param stockSelectedRo 股票自选对象
     * @return 查询自选表里面的昨日历史记录的信息
     */
    OutputResult yesHistory(StockSelectedRo stockSelectedRo);

    /**
     * 编辑笔记
     * @param stockSelectedRo 笔记对象
     * @return 编辑笔记
     */
    OutputResult editNotes(StockSelectedRo stockSelectedRo);
}
