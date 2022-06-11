package top.yueshushu.learn.extension.service;

import top.yueshushu.learn.enumtype.DataFlagType;
import top.yueshushu.learn.enumtype.ExtJobInfoType;
import top.yueshushu.learn.extension.entity.ExtJobInfo;
import top.yueshushu.learn.extension.model.ro.ExtJobInfoRo;
import top.yueshushu.learn.response.OutputResult;

/**
 * 定时任务的服务
 *
 * @author Yue Jianli
 * @date 2022-06-02
 */

public interface ExtJobInfoService {
    /**
     * 分页查询任务信息
     *
     * @param extJobInfoRo
     * @return top.yueshushu.learn.response.OutputResult
     * @date 2022/6/2 11:52
     * @author yuejianli
     */
    OutputResult pageJob(ExtJobInfoRo extJobInfoRo);

    /**
     * 改变任务的状态
     *
     * @param id
     * @param dataFlagType
     * @return top.yueshushu.learn.response.OutputResult
     * @date 2022/6/2 13:54
     * @author yuejianli
     */
    OutputResult changeStatus(Integer id, DataFlagType dataFlagType);

    /**
     * 根据编码，获取相应的任务信息
     *
     * @param extJobInfoType 扩展任务类型
     */
    ExtJobInfo getByCode(ExtJobInfoType extJobInfoType);

    /**
     * 更新定时任务
     *
     * @param jobInfo 定时任务对象
     */
    void updateInfoById(ExtJobInfo jobInfo);

    /**
     * 删除定时任务
     *
     * @param id 定时任务编号
     */
    OutputResult deleteById(Integer id);

    /**
     * 根据任务编号，获取对应的任务信息
     *
     * @param id 傻编号
     */
    ExtJobInfo getById(Integer id);
}
