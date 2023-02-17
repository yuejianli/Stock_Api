package top.yueshushu.learn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.yueshushu.learn.domain.StockBkMoneyHistoryDo;
import top.yueshushu.learn.enumtype.BKType;

import java.util.Date;
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
    void deleteByDate(Date date, BKType bkType);

    /**
     * 根据版块编码和日期范围查询相应的数据
     *
     * @param bkCode    版块编码
     * @param startDate 开始日期
     * @param endDate   线束日期
     */
    List<StockBkMoneyHistoryDo> getMoneyHistoryByCodeAndRangeDate(String bkCode, Date startDate, Date endDate);
}
