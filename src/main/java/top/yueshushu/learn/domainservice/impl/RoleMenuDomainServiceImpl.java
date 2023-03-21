package top.yueshushu.learn.domainservice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.yueshushu.learn.domain.RoleMenuDo;
import top.yueshushu.learn.domainservice.RoleMenuDomainService;
import top.yueshushu.learn.mapper.RoleMenuMapper;

import java.util.List;

/**
 * 用途描述
 *
 * @author yuejianli
 * @date 2023-03-20
 */
@Service
public class RoleMenuDomainServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenuDo>
        implements RoleMenuDomainService {

    @Override
    public List<RoleMenuDo> listByCondition(Integer roleId) {
        return this.lambdaQuery()
                .eq(roleId != null, RoleMenuDo::getRoleId, roleId)
                .list();
    }

    @Override
    public void removeByRoleId(Integer roleId) {
        this.lambdaUpdate()
                .eq(RoleMenuDo::getRoleId, roleId)
                .remove();
    }
}
