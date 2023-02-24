package top.yueshushu.learn.domainservice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.yueshushu.learn.domain.StockPoolHistoryDo;
import top.yueshushu.learn.domainservice.StockPoolHistoryDomainService;
import top.yueshushu.learn.mapper.StockPoolHistoryMapper;
import top.yueshushu.learn.mode.dto.StockPoolQueryDto;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 岳建立
 * @since 2023-02-15
 */
@Service
public class StockPoolHistoryDomainServiceImpl extends ServiceImpl<StockPoolHistoryMapper, StockPoolHistoryDo> implements StockPoolHistoryDomainService {
    @Override
    public List<StockPoolHistoryDo> listByCondition(StockPoolQueryDto stockPoolQueryDto) {
        return this.lambdaQuery()
                .eq(stockPoolQueryDto.getPoolType() != null, StockPoolHistoryDo::getType, stockPoolQueryDto.getPoolType())
                .gt(stockPoolQueryDto.getStartDate() != null, StockPoolHistoryDo::getCurrDate, stockPoolQueryDto.getStartDate())
                .lt(stockPoolQueryDto.getEndDate() != null, StockPoolHistoryDo::getCurrDate, stockPoolQueryDto.getEndDate())
                .last(StringUtils.hasText(stockPoolQueryDto.getKeywords()), " and ( code like '%" + stockPoolQueryDto.getKeywords() + "%' or name like '%" + stockPoolQueryDto.getKeywords() + "%')")
                .list();
    }
}
