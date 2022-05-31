package top.yueshushu.learn.domainservice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.yueshushu.learn.domain.TradeDealDo;
import top.yueshushu.learn.domainservice.TradeDealDomainService;
import top.yueshushu.learn.mapper.TradeDealDoMapper;
import top.yueshushu.learn.mode.dto.TradeDealQueryDto;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description 委托操作
 * @Author yuejianli
 * @Date 2022/5/20 23:23
 **/
@Service
@Slf4j
public class TradeDealDomainServiceImpl extends ServiceImpl<TradeDealDoMapper, TradeDealDo>
        implements TradeDealDomainService {
    @Resource
    private TradeDealDoMapper tradeDealDoMapper;

    @Override
    public List<TradeDealDo> listByQuery(TradeDealQueryDto tradeDealQueryDto) {
        return tradeDealDoMapper.listByQuery(tradeDealQueryDto);
    }

    @Override
    public void deleteToDayByQuery(TradeDealQueryDto tradeDealQueryDto) {
         tradeDealDoMapper.deleteToDayByQuery(tradeDealQueryDto);
    }

    @Override
    public List<TradeDealDo> listHistoryByQuery(TradeDealQueryDto tradeDealQueryDto) {
        return tradeDealDoMapper.listHistoryByQuery(tradeDealQueryDto);
    }
}
