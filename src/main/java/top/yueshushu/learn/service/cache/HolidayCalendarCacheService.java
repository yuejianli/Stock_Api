package top.yueshushu.learn.service.cache;

import java.util.Date;
import java.util.List;

/**
 * @Description 假期数据缓存处理
 * @Author yuejianli
 * @Date 2022/5/21 23:27
 **/
public interface HolidayCalendarCacheService {
    /**
     * 查询当前年的日期信息
     * @param year 当前年
     * @return 查询当前年的日期信息
     */
    List<String> listHolidayDateByYear(Integer year);
}
