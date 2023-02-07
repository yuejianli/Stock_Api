package top.yueshushu.learn.domainservice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.yueshushu.learn.domain.StockBkDo;
import top.yueshushu.learn.domainservice.StockBkDomainService;
import top.yueshushu.learn.mapper.StockBkMapper;

import java.util.List;

/**
 * <p>
 * 股票版块信息 服务实现类
 * </p>
 *
 * @author 岳建立
 * @since 2023-02-07
 */
@Service
public class StockBkDomainServiceImpl extends ServiceImpl<StockBkMapper, StockBkDo> implements StockBkDomainService {

    @Override
    public List<StockBkDo> listByOrder() {
        return this.lambdaQuery()
                .orderByDesc(StockBkDo::getHotNum)
                .orderByAsc(StockBkDo::getCode)
                .list();
    }

    @Override
    public StockBkDo selectByCode(String code) {
        return this.lambdaQuery()
                .eq(StockBkDo::getCode, code)
                .one();
    }
}
