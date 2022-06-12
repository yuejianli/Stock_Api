package top.yueshushu.learn.extension.domainservice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.yueshushu.learn.extension.domain.ExtJobInterfaceDo;
import top.yueshushu.learn.extension.domainservice.ExtJobInterfaceDomainService;
import top.yueshushu.learn.extension.mapper.ExtJobInterfaceDoMapper;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description jobInfo的处理
 * @Author yuejianli
 * @Date 2022/5/20 23:23
 **/
@Service
@Slf4j
public class ExtJobInterfaceDomainServiceImpl extends ServiceImpl<ExtJobInterfaceDoMapper, ExtJobInterfaceDo>
        implements ExtJobInterfaceDomainService {
    @Resource
    private ExtJobInterfaceDoMapper extJobInterfaceDoMapper;

    @Override
    public List<ExtJobInterfaceDo> listByJobId(Integer extJobId) {
        return this.lambdaQuery()
                .eq(ExtJobInterfaceDo::getExtJobId, extJobId)
                .list();
    }
}
