package top.yueshushu.learn.domainservice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.yueshushu.learn.domain.RebootDo;
import top.yueshushu.learn.domainservice.RebootDomainService;
import top.yueshushu.learn.mapper.RebootDoMapper;

/**
 * 用途描述
 *
 * @author yuejianli
 * @date 2023-01-11
 */
@Service
public class RebootDomainServiceImpl extends ServiceImpl<RebootDoMapper, RebootDo>
        implements RebootDomainService {


    @Override
    public RebootDo getByCode(String code) {
        return this.lambdaQuery()
                .eq(RebootDo::getCode, code)
                .one();
    }
}
