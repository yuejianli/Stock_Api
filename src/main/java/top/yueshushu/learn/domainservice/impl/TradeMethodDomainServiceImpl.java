package top.yueshushu.learn.domainservice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import top.yueshushu.learn.domain.TradeMethodDo;
import top.yueshushu.learn.domain.TradeUserDo;
import top.yueshushu.learn.domainservice.TradeMethodDomainService;
import top.yueshushu.learn.mapper.TradeMethodDoMapper;

import java.util.List;

/**
 * @Description 交易方法实体
 * @Author yuejianli
 * @Date 2022/5/21 6:19
 **/
@Service
public class TradeMethodDomainServiceImpl extends ServiceImpl<TradeMethodDoMapper, TradeMethodDo>
        implements TradeMethodDomainService {

    @Override
    public TradeMethodDo getMethodByCode(String methodCode) {

        List<TradeMethodDo> list = this.lambdaQuery()
                .eq(
                        TradeMethodDo::getCode, methodCode
                ).list();
        if (CollectionUtils.isEmpty(list)){
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<TradeMethodDo> listByName(String name) {
        return this.lambdaQuery()
                .like(
                        !StringUtils.isEmpty(name),TradeMethodDo::getName,name
                ).list();
    }
}
