package top.yueshushu.learn.domainservice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.yueshushu.learn.domain.StockBkStockDo;
import top.yueshushu.learn.domainservice.StockBkStockDomainService;
import top.yueshushu.learn.mapper.StockBkStockMapper;

import java.util.List;

/**
 * 用途描述
 *
 * @author yuejianli
 * @date 2023-02-09
 */
@Service
public class StockBkStockDomainServiceImpl extends ServiceImpl<StockBkStockMapper, StockBkStockDo>
        implements StockBkStockDomainService {

    @Override
    public List<StockBkStockDo> listByStockCode(String stockCode, String bkCode) {
        return this.lambdaQuery()
                .eq(StringUtils.hasText(stockCode), StockBkStockDo::getStockCode, stockCode)
                .eq(StringUtils.hasText(bkCode), StockBkStockDo::getBkCode, bkCode)
                .list();
    }
}
