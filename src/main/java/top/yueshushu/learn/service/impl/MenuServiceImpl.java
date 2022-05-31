package top.yueshushu.learn.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import top.yueshushu.learn.assembler.MenuAssembler;
import top.yueshushu.learn.domainservice.MenuDomainService;
import top.yueshushu.learn.entity.Menu;
import top.yueshushu.learn.mode.vo.MenuVo;
import top.yueshushu.learn.domain.MenuDo;
import top.yueshushu.learn.mapper.MenuDoMapper;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 菜单表 自定义的
 * </p>
 *
 * @author 岳建立
 * @since 2022-01-02
 */
@Service
public class MenuServiceImpl implements MenuService {
    @Resource
    private MenuDomainService menuDomainService;
    @Resource
    private MenuAssembler menuAssembler;
    @Override
    public OutputResult getMenuListByUid(Integer userId) {
        //如果不为空的话，则进行转换后返回
        return OutputResult.buildSucc(
                listMenuListByUid(userId)
        );
    }

    @Override
    public List<MenuVo> listMenuListByUid(Integer userId) {
        //获取所有的权限
        List<MenuDo> menuDoList = menuDomainService.listPermissionByUidAndRoleId(
                userId,null
        );
        if(CollectionUtils.isEmpty(menuDoList)){
            return Collections.emptyList();
        }

        List<Menu> menuList = new ArrayList<>();
        menuDoList.forEach(
                n-> menuList.add(
                        menuAssembler.doToEntity(
                                n
                        )
                )
        );

        // 再转换成相应的 VO 对象

        List<MenuVo> menuVoList = new ArrayList<>();
        menuList.forEach(
                n-> menuVoList.add(
                        menuAssembler.entityToVo(
                                n
                        )
                )
        );
        return menuVoList;
    }
}
