package top.yueshushu.learn.domainservice;

import com.baomidou.mybatisplus.extension.service.IService;
import top.yueshushu.learn.domain.StockBkMoneyHistoryDo;

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
public interface StockBkMoneyHistoryDomainService extends IService<StockBkMoneyHistoryDo> {
    /**
     * 删除之前的数据
     */
    void deleteByDate(Date date, Integer type);

    /**
     * 根据版块编码和日期范围查询相应的数据
     *
     * @param bkCode    版块编码
     * @param startDate 开始日期
     * @param endDate   线束日期
     */
    List<StockBkMoneyHistoryDo> getMoneyHistoryByCodeAndRangeDate(String bkCode, Date startDate, Date endDate);

    /**
     * 查询该日期下 版块的涨幅度排行榜
     *
     * @param date   日期
     * @param bkType 类型
     * @param topNum 数量
     */
    List<StockBkMoneyHistoryDo> listTopByDateOrderByProportionDesc(Date date, Integer bkType, int topNum);
}
