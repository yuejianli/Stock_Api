package top.yueshushu.learn.helper;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import top.yueshushu.learn.common.Const;
import top.yueshushu.learn.service.cache.HolidayCalendarCacheService;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Description 日期帮助类
 * @Author yuejianli
 * @Date 2022/5/21 22:53
 **/
@Component
public class DateHelper {
    @Resource
    private HolidayCalendarCacheService holidayCalendarCacheService;

    /**
     * 获取最近的一个工作日, 包括当前的日期。
     *
     * @return 返回最近一个工作日
     */
    public DateTime getBeforeLastWorking(Date currDate) {
        //1. 查询出当前年的全部的假期数据
        if (currDate == null) {
            currDate = DateUtil.date();
        }
        List<String> holidayDateList = holidayCalendarCacheService.listHolidayDateByYear(
                DateUtil.year(currDate)
        );
        for (int i = 0; i >= -30; i--) {
            //获取当前的日期
            DateTime tempDate = DateUtil.offsetDay(
                    currDate, i
            );
            //如果是周末，则跳过
            if (DateUtil.isWeekend(tempDate)) {
                continue;
            }
            // 日期转换
            String formatDate = DateUtil.format(
                    tempDate,
                    Const.SIMPLE_DATE_FORMAT
            );
            if (holidayDateList.contains(formatDate)) {
                continue;
            }
            return tempDate;
        }
        return null;
    }

    /**
     * 当前时间是否是工作日, 是为 true, 否则 为false
     *
     * @param currDate 指定的时间
     * @return 当前时间是否是工作日, 是为 true, 否则 为false
     */
    public boolean isWorkingDay(Date currDate) {
        //1. 查询出当前年的全部的假期数据
        if (currDate == null) {
            currDate = DateUtil.date();
        }
        List<String> holidayDateList = holidayCalendarCacheService.listHolidayDateByYear(
                DateUtil.year(currDate)
        );
        //如果是周末，则跳过
        if (DateUtil.isWeekend(currDate)) {
            return false;
        }
        // 日期转换
        String formatDate = DateUtil.format(
                currDate,
                Const.SIMPLE_DATE_FORMAT
        );
        return !holidayDateList.contains(formatDate);
    }

    /**
     * 验证一下，当前时间是否是股票交易的时间。
     *
     * @param date 日期处理
     * @return 验证一下，当前时间是否是股票交易的时间。
     */
    public boolean isTradeTime(Date date) {
        if (!isWorkingDay(date)) {
            return false;
        }
        //验证一下，是不是在 9点半到11点半， 1点到 3点之间
        return between930To1130(date) || between13To15(date);
    }

    /**
     * 当前日期是否在 9点半到11点之间
     *
     * @param date 当前日期
     * @return 当前日期是否在 9点半到11点之间
     */
    public boolean between930To1130(Date date) {

        //组装一个下午3点的时间
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar.set(Calendar.MINUTE, 29);
        calendar.set(Calendar.SECOND, 45);
        Date date930 = calendar.getTime();

        calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 11);
        calendar.set(Calendar.MINUTE, 30);
        calendar.set(Calendar.SECOND, 15);
        Date date1130 = calendar.getTime();

        return date.before(date1130) && date.after(date930);
    }

    /**
     * 当前日期是否在 下午1点到下午 3点
     *
     * @param date 当前日期
     * @return 当前日期是否在 下午1点到下午 3点
     */
    public boolean between13To15(Date date) {

        //组装一个下午3点的时间
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 45);
        Date date13 = calendar.getTime();

        calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 15);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 15);
        Date date15 = calendar.getTime();

        return date.before(date15) && date.after(date13);
    }

    /**
     * 开始和结束日期之间，总共多少个工作日。
     *
     * @param startDateStr 开始日期
     * @param endDateStr   结束日期
     */
    public List<String> betweenWorkDay(String startDateStr, String endDateStr) {
        if (!(StringUtils.hasText(startDateStr) && StringUtils.hasText(endDateStr))) {
            return Collections.emptyList();
        }
        Date startDate = DateUtil.parse(startDateStr);
        Date endDate = DateUtil.endOfDay(DateUtil.parse(endDateStr, DatePattern.NORM_DATE_PATTERN));
        List<Date> dateList = betweenWorkDay(startDate, endDate);
        if (CollectionUtils.isEmpty(dateList)) {
            return Collections.emptyList();
        }
        List<String> result = new ArrayList<>();
        dateList.forEach(
                n -> {
                    result.add(DateUtil.format(n, Const.SIMPLE_DATE_FORMAT));
                }
        );
        return result;
    }

    /**
     * 开始和结束日期之间，总共多少个工作日。
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     */
    public List<Date> betweenWorkDay(Date startDate, Date endDate) {
        if (!(startDate != null && endDate != null)) {
            return Collections.emptyList();
        }
        List<Date> result = new ArrayList<>();
        while (endDate.after(startDate)) {
            // startDate 进行加1
            if (isWorkingDay(startDate)) {
                result.add(DateUtil.beginOfDay(startDate));
            }
            startDate = DateUtil.offsetDay(startDate, 1);
        }
        return result;
    }
}
