package top.yueshushu.learn.domainservice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.yueshushu.learn.domain.TradeMoneyDo;
import top.yueshushu.learn.domainservice.TradeMoneyDomainService;
import top.yueshushu.learn.mapper.TradeMoneyDoMapper;

import javax.annotation.Resource;

/**
 * @Description 持仓操作
 * @Author yuejianli
 * @Date 2022/5/20 23:23
 **/
@Service
@Slf4j
public class TradeMoneyDomainServiceImpl extends ServiceImpl<TradeMoneyDoMapper, TradeMoneyDo>
        implements TradeMoneyDomainService {
    @Resource
    private TradeMoneyDoMapper tradeMoneyDoMapper;


    @Override
    public TradeMoneyDo getByUserIdAndMockType(Integer userId, Integer mockType) {
        return this.lambdaQuery()
                .eq(TradeMoneyDo::getUserId,userId)
                .eq(TradeMoneyDo::getMockType,mockType)
                .one();
    }
}
