package top.yueshushu.learn.domainservice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import top.yueshushu.learn.domain.StockSelectedDo;
import top.yueshushu.learn.domainservice.StockSelectedDomainService;
import top.yueshushu.learn.mapper.StockSelectedDoMapper;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @Description TODO
 * @Author yuejianli
 * @Date 2022/5/20 23:23
 **/
@Service
@Slf4j
public class StockSelectedDomainServiceImpl extends ServiceImpl<StockSelectedDoMapper, StockSelectedDo>
        implements StockSelectedDomainService {
    @Resource
    private StockSelectedDoMapper stockSelectedDoMapper;
    @Override
    public List<StockSelectedDo> listByUserIdAndKeyword(Integer userId, String keyword) {
        return stockSelectedDoMapper.listByUserIdAndKeyword(
                userId,keyword
        );
    }

    @Override
    public StockSelectedDo getByUserIdAndCodeAndStatus(Integer userId, String stockCode, Integer status) {
        Assert.notNull(
                stockCode,
                "股票编码不能为空"
        );
        List<StockSelectedDo> stockSelectedDoList = listByUserIdAndCodeAndStatus(userId, stockCode, status);
        if (CollectionUtils.isEmpty(stockSelectedDoList)){
            return null;
        }
        return stockSelectedDoList.get(0);
    }

    @Override
    public List<StockSelectedDo> listByUserIdAndCodeAndStatus(Integer userId, String stockCode, Integer status) {
       return this.lambdaQuery()
                .eq(userId !=null, StockSelectedDo::getUserId,userId)
                .eq(stockCode!=null, StockSelectedDo::getStockCode,stockCode)
                .eq(status!=null, StockSelectedDo::getStatus,status)
                .list();
    }


    @Override
    public int countByUserIdAndStatus(Integer userId, Integer status) {
        return Optional.ofNullable(
                this.lambdaQuery()
                        .eq(userId !=null, StockSelectedDo::getUserId,userId)
                        .eq(status!=null, StockSelectedDo::getStatus,status)
                        .count()
        ).orElse(0);
    }

    @Override
    public List<String> findCodeList(Integer userId) {
        return stockSelectedDoMapper.findCodeList(userId);
    }
}
