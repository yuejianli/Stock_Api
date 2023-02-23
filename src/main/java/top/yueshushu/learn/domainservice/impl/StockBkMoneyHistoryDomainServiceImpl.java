package top.yueshushu.learn.domainservice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.yueshushu.learn.domain.StockBkMoneyHistoryDo;
import top.yueshushu.learn.domainservice.StockBkMoneyHistoryDomainService;
import top.yueshushu.learn.mapper.StockBkMoneyHistoryMapper;

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
public class StockBkMoneyHistoryDomainServiceImpl extends ServiceImpl<StockBkMoneyHistoryMapper, StockBkMoneyHistoryDo>
        implements StockBkMoneyHistoryDomainService {
    @Resource
    private StockBkMoneyHistoryMapper stockBkMoneyHistoryMapper;

    @Override
    public void deleteByDate(Date date, Integer type) {
        stockBkMoneyHistoryMapper.deleteByDate(date, type);
    }

    @Override
    public List<StockBkMoneyHistoryDo> getMoneyHistoryByCodeAndRangeDate(String bkCode, Date startDate, Date endDate) {
        return stockBkMoneyHistoryMapper.listMoneyHistoryByCodeAndRangeDate(bkCode, startDate, endDate);
    }

    @Override
    public List<StockBkMoneyHistoryDo> listTopByDateOrderByProportionDesc(Date date, Integer bkType, int topNum) {
        return stockBkMoneyHistoryMapper.listTopByDateOrderByProportionDesc(date, bkType, topNum);
    }
}
