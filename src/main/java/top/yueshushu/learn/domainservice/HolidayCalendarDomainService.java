package top.yueshushu.learn.domainservice;

import com.baomidou.mybatisplus.extension.service.IService;
import top.yueshushu.learn.domain.HolidayCalendarDo;
import top.yueshushu.learn.domain.UserDo;

import java.util.List;

/**
 * @Description 用户的操作
 * @Author yuejianli
 * @Date 2022/5/20 23:23
 **/
public interface HolidayCalendarDomainService extends IService<HolidayCalendarDo> {
    /**
     * 根据年份查询当前的假期信息
     * @param year 当前年份
     * @return 返回当前年的假期信息
     */
    List<HolidayCalendarDo> listByYear(Integer year);

    /**
     * 当前年的假期数量, 是否已经同步过了
     * @param year 当前年
     * @return 当前年的假期数量
     */
    Integer countByYear(Integer year);
}
