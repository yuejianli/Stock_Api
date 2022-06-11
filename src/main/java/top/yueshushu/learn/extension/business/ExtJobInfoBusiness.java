package top.yueshushu.learn.extension.business;

import top.yueshushu.learn.enumtype.DataFlagType;
import top.yueshushu.learn.enumtype.ExtJobInfoType;
import top.yueshushu.learn.extension.model.ro.ExtJobInfoRo;
import top.yueshushu.learn.response.OutputResult;

/**
 * 定时任务编排层
 *
 * @author Yue Jianli
 * @date 2022-06-02
 */

public interface ExtJobInfoBusiness {
    /**
     * 分页查询任务信息
     *
     * @param extJobInfoRo 任务RO对象
     * @return top.yueshushu.learn.response.OutputResult
     * @date 2022/6/2 11:52
     * @author yuejianli
     */
    OutputResult listJob(ExtJobInfoRo extJobInfoRo);

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
    OutputResult execJob(ExtJobInfoType jobInfoType, Integer triggerType);

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
