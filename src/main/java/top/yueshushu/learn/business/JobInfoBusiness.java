package top.yueshushu.learn.business;

import top.yueshushu.learn.enumtype.DataFlagType;
import top.yueshushu.learn.enumtype.JobInfoType;
import top.yueshushu.learn.mode.ro.JobInfoRo;
import top.yueshushu.learn.response.OutputResult;

/**
 * 定时任务编排层
 *
 * @author Yue Jianli
 * @date 2022-06-02
 */

public interface JobInfoBusiness {
    /**
     * 分页查询任务信息
     *
     * @param jobInfoRo 任务RO对象
     * @return top.yueshushu.learn.response.OutputResult
     * @date 2022/6/2 11:52
     * @author yuejianli
     */
    OutputResult listJob(JobInfoRo jobInfoRo);

    /**
     * 改变任务的状态，禁用还是启用
     *
     * @param id           任务编号
     * @param dataFlagType 任务状态
     * @return top.yueshushu.learn.response.OutputResult
     * @date 2022/6/2 13:53
     * @author yuejianli
     */
    OutputResult changeStatus(Integer id, DataFlagType dataFlagType);

    /**
     * 执行任务
     *
     * @param jobInfoType 任务类型
     * @param triggerType 执行类型
     */
    OutputResult execJob(JobInfoType jobInfoType, Integer triggerType);

    /**
     * 删除定时任务
     *
     * @param id 任务编号id
     */
    OutputResult deleteById(Integer id);

    /**
     * 手动执行定时任务
     *
     * @param id 任务编号id
     */
    OutputResult handlerById(Integer id);
}
