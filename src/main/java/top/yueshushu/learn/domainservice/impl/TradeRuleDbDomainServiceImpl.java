package top.yueshushu.learn.domainservice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.yueshushu.learn.domain.TradeRuleDbDo;
import top.yueshushu.learn.domainservice.TradeRuleDbDomainService;
import top.yueshushu.learn.mapper.TradeRuleDbMapper;

import java.util.List;

/**
 * <p>
 * 打板交易规则配置 服务实现类
 * </p>
 *
 * @author 岳建立
 * @since 2023-02-10
 */
@Service
public class TradeRuleDbDomainServiceImpl extends ServiceImpl<TradeRuleDbMapper, TradeRuleDbDo> implements TradeRuleDbDomainService {

    @Override
    public List<TradeRuleDbDo> listByQuery(Integer userId, Integer mockType) {
        return this.lambdaQuery()
                .eq(TradeRuleDbDo::getUserId, userId)
                .eq(TradeRuleDbDo::getMockType, mockType)
                .list();
    }

    @Override
    public TradeRuleDbDo getByQuery(Integer userId, Integer mockType) {
        List<TradeRuleDbDo> tradeRuleDbDos = listByQuery(userId, mockType);
        if (CollectionUtils.isEmpty(tradeRuleDbDos)) {
            return null;
        }
        return tradeRuleDbDos.get(0);
    }
}
