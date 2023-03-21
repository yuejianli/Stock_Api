package top.yueshushu.learn.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.yueshushu.learn.domain.UserRoleDo;
import top.yueshushu.learn.mapper.UserRoleMapper;
import top.yueshushu.learn.service.UserRoleService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<UserRoleDo> listByUserIdAndRoleId(Integer userId, Integer roleId) {
        return this.lambdaQuery()
                .eq(userId != null, UserRoleDo::getUserId, userId)
                .eq(roleId != null, UserRoleDo::getRoleId, roleId)
                .list();
    }

    @Override
    public void configRole(Integer userId, Integer roleId) {
        if (null == roleId) {
            return;
        }
        List<UserRoleDo> userRoleDoList = listByUserIdAndRoleId(userId, null);
        if (!CollectionUtils.isEmpty(userRoleDoList)) {
            List<Integer> ids = userRoleDoList.stream().map(UserRoleDo::getId).collect(Collectors.toList());
            removeByIds(ids);
        }
        UserRoleDo userRoleDo = new UserRoleDo();
        userRoleDo.setUserId(userId);
        userRoleDo.setRoleId(roleId);
        userRoleDo.setCreateTime(LocalDateTime.now());
        save(userRoleDo);
    }
}
