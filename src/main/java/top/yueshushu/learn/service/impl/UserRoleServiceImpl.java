package top.yueshushu.learn.service.impl;

import top.yueshushu.learn.domain.UserRoleDo;
import top.yueshushu.learn.mapper.UserRoleMapper;
import top.yueshushu.learn.service.UserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 员工角色关联表 自定义的
 * </p>
 *
 * @author 岳建立
 * @since 2022-01-02
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRoleDo> implements UserRoleService {

}
