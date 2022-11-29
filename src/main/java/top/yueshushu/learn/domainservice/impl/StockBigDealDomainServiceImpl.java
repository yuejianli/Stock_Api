package top.yueshushu.learn.domainservice.impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.yueshushu.learn.domain.StockBigDealDo;
import top.yueshushu.learn.domainservice.StockBigDealDomainService;
import top.yueshushu.learn.mapper.StockBigDealDoMapper;

import javax.annotation.Resource;

/**
 * 用途描述
 *
 * @author yuejianli
 * @date 2022-11-29
 */
@Service
public class StockBigDealDomainServiceImpl extends ServiceImpl<StockBigDealDoMapper, StockBigDealDo>
        implements StockBigDealDomainService {

    @Resource
    private StockBigDealDoMapper stockBigDealDoMapper;

    @Override
    public void deleteHasAsyncData(String fullCode, DateTime currentDate) {
        stockBigDealDoMapper.deleteHasAsyncData(fullCode, currentDate);
    }
}
