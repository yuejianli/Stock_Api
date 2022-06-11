package top.yueshushu.learn.extension.domainservice.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.yueshushu.learn.extension.domain.ExtCustomerJobDo;
import top.yueshushu.learn.extension.domainservice.ExtCustomerJobDomainService;
import top.yueshushu.learn.extension.mapper.ExtCustomerJobDoMapper;
import top.yueshushu.learn.extension.mapper.ExtInterfaceDoMapper;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description jobInfo的处理
 * @Author yuejianli
 * @Date 2022/5/20 23:23
 **/
@Service
@Slf4j
public class ExtCustomerJobDomainServiceImpl extends ServiceImpl<ExtCustomerJobDoMapper, ExtCustomerJobDo>
        implements ExtCustomerJobDomainService {
    @Resource
    private ExtInterfaceDoMapper extInterfaceDoMapper;

    @Override
    public List<ExtCustomerJobDo> listByCustomerIdAndJobId(Integer extCustomerId, Integer extJobId) {
        return this.lambdaQuery()
                .eq(ExtCustomerJobDo::getExtCustomerId, extCustomerId)
                .eq(ExtCustomerJobDo::getExtJobId, extJobId)
                .list();
    }

    @Override
    public void removeByCustomerIdAndJobId(Integer extCustomerId, Integer extJobId) {
        LambdaQueryWrapper<ExtCustomerJobDo> lq = Wrappers.lambdaQuery();
        lq.eq(ExtCustomerJobDo::getExtCustomerId, extCustomerId);
        lq.eq(ExtCustomerJobDo::getExtJobId, extJobId);
        remove(lq);
    }

    @Override
    public List<ExtCustomerJobDo> listByJobId(Integer jobId) {
        return this.lambdaQuery()
                .eq(ExtCustomerJobDo::getExtJobId, jobId)
                .list();
    }

    @Override
    public void removeByUserId(Integer customerId) {
        LambdaQueryWrapper<ExtCustomerJobDo> lq = Wrappers.lambdaQuery();
        lq.eq(ExtCustomerJobDo::getExtCustomerId, customerId);
        remove(lq);
    }
}
