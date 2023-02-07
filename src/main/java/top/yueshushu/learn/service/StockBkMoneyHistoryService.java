package top.yueshushu.learn.service;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.extension.service.IService;
import top.yueshushu.learn.domain.StockBkMoneyHistoryDo;

import java.util.List;

/**
 * <p>
 * 版块资金注入历史 服务类
 * </p>
 *
 * @author 岳建立
 * @since 2023-02-07
 */
public interface StockBkMoneyHistoryService extends IService<StockBkMoneyHistoryDo> {
    /**
     * 删除之前的数据
     */
    void deleteByDate(DateTime date);

    /**
     * 根据版块编码和日期范围查询相应的数据
     *
     * @param bkCode    版块编码
     * @param startDate 开始日期
     * @param endDate   线束日期
     */
    List<StockBkMoneyHistoryDo> getMoneyHistoryByCodeAndRangeDate(String bkCode, DateTime startDate, DateTime endDate);
}
