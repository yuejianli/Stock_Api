package top.yueshushu.learn.common;

/**
 * @ClassName:XxlJobConst
 * @Description 定时任务的常量
 * @Author 岳建立
 * @Date 2022/1/9 14:20
 * @Version 1.0
 **/
public class XxlJobConst {
    //每隔5s 获取一下股票的价格
    public static final String SELECTED_SCAN_CRON = "0/10 * 9,10,11,12,13,14,15 * * ?";
    // 系统组
    public static final Integer JOB_SYSTEM_GROUP = 2;
    //自选组
    public static final Integer JOB_SELECTED_GROUP = 3;

    //定时任务
    public static final String SELECTED_SCAN_HANDLER ="stockPriceHandler";

}
