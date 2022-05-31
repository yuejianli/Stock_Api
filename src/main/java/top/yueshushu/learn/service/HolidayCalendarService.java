package top.yueshushu.learn.service;

import top.yueshushu.learn.mode.ro.HolidayRo;
import top.yueshushu.learn.domain.HolidayCalendarDo;
import com.baomidou.mybatisplus.extension.service.IService;
import top.yueshushu.learn.response.OutputResult;

/**
 * <p>
 * 法定假期表(只写入法定的类型) 自定义的
 * </p>
 *
 * @author 岳建立
 * @since 2022-01-02
 */
public interface HolidayCalendarService{
    /**
     * 查询当前年的假期信息
     * @param holidayRo 当前年对象
     * @return 查询当前年的假期信息
     */
    OutputResult listHoliday(HolidayRo holidayRo);
    /**
     * 同步该年的假期信息
     * @return
     */
    OutputResult syncYear(Integer year);
}
