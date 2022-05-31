package top.yueshushu.learn.domainservice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.yueshushu.learn.domain.HolidayCalendarDo;
import top.yueshushu.learn.domainservice.HolidayCalendarDomainService;
import top.yueshushu.learn.mapper.HolidayCalendarDoMapper;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description TODO
 * @Author yuejianli
 * @Date 2022/5/20 23:23
 **/
@Service
@Slf4j
public class HolidayCalendarDomainServiceImpl extends ServiceImpl<HolidayCalendarDoMapper, HolidayCalendarDo>
        implements HolidayCalendarDomainService {

    @Override
    public List<HolidayCalendarDo> listByYear(Integer year) {
        return this.lambdaQuery()
                .eq(HolidayCalendarDo::getCurrYear,year)
                .orderByAsc(HolidayCalendarDo::getHolidayDate)
                .list();
    }

    @Override
    public Integer countByYear(Integer year) {
        return this.lambdaQuery()
                .eq(HolidayCalendarDo::getCurrYear,year)
                .count();
    }
}
