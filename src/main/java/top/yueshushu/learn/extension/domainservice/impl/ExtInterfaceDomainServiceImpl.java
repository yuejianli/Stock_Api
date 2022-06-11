package top.yueshushu.learn.extension.domainservice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.yueshushu.learn.extension.domain.ExtInterfaceDo;
import top.yueshushu.learn.extension.domainservice.ExtInterfaceDomainService;
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
public class ExtInterfaceDomainServiceImpl extends ServiceImpl<ExtInterfaceDoMapper, ExtInterfaceDo>
        implements ExtInterfaceDomainService {
    @Resource
    private ExtInterfaceDoMapper extInterfaceDoMapper;

    @Override
    public List<ExtInterfaceDo> listByName(String keyword) {
        return this.lambdaQuery()
                .like(
                        !StringUtils.isEmpty(keyword), ExtInterfaceDo::getName, keyword
                ).list();
    }
}
