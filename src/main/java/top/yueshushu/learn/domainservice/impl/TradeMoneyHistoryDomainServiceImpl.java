package top.yueshushu.learn.domainservice.impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.yueshushu.learn.domain.TradeMoneyHistoryDo;
import top.yueshushu.learn.domainservice.TradeMoneyHistoryDomainService;
import top.yueshushu.learn.mapper.TradeMoneyHistoryDoMapper;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description 持仓历史操作
 * @Author yuejianli
 * @Date 2022/5/20 23:23
 **/
@Service
@Slf4j
public class TradeMoneyHistoryDomainServiceImpl extends ServiceImpl<TradeMoneyHistoryDoMapper, TradeMoneyHistoryDo>
        implements TradeMoneyHistoryDomainService {
    @Resource
    private TradeMoneyHistoryDoMapper tradeMoneyHistoryDoMapper;
    
    @Override
    public List<TradeMoneyHistoryDo> listMoneyHistory(Integer userId, Integer mockType, DateTime startDate, DateTime endDate) {
        return tradeMoneyHistoryDoMapper.listMoneyHistory(userId, mockType, startDate, endDate);
    }

    @Override
    public void deleteByUserIdAndMockTypeAndDate(Integer userId, Integer mockType, DateTime currDate) {
         tradeMoneyHistoryDoMapper.deleteByUserIdAndMockTypeAndDate(userId, mockType, currDate);
    }

    @Override
    public TradeMoneyHistoryDo getLastRecordProfit(Integer userId, Integer mockType, DateTime currDate) {
        return tradeMoneyHistoryDoMapper.getLastRecord(userId, mockType, currDate);
    }
}
