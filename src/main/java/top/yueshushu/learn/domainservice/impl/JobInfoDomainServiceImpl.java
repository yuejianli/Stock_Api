package top.yueshushu.learn.domainservice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.yueshushu.learn.domain.JobInfoDo;
import top.yueshushu.learn.domainservice.JobInfoDomainService;
import top.yueshushu.learn.mapper.JobInfoDoMapper;

import javax.annotation.Resource;

/**
 * @Description jobInfo的处理
 * @Author yuejianli
 * @Date 2022/5/20 23:23
 **/
@Service
@Slf4j
public class JobInfoDomainServiceImpl extends ServiceImpl<JobInfoDoMapper, JobInfoDo>
        implements JobInfoDomainService {
    @Resource
    private JobInfoDoMapper jobInfoDoMapper;

    @Override
    public JobInfoDo getByCode(String code) {
        return this.lambdaQuery()
                .eq(JobInfoDo::getCode, code)
                .one();
    }
}
