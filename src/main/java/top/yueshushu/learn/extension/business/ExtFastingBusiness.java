package top.yueshushu.learn.extension.business;

/**
 * @Description 提供的接口
 * @Author yuejianli
 * @Date 2022/6/11 15:10
 **/
public interface ExtFastingBusiness {
    /**
     * 执行 斋戒 的定时任务
     *
     * @param interfaceId 定时任务id
     */
    String fastingJob(Integer interfaceId);
}
