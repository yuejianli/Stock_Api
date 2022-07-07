package top.yueshushu.learn.domainservice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;

import cn.hutool.core.date.DateTime;
import lombok.extern.slf4j.Slf4j;
import top.yueshushu.learn.domain.TradePositionHistoryDo;
import top.yueshushu.learn.domainservice.TradePositionHistoryDomainService;
import top.yueshushu.learn.mapper.TradePositionHistoryDoMapper;

/**
 * @Description 持仓历史
 * @Author yuejianli
 * @Date 2022/5/20 23:23
 **/
@Service
@Slf4j
public class TradePositionHistoryDomainServiceImpl extends ServiceImpl<TradePositionHistoryDoMapper, TradePositionHistoryDo>
        implements TradePositionHistoryDomainService {
    @Resource
    private TradePositionHistoryDoMapper tradePositionHistoryDoMapper;
    
    @Override
    public List<TradePositionHistoryDo> listPositionHistoryAndDate(Integer userId, Integer mockTpe, String code, DateTime startDate, DateTime endDate) {
        return tradePositionHistoryDoMapper.listPositionHistoryAndDateDesc(
                userId, mockTpe,
                code, startDate, endDate
        );
    }

    @Override
    public void deleteByUserIdAndMockTypeAndDate(Integer userId, Integer mockType, DateTime currDate) {
         tradePositionHistoryDoMapper.deleteByUserIdAndMockTypeAndDate(
                userId,mockType,currDate
        );
    }
}
