package top.yueshushu.learn.business.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.yueshushu.learn.business.TradeUserBusiness;
import top.yueshushu.learn.mode.ro.TradeUserRo;
import top.yueshushu.learn.mode.vo.MenuVo;
import top.yueshushu.learn.mode.vo.TradeUserVo;
import top.yueshushu.learn.mode.vo.UserVo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.service.MenuService;
import top.yueshushu.learn.service.TradeUserService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description 交易用户实现编排层
 * @Author yuejianli
 * @Date 2022/5/20 23:54
 **/
@Service
@Slf4j
public class TradeUserBusinessImpl implements TradeUserBusiness {
    @Resource
    private TradeUserService tradeUserService;
    @Resource
    private MenuService menuService;

    @Override
    public OutputResult login(TradeUserRo tradeUserRo) {
        OutputResult outputResult = tradeUserService.login(tradeUserRo);
        if (!outputResult.getSuccess()){
            return outputResult;
        }
        TradeUserVo tradeUserVo = (TradeUserVo) outputResult.getData();

        //获取权限信息
        List<MenuVo> menuVoList = menuService.listMenuListByUid(tradeUserRo.getId());
        tradeUserVo.setMenuList(menuVoList);

        return OutputResult.buildSucc(tradeUserVo);
    }
}
