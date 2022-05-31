package top.yueshushu.learn.util;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @ClassName:MyDateUtil
 * @Description TODO
 * @Author 岳建立
 * @Date 2021/11/12 22:14
 * @Version 1.0
 **/
public class MyDateUtil {
    static List<String> holiday;
    static List<String> extraWorkDay;

    /**
     * 当前时间是否在下午3点之后
     * @return
     */
    public static boolean after15Hour(){
        Date now= DateUtil.date();
        //组装一个下午3点的时间
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,15);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        Date hour15 = calendar.getTime();
        if(now.before(hour15)){
            return false;
        }
        return true;
    }
    /**
     * 当前时间是否在下午3点之后
     * @return
     */
    public static boolean before930(){
        Date now= DateUtil.date();
        //组装一个下午3点的时间
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,9);
        calendar.set(Calendar.MINUTE,30);
        calendar.set(Calendar.SECOND,0);
        Date hour930 = calendar.getTime();
        if(now.before(hour930)){
            return true;
        }
        return false;
    }

    /**
     * 当前时间是否 9点半到 15点之间
     * @return
     */
    public static boolean between930And15(){
       return !before930()&&!after15Hour();
    }

    public static void main(String[] args) {
       // System.out.println(after15Hour());
        System.out.println(before930());
    }
    /**
     * 是否是节假日
     */
    public static Boolean isWorkingDay(DateTime dateTime) {
        String formatTime = DateUtil.format(dateTime,"yyyy-MM-dd");
        //是否加班日
        //if(extraWorkDay.contains(formatTime)){
        //    return true;
        //}
        //是否节假日
        if(holiday.contains(formatTime)){
            return false;
        }
        //如果是1-5表示周一到周五  是工作日
        if(DateUtil.isWeekend(dateTime)){
            return false;
        }
        return true;

    }
    /**
     *  初始化节假日
     */
     static{
        holiday =new ArrayList<>();
        holiday.add("2021-01-01");
        holiday.add("2021-01-02");
        holiday.add("2021-01-03");
        holiday.add("2021-02-11");
        holiday.add("2021-02-12");
        holiday.add("2021-02-13");
        holiday.add("2021-02-14");
        holiday.add("2021-02-15");
        holiday.add("2021-02-16");
        holiday.add("2021-02-17");
        holiday.add("2021-04-03");
        holiday.add("2021-04-04");
        holiday.add("2021-04-05");
        holiday.add("2021-05-01");
        holiday.add("2021-05-02");
        holiday.add("2021-05-03");
        holiday.add("2021-05-04");
        holiday.add("2021-05-05");
        holiday.add("2021-06-12");
        holiday.add("2021-06-13");
        holiday.add("2021-06-14");
        holiday.add("2021-09-19");
        holiday.add("2021-09-20");
        holiday.add("2021-09-21");
        holiday.add("2021-10-01");
        holiday.add("2021-10-02");
        holiday.add("2021-10-03");
        holiday.add("2021-10-04");
        holiday.add("2021-10-05");
        holiday.add("2021-10-06");
        holiday.add("2021-10-07");
    }
    /**
     *  初始化额外加班日
     */
    static{
        extraWorkDay =new ArrayList<>();
        extraWorkDay.add("2021-02-07");
        extraWorkDay.add("2021-02-20");
        extraWorkDay.add("2021-04-25");
        extraWorkDay.add("2021-05-08");
        extraWorkDay.add("2021-09-18");
        extraWorkDay.add("2021-09-26");
        extraWorkDay.add("2021-10-09");
    }
}
