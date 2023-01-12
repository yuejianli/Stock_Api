package top.yueshushu.learn.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import top.yueshushu.learn.assembler.HolidayCalendarAssembler;
import top.yueshushu.learn.common.Const;
import top.yueshushu.learn.common.ResultCode;
import top.yueshushu.learn.domain.HolidayCalendarDo;
import top.yueshushu.learn.domainservice.HolidayCalendarDomainService;
import top.yueshushu.learn.mode.ro.HolidayRo;
import top.yueshushu.learn.mode.vo.HolidayCalendarVo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.response.PageResponse;
import top.yueshushu.learn.service.HolidayCalendarService;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 法定假期表(只写入法定的类型) 自定义的
 * </p>
 *
 * @author 岳建立
 * @since 2022-01-02
 */
@Service
@Slf4j(topic = "日历同步")
public class HolidayCalendarServiceImpl implements HolidayCalendarService {
    @Resource
    private HolidayCalendarDomainService holidayCalendarDomainService;
    @Resource
    private HolidayCalendarAssembler holidayCalendarAssembler;
    @Resource(name = "restTemplate")
    private RestTemplate restTemplate;
    @Override
    public OutputResult listHoliday(HolidayRo holidayRo) {
        PageHelper.startPage(holidayRo.getPageNum(),holidayRo.getPageSize());
        List<HolidayCalendarDo> holidayCalendarDoList = holidayCalendarDomainService.listByYear(holidayRo.getYear());
        if (CollectionUtils.isEmpty(holidayCalendarDoList)){
            return OutputResult.buildSucc(
                    PageResponse.emptyPageResponse()
            );
        }

        List<HolidayCalendarVo> pageResultList = new ArrayList<>(holidayCalendarDoList.size());
        holidayCalendarDoList.forEach(
                n->{
                    pageResultList.add(
                            holidayCalendarAssembler.entityToVo(
                                    holidayCalendarAssembler.doToEntity(
                                            n
                                    )
                            )
                    );
                }
        );
        PageInfo pageInfo=new PageInfo<>(pageResultList);
        return OutputResult.buildSucc(new PageResponse<HolidayCalendarVo>(pageInfo.getTotal(),
                pageInfo.getList()));


    }
    @Override
    @CacheEvict(value= Const.HOLIDAY_CALENDAR_CACHE,key = "#year")
    public OutputResult syncYear(Integer year) {
        //看是否存在天数.
        int count = Optional.of(holidayCalendarDomainService.countByYear(year)).orElse(0);
        if(count>0){
            log.info(">>>已经存在 {}年的假期数据，不需要同步",year);
            return OutputResult.buildAlert(
                    ResultCode.HOLIDAY_EXISTS
            );
        }
        Map<?, ?> data ;
        try{
            data = restTemplate.getForObject("http://tool.bitefu.net/jiari/?d=" + year, Map.class);
        }catch (Exception e){
            log.error("获取同步假期数据时出现异常",e);
            return OutputResult.buildFail();
        }
        @SuppressWarnings("unchecked")
        Map<String, Integer> dateInfo = (Map<String, Integer>) data.get(String.valueOf(year));
        List<HolidayCalendarDo> list = dateInfo.entrySet().stream().filter(entry -> entry.getValue() != 0).map(entry -> {
            Date date;
            try {
                date = DateUtils.parseDate(year + entry.getKey(), "yyyyMMdd");
            } catch (ParseException e) {
                throw new IllegalArgumentException(e);
            }
            HolidayCalendarDo holidayCalendarDo = new HolidayCalendarDo();
            holidayCalendarDo.setHolidayDate(date);
            holidayCalendarDo.setCurrYear(year);
            holidayCalendarDo.setDateType(3);
            return holidayCalendarDo;
        }).collect(Collectors.toList());
        holidayCalendarDomainService.saveBatch(list);
        return OutputResult.buildSucc();
    }
}
