package top.yueshushu.learn.domainservice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.yueshushu.learn.domain.RoleDo;
import top.yueshushu.learn.domainservice.RoleDomainService;
import top.yueshushu.learn.mapper.RoleMapper;

import java.util.List;

/**
 * 用途描述
 *
 * @author yuejianli
 * @date 2023-03-16
 */
@Service
public class RoleDomainServiceImpl extends ServiceImpl<RoleMapper, RoleDo>
        implements RoleDomainService {

    @Override
    public List<RoleDo> listByName(String name) {
        return this.lambdaQuery()
                .like(StringUtils.hasText(name), RoleDo::getName, name)
                .list();
    }
}
