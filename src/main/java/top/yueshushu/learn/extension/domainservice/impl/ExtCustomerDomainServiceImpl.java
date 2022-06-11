package top.yueshushu.learn.extension.domainservice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.yueshushu.learn.extension.domain.ExtCustomerDo;
import top.yueshushu.learn.extension.domainservice.ExtCustomerDomainService;
import top.yueshushu.learn.extension.mapper.ExtCustomerDoMapper;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用途描述
 *
 * @author yuejianli
 * @date 2022-06-09
 */
@Service
public class ExtCustomerDomainServiceImpl extends ServiceImpl<ExtCustomerDoMapper, ExtCustomerDo>
        implements ExtCustomerDomainService {
    @Resource
    private ExtCustomerDoMapper extCustomerDoMapper;

    @Override
    public List<ExtCustomerDo> listByKeyword(String keyword) {
        return extCustomerDoMapper.listByKeyword(keyword);
    }

    @Override
    public ExtCustomerDo getByAccount(String userAccount) {
        return this.lambdaQuery()
                .eq(ExtCustomerDo::getUserAccount, userAccount)
                .one();
    }
}
