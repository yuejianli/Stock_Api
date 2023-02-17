package top.yueshushu.learn.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.yueshushu.learn.domain.StockBkMoneyHistoryDo;
import top.yueshushu.learn.domainservice.StockBkMoneyHistoryDomainService;
import top.yueshushu.learn.enumtype.BKType;
import top.yueshushu.learn.mapper.StockBkMoneyHistoryMapper;
import top.yueshushu.learn.service.StockBkMoneyHistoryService;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 版块资金注入历史 服务实现类
 * </p>
 *
 * @author 岳建立
 * @since 2023-02-07
 */
@Service
public class StockBkMoneyHistoryServiceImpl extends ServiceImpl<StockBkMoneyHistoryMapper, StockBkMoneyHistoryDo> implements StockBkMoneyHistoryService {

    @Resource
    private StockBkMoneyHistoryDomainService stockBkMoneyHistoryDomainService;

    @Override
    public void deleteByDate(Date date, BKType bkType) {
        stockBkMoneyHistoryDomainService.deleteByDate(date, bkType == null ? null : bkType.getCode());
    }

    @Override
    public List<StockBkMoneyHistoryDo> getMoneyHistoryByCodeAndRangeDate(String bkCode, Date startDate, Date endDate) {
        //将开始日期变成 上一天的最后时刻
        startDate = DateUtil.endOfDay(
                DateUtil.offsetDay(
                        startDate, -1
                )
        );
        //将结束日期变成这一天的最后时刻
        endDate = DateUtil.endOfDay(endDate);
        return stockBkMoneyHistoryDomainService.getMoneyHistoryByCodeAndRangeDate(bkCode, startDate, endDate);
    }
}
