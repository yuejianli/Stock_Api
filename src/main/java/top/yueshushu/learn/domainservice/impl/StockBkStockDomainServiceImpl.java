package top.yueshushu.learn.domainservice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import top.yueshushu.learn.domain.StockBkStockDo;
import top.yueshushu.learn.domainservice.StockBkStockDomainService;
import top.yueshushu.learn.mapper.StockBkStockMapper;
import top.yueshushu.learn.mode.dto.StockBkCodeQueryDto;

import java.util.List;
import java.util.stream.Collectors;

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
        StockBkCodeQueryDto stockBkCodeQueryDto = new StockBkCodeQueryDto();
        stockBkCodeQueryDto.setStockCode(stockCode);
        stockBkCodeQueryDto.setBkCode(bkCode);
        return listByCondition(stockBkCodeQueryDto);
    }

    @Override
    public List<String> listAllStockCode() {
        return listAllStockCodeByCode(null);
    }

    @Override
    public List<String> listAllStockCodeByCode(String stockCode) {
        return this.lambdaQuery()
                .select(StockBkStockDo::getStockCode)
                .eq(StringUtils.hasText(stockCode), StockBkStockDo::getStockCode, stockCode)
                .list()
                .stream()
                .map(StockBkStockDo::getStockCode)
                .collect(Collectors.toList())
                .stream()
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<StockBkStockDo> listByCondition(StockBkCodeQueryDto stockBkCodeQueryDto) {
        return this.lambdaQuery()
                .eq(StringUtils.hasText(stockBkCodeQueryDto.getStockCode()), StockBkStockDo::getStockCode, stockBkCodeQueryDto.getStockCode())
                .eq(StringUtils.hasText(stockBkCodeQueryDto.getBkCode()), StockBkStockDo::getBkCode, stockBkCodeQueryDto.getBkCode())
                .in(!CollectionUtils.isEmpty(stockBkCodeQueryDto.getStockCodeList()), StockBkStockDo::getStockCode, stockBkCodeQueryDto.getStockCodeList())
                .in(!CollectionUtils.isEmpty(stockBkCodeQueryDto.getBkCodeList()), StockBkStockDo::getBkCode, stockBkCodeQueryDto.getBkCodeList())
                .list();
    }
}
