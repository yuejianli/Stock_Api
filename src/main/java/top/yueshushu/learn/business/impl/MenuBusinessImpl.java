package top.yueshushu.learn.business.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.yueshushu.learn.business.MenuBusiness;
import top.yueshushu.learn.mode.vo.MenuVo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.service.MenuService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description 菜单实现编排层
 * @Author yuejianli
 * @Date 2022/5/20 23:54
 **/
@Service
@Slf4j
public class MenuBusinessImpl implements MenuBusiness {
    @Resource
    private MenuService menuService;

    @Override
    public OutputResult getMenuListByUid(Integer userId) {
        return menuService.getMenuListByUid(userId);
    }
}
