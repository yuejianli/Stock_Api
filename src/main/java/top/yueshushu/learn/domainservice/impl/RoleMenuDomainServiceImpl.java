package top.yueshushu.learn.domainservice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.yueshushu.learn.domain.RoleMenuDo;
import top.yueshushu.learn.mapper.RoleMenuMapper;
import top.yueshushu.learn.service.RoleMenuService;

/**
 * <p>
 * 角色权限表 自定义的
 * </p>
 *
 * @author 岳建立
 * @since 2022-01-02
 */
@Service
public class RoleMenuDomainServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenuDo> implements RoleMenuService {

}
