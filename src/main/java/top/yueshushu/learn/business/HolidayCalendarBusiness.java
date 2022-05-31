package top.yueshushu.learn.business;

import top.yueshushu.learn.mode.ro.HolidayRo;
import top.yueshushu.learn.mode.ro.UserRo;
import top.yueshushu.learn.response.OutputResult;

/**
 * @Description 假期的编排层处理
 * @Author 岳建立
 * @Date 2022/5/20 22:33
 **/
public interface HolidayCalendarBusiness {
    /**
     * 查询当前年的假期信息
     * @param holidayRo 当前年份的查询对象
     * @return 查询当前年的假期信息
     */
    OutputResult listHoliday(HolidayRo holidayRo);

    /**
     * 同步当前年的假期信息
     * @param year 当前年份
     * @return 同步当前年的假期信息
     */
    OutputResult syncYear(Integer year);
}
