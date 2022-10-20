package top.yueshushu.learn.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yueshushu.learn.annotation.AuthToken;
import top.yueshushu.learn.business.HolidayCalendarBusiness;
import top.yueshushu.learn.mode.ro.HolidayRo;
import top.yueshushu.learn.response.OutputResult;

import javax.annotation.Resource;

/**
 * <p>
 * 法定假期表(只写入法定的类型) 我是自定义的
 * </p>
 *
 * @author 岳建立
 * @date 2022-01-02
 */
@RestController
@RequestMapping("/holidayCalendar")
@Api("节假日信息假期")
public class HolidayCalendarController {
    @Resource
    private HolidayCalendarBusiness holidayCalendarBusiness;

    @PostMapping("/list")
    @ApiOperation("查询假期信息")
    public OutputResult list(@RequestBody HolidayRo holidayRo) {
        return holidayCalendarBusiness.listHoliday(holidayRo);
    }

    @PostMapping("/sync")
    @ApiOperation("同步假期")
    @AuthToken
    public OutputResult sync(@RequestBody HolidayRo holidayRo) {
        return holidayCalendarBusiness.syncYear(holidayRo.getYear());
    }

}
