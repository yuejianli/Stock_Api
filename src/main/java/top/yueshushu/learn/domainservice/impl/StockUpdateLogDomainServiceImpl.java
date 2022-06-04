package top.yueshushu.learn.domainservice.impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.yueshushu.learn.domain.StockUpdateLogDo;
import top.yueshushu.learn.domainservice.StockUpdateLogDomainService;
import top.yueshushu.learn.mapper.StockUpdateLogDoMapper;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description 股票更新历史
 * @Author yuejianli
 * @Date 2022/6/4 10:08
 **/
@Service
public class StockUpdateLogDomainServiceImpl extends ServiceImpl<StockUpdateLogDoMapper, StockUpdateLogDo>
        implements StockUpdateLogDomainService {
    @Resource
    private StockUpdateLogDoMapper stockUpdateLogDoMapper;
    @Override
    public List<StockUpdateLogDo> listStockLogAndDate(String code, DateTime startDate, DateTime endDate) {
        return stockUpdateLogDoMapper.listStockLogAndDate(code,startDate,endDate);
    }
}
