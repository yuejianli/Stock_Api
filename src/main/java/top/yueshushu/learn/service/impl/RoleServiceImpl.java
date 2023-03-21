package top.yueshushu.learn.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.yueshushu.learn.assembler.RoleAssembler;
import top.yueshushu.learn.common.ResultCode;
import top.yueshushu.learn.domain.RoleDo;
import top.yueshushu.learn.domain.UserRoleDo;
import top.yueshushu.learn.domainservice.RoleDomainService;
import top.yueshushu.learn.entity.Role;
import top.yueshushu.learn.enumtype.DataFlagType;
import top.yueshushu.learn.mode.ro.RoleRo;
import top.yueshushu.learn.mode.vo.RoleVo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.response.PageResponse;
import top.yueshushu.learn.service.RoleService;
import top.yueshushu.learn.service.UserRoleService;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 角色表 自定义的
 * </p>
 *
 * @author 岳建立
 * @since 2022-01-02
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Resource
    private RoleDomainService roleDomainService;
    @Resource
    private RoleAssembler roleAssembler;
    @Resource
    private UserRoleService userRoleService;

    @Override
    public OutputResult list(RoleRo roleRo) {
        Page<Object> pageGithubResult = PageHelper.startPage(roleRo.getPageNum(), roleRo.getPageSize());
        List<RoleDo> roleDoList = roleDomainService.listByName(roleRo.getName());
        if (CollectionUtils.isEmpty(roleDoList)) {
            return OutputResult.buildSucc(PageResponse.emptyPageResponse());
        }
        List<RoleVo> roleVoList = roleAssembler.entityToVoList(roleAssembler.doToEntityList(roleDoList));
        return OutputResult.buildSucc(new PageResponse<>(pageGithubResult.getTotal(), roleVoList));
    }

    @Override
    public OutputResult add(RoleRo roleRo) {
        int size = roleDomainService.listByName(roleRo.getName()).size();
        if (size > 0) {
            return OutputResult.buildAlert(ResultCode.ROLE_EXIST);
        }
        Role role = new Role();
        role.setName(roleRo.getName());
        role.setDescription(roleRo.getDescription());
        role.setCreateTime(new Date());
        role.setUpdateTime(new Date());
        role.setFlag(DataFlagType.NORMAL.getCode());

        // 进行保存
        roleDomainService.save(roleAssembler.entityToDo(role));
        return OutputResult.buildSucc();
    }

    @Override
    public OutputResult update(RoleRo roleRo) {
        RoleDo roleDo = roleDomainService.getById(roleRo.getId());
        if (null == roleDo) {
            return OutputResult.buildFail(ResultCode.INVALID_PARAM);
        }
        if (!roleDo.getName().equals(roleRo.getName())) {
            int size = roleDomainService.listByName(roleRo.getName()).size();
            if (size > 0) {
                return OutputResult.buildAlert(ResultCode.ROLE_EXIST);
            }
        }
        RoleDo editRoleDo = new RoleDo();
        editRoleDo.setId(roleDo.getId());
        editRoleDo.setName(roleRo.getName());
        editRoleDo.setDescription(roleRo.getDescription());
        editRoleDo.setUpdateTime(new Date());
        roleDomainService.updateById(editRoleDo);
        return OutputResult.buildSucc();
    }

    @Override
    public OutputResult delete(RoleRo roleRo) {
        RoleDo roleDo = roleDomainService.getById(roleRo.getId());
        if (null == roleDo) {
            return OutputResult.buildFail(ResultCode.INVALID_PARAM);
        }
        // 看角色是否配置了用户， 如果配置了用户，则不能删除角色。
        List<UserRoleDo> userRoleDoList = userRoleService.listByUserIdAndRoleId(null, roleRo.getId());
        if (!CollectionUtils.isEmpty(userRoleDoList)) {
            return OutputResult.buildAlert(ResultCode.ROLE_USER_EXIST);
        }
        roleDomainService.removeById(roleRo.getId());
        return OutputResult.buildSucc();
    }

    @Override
    public RoleDo getById(Integer id) {
        return roleDomainService.getById(id);
    }
}
