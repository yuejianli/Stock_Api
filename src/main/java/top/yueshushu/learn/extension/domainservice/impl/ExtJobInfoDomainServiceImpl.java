package top.yueshushu.learn.extension.domainservice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.yueshushu.learn.extension.domain.ExtJobInfoDo;
import top.yueshushu.learn.extension.domainservice.ExtJobInfoDomainService;
import top.yueshushu.learn.extension.mapper.ExtJobInfoDoMapper;

import javax.annotation.Resource;

/**
 * @Description jobInfo的处理
 * @Author yuejianli
 * @Date 2022/5/20 23:23
 **/
@Service
@Slf4j
public class ExtJobInfoDomainServiceImpl extends ServiceImpl<ExtJobInfoDoMapper, ExtJobInfoDo>
        implements ExtJobInfoDomainService {
    @Resource
    private ExtJobInfoDoMapper extJobInfoDoMapper;

    @Override
    public ExtJobInfoDo getByCode(String code) {
        return this.lambdaQuery()
                .eq(ExtJobInfoDo::getCode, code)
                .one();
    }
}
