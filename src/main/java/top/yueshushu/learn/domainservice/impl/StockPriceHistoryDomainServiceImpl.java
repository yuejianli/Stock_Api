package top.yueshushu.learn.domainservice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.yueshushu.learn.domain.StockPriceHistoryDo;
import top.yueshushu.learn.domainservice.StockPriceHistoryDomainService;
import top.yueshushu.learn.mapper.StockPriceHistoryMapper;

/**
 * <p>
 * 股票的每分钟实时价格 服务实现类
 * </p>
 *
 * @author 岳建立
 * @since 2023-02-07
 */
@Service
public class StockPriceHistoryDomainServiceImpl extends ServiceImpl<StockPriceHistoryMapper, StockPriceHistoryDo>
        implements StockPriceHistoryDomainService {

}
