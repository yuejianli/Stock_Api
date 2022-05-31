package top.yueshushu.learn.service.cache.impl;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.yueshushu.learn.common.Const;
import top.yueshushu.learn.domain.HolidayCalendarDo;
import top.yueshushu.learn.domainservice.HolidayCalendarDomainService;
import top.yueshushu.learn.service.cache.HolidayCalendarCacheService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description TODO
 * @Author yuejianli
 * @Date 2022/5/21 23:28
 **/
@Slf4j
@Service
public class HolidayCalendarCacheServiceImpl implements HolidayCalendarCacheService {
    @Resource
    private HolidayCalendarDomainService holidayCalendarDomainService;

    @Override
    @Cacheable(value= Const.HOLIDAY_CALENDAR_CACHE,key = "#year")
    public List<String> listHolidayDateByYear(Integer year){
        List<HolidayCalendarDo> holidayCalendarDoList = holidayCalendarDomainService.listByYear(year);
        if(CollectionUtils.isEmpty(holidayCalendarDoList)){
            return Collections.EMPTY_LIST;
        }
        List<String> result = new ArrayList<>(holidayCalendarDoList.size());
        holidayCalendarDoList.forEach(
                n-> result.add(
                        DateUtil.format(
                                n.getHolidayDate(),
                                Const.SIMPLE_DATE_FORMAT
                        )
                )
        );
        return result;
    }

}
