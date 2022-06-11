package top.yueshushu.learn.extension.business;

import top.yueshushu.learn.extension.model.ro.ExtCustomerJobRo;
import top.yueshushu.learn.response.OutputResult;

/**
 * @Description 扩展用户配置任务
 * @Author yuejianli
 * @Date 2022/6/11 15:09
 **/
public interface ExtCustomerJobBusiness {
    /**
     * 扩展用户查询在某个定时任务上配置的功能列表信息
     *
     * @param extCustomerJobRo 扩展用户配置任务对象
     * @return 扩展用户查询在某个定时任务上配置的功能列表信息
     */
    OutputResult list(ExtCustomerJobRo extCustomerJobRo);

    /**
     * 配置某个用户在定时任务上配置哪些功能列表
     *
     * @param extCustomerJobRo 配置任务对象
     * @return 配置某个用户在定时任务上配置哪些功能列表
     */
    OutputResult config(ExtCustomerJobRo extCustomerJobRo);
}
