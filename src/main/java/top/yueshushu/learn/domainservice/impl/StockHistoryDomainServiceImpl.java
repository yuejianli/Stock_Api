package top.yueshushu.learn.domainservice.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.yueshushu.learn.domain.StockHistoryDo;
import top.yueshushu.learn.domainservice.StockHistoryDomainService;
import top.yueshushu.learn.mapper.StockHistoryDoMapper;
import top.yueshushu.learn.mode.dto.StockHistoryQueryDto;
import top.yueshushu.learn.mode.dto.StockPriceCacheDto;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Description TODO
 * @Author yuejianli
 * @Date 2022/5/20 23:23
 **/
@Service
@Slf4j
public class StockHistoryDomainServiceImpl extends ServiceImpl<StockHistoryDoMapper, StockHistoryDo>
        implements StockHistoryDomainService {
    @Resource
    private StockHistoryDoMapper stockHistoryDoMapper;

    @Override
    public List<StockHistoryDo> listStockHistoryAndDate(String code, DateTime startDate, DateTime endDate) {
        return stockHistoryDoMapper.listStockHistoryAndDateDesc(
                code, startDate, endDate
        );
    }

    @Override
    public List<StockPriceCacheDto> listYesterdayClosePrice(List<String> codeList, Date yesDate) {
        return stockHistoryDoMapper.listClosePrice(
                codeList, yesDate
        );
    }

    @Override
    public List<StockHistoryDo> limit10Desc(String stockCode, DateTime lastDay) {
        return stockHistoryDoMapper.limit10Desc(stockCode, lastDay);
    }

    @Override
    public StockHistoryDo getByCodeAndCurrDate(String code, DateTime currDate) {
        return stockHistoryDoMapper.getStockForDate(
                code, currDate
        );
    }

    @Override
    public StockHistoryDo getRecentyHistoryBeforeDate(String code, DateTime endDate) {
        return stockHistoryDoMapper.getRecentyHistoryBeforeDate(
                code,endDate
        );
    }

    @Override
    public List<StockHistoryDo> listStockHistoryAndDateAsc(String code, DateTime startDate, DateTime endDate) {
        return stockHistoryDoMapper.listStockHistoryAndDateAsc(
                code,startDate,endDate
        );
    }

    @Override
    public List<StockHistoryDo> listDayRange(StockHistoryQueryDto stockHistoryQueryDto) {
        return stockHistoryDoMapper.listDayRange(
                stockHistoryQueryDto
        );
    }

    @Override
    public void deleteAsyncRangeDateData(String code, String startDate, String endDate) {
        stockHistoryDoMapper.deleteAsyncRangeDateData(
                code,
                DateUtil.parse(
                        startDate,"yyyyMMdd"
                ),
                DateUtil.parse(
                        endDate,"yyyyMMdd"
                )
        );
    }

    @Override
    public List<String> listDate(String code, String startDate, String endDate) {
       return stockHistoryDoMapper.listDate(
                code,
                DateUtil.parse(
                        startDate,"yyyyMMdd"
                ),
                DateUtil.parse(
                        endDate,"yyyyMMdd"
                )
        );
    }
}
