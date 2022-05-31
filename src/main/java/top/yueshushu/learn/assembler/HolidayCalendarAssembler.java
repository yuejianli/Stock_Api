package top.yueshushu.learn.assembler;

import org.mapstruct.Mapper;
import top.yueshushu.learn.domain.HolidayCalendarDo;
import top.yueshushu.learn.entity.HolidayCalendar;
import top.yueshushu.learn.mode.vo.HolidayCalendarVo;

/**
 * @Description 假期信息转换器
 * @Author yuejianli
 * @Date 2022/5/20 23:01
 **/
@Mapper(componentModel = "spring" )
public interface HolidayCalendarAssembler {
    /**
     * 假期信息 domain 转换成实体entity
     * @param holidayCalendarDo 假期信息Do
     * @return 假期信息 domain 转换成实体entity
     */
    HolidayCalendar doToEntity(HolidayCalendarDo holidayCalendarDo);

    /**
     * 假期信息 entity 转换成 domain
     * @param holidayCalendar 假期信息
     * @return 假期信息 entity 转换成 domain
     */
    HolidayCalendarDo entityToDo(HolidayCalendar holidayCalendar);

    /**
     * 假期信息 entity 转换成 vo
     * @param holidayCalendar 假期信息
     * @return 假期信息 entity 转换成 vo
     */
    HolidayCalendarVo entityToVo(HolidayCalendar holidayCalendar);
}
