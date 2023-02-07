package top.yueshushu.learn.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.yueshushu.learn.domain.StockBkDo;
import top.yueshushu.learn.domainservice.StockBkDomainService;
import top.yueshushu.learn.mapper.StockBkMapper;
import top.yueshushu.learn.service.StockBkService;

import javax.annotation.Resource;
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
public class StockBkServiceImpl extends ServiceImpl<StockBkMapper, StockBkDo> implements StockBkService {
    @Resource
    private StockBkDomainService stockBkDomainService;

    @Override
    public List<StockBkDo> listByOrder() {
        return stockBkDomainService.listByOrder();
    }

    @Override
    public StockBkDo selectByCode(String code) {
        return stockBkDomainService.selectByCode(code);
    }
}
