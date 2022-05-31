package top.yueshushu.learn.business.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.yueshushu.learn.business.HolidayCalendarBusiness;
import top.yueshushu.learn.business.UserBusiness;
import top.yueshushu.learn.entity.User;
import top.yueshushu.learn.mode.ro.HolidayRo;
import top.yueshushu.learn.mode.ro.UserRo;
import top.yueshushu.learn.mode.vo.MenuVo;
import top.yueshushu.learn.mode.vo.UserVo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.service.HolidayCalendarService;
import top.yueshushu.learn.service.MenuService;
import top.yueshushu.learn.service.UserService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description 假期编排层
 * @Author 岳建立
 * @Date 2022/5/20 22:33
 **/
@Service
@Slf4j
public class HolidayCalendarBusinessImpl implements HolidayCalendarBusiness {
    @Resource
    private HolidayCalendarService holidayCalendarService;
    @Override
    public OutputResult listHoliday(HolidayRo holidayRo) {
        return holidayCalendarService.listHoliday(
                holidayRo
        );
    }

    @Override
    public OutputResult syncYear(Integer year) {
        return holidayCalendarService.syncYear(
                year
        );
    }
}
