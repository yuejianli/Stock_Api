package top.yueshushu.learn.domainservice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.yueshushu.learn.domain.TradeEntrustDo;
import top.yueshushu.learn.domainservice.TradeEntrustDomainService;
import top.yueshushu.learn.mapper.TradeEntrustDoMapper;
import top.yueshushu.learn.mode.dto.TradeEntrustQueryDto;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description 委托操作
 * @Author yuejianli
 * @Date 2022/5/20 23:23
 **/
@Service
@Slf4j
public class TradeEntrustDomainServiceImpl extends ServiceImpl<TradeEntrustDoMapper, TradeEntrustDo>
        implements TradeEntrustDomainService {
    @Resource
    private TradeEntrustDoMapper tradeEntrustDoMapper;
    @Override
    public List<TradeEntrustDo> listByQuery(TradeEntrustQueryDto tradeEntrustQueryDto) {
        return tradeEntrustDoMapper.listByQuery(tradeEntrustQueryDto);
    }

    @Override
    public void deleteToDayByQuery(TradeEntrustQueryDto tradeEntrustQueryDto) {
         tradeEntrustDoMapper.deleteToDayByQuery(tradeEntrustQueryDto);
    }

    @Override
    public List<TradeEntrustDo> listHistoryByQuery(TradeEntrustQueryDto tradeEntrustQueryDto) {
        return tradeEntrustDoMapper.listHistoryByQuery(tradeEntrustQueryDto);
    }
}
