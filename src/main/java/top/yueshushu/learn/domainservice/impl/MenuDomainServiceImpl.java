package top.yueshushu.learn.domainservice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.yueshushu.learn.domain.MenuDo;
import top.yueshushu.learn.domainservice.MenuDomainService;
import top.yueshushu.learn.mapper.MenuDoMapper;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description TODO
 * @Author yuejianli
 * @Date 2022/5/21 0:01
 **/
@Service
@Slf4j
public class MenuDomainServiceImpl extends ServiceImpl<MenuDoMapper, MenuDo>
    implements MenuDomainService {
    @Resource
    private MenuDoMapper menuDoMapper;
    @Override
    public List<MenuDo> listPermissionByUidAndRoleId(Integer userId, Integer ruleId) {
        return menuDoMapper.listPermissionByUidAndRoleId(userId,ruleId);

    }
}
