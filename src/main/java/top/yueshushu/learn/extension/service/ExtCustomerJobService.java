package top.yueshushu.learn.extension.service;

import top.yueshushu.learn.extension.entity.ExtCustomerJob;
import top.yueshushu.learn.extension.model.ro.ExtCustomerJobRo;
import top.yueshushu.learn.extension.model.vo.ExtCustomerJobVo;
import top.yueshushu.learn.response.OutputResult;

import java.util.List;

/**
 * 扩展功能使用的service
 *
 * @author Yue Jianli
 * @date 2022-06-10
 */

public interface ExtCustomerJobService {
    /**
     * 扩展用户查询在某个定时任务上配置的功能列表信息
     *
     * @param extCustomerJobRo 扩展用户配置任务对象
     * @return 扩展用户查询在某个定时任务上配置的功能列表信息
     */
    OutputResult<ExtCustomerJobVo> list(ExtCustomerJobRo extCustomerJobRo);

    /**
     * 配置某个用户在定时任务上配置哪些功能列表
     *
     * @param extCustomerJobRo 配置任务对象
     * @return 配置某个用户在定时任务上配置哪些功能列表
     */
    OutputResult config(ExtCustomerJobRo extCustomerJobRo);

    /**
     * 根据任务编号，查询相应的信息.
     *
     * @param jobId 任务编号
     * @return 返回该任务对应的关联任务对象信息
     */
    List<ExtCustomerJob> listByJobId(Integer jobId);

    /**
     * 根据用户编号，删除对应的配置功能信息
     *
     * @param customerId 客户编号
     */
    void removeByUserId(Integer customerId);
}
