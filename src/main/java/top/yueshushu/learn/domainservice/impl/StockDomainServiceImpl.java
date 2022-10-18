package top.yueshushu.learn.domainservice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.yueshushu.learn.domain.StockDo;
import top.yueshushu.learn.domainservice.StockDomainService;
import top.yueshushu.learn.mapper.StockDoMapper;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description TODO
 * @Author yuejianli
 * @Date 2022/5/20 23:23
 **/
@Service
@Slf4j
public class StockDomainServiceImpl extends ServiceImpl<StockDoMapper, StockDo>
        implements StockDomainService {
    @Resource
    private StockDoMapper stockDoMapper;
    @Override
    public List<StockDo> selectByKeyword(String keyword) {
        return stockDoMapper.selectByKeyword(keyword);
    }

    @Override
    public StockDo getByCode(String code) {
        List<StockDo> list = this.lambdaQuery()
                .eq(
                        StockDo::getCode, code
                ).list();
        if (CollectionUtils.isEmpty(list)){
            return null;
        }
        return list.get(0);
    }
    @Override
    public StockDo getByFullCode(String fullCode) {
        List<StockDo> list = this.lambdaQuery()
                .eq(
                        StockDo::getFullCode, fullCode
                ).list();
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<String> listAllCode() {
        return stockDoMapper.listAllCode();
    }

    @Override
    public List<StockDo> listByCodes(List<String> codeList) {
        return this.lambdaQuery()
                .in(
                        StockDo::getCode, codeList
                ).list();
    }
}
