package top.yueshushu.learn.domainservice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.yueshushu.learn.domain.TradePositionDo;
import top.yueshushu.learn.domainservice.TradePositionDomainService;
import top.yueshushu.learn.mapper.TradePositionDoMapper;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description 持仓操作
 * @Author yuejianli
 * @Date 2022/5/20 23:23
 **/
@Service
@Slf4j
public class TradePositionDomainServiceImpl extends ServiceImpl<TradePositionDoMapper, TradePositionDo>
        implements TradePositionDomainService {
    @Resource
    private TradePositionDoMapper tradePositionDoMapper;

    @Override
    public List<TradePositionDo> listByUserIdAndMockTypeAndCode(Integer userId, Integer mockType, String code) {
        return this.lambdaQuery()
                .eq(TradePositionDo::getUserId,userId)
                .eq(TradePositionDo::getMockType,mockType)
                .eq(StringUtils.hasText(code),TradePositionDo::getCode,code)
                .list();
    }
    @Override
    public void syncUseAmountByXxlJob() {
        // 删除可用数量为 0 的数据。
        tradePositionDoMapper.deleteUseAmoutIsNull();
        tradePositionDoMapper.syncUseAmount();
        ;
    }

    @Override
    public void deleteByUserIdAndMockType(Integer userId, Integer mockType) {
        tradePositionDoMapper.deleteByUserIdAndMockType(userId,mockType);
    }
}
