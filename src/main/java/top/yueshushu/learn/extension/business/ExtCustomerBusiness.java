package top.yueshushu.learn.extension.business;

import top.yueshushu.learn.extension.model.ro.ExtCustomerRo;
import top.yueshushu.learn.response.OutputResult;

/**
 * @Description 客户列表
 * @Author yuejianli
 * @Date 2022/6/11 15:09
 **/
public interface ExtCustomerBusiness {
    /**
     * 查询客户列表
     *
     * @param extCustomerRo 客户筛选条件
     * @return 返回客户列表信息
     */
    OutputResult list(ExtCustomerRo extCustomerRo);

    /**
     * 添加客户列表信息
     *
     * @param extCustomerRo 客户对象
     * @return 返回客户列表信息
     */
    OutputResult add(ExtCustomerRo extCustomerRo);

    /**
     * 更新客户列表信息
     *
     * @param extCustomerRo 客户对象
     * @return 更新客户列表信息
     */
    OutputResult update(ExtCustomerRo extCustomerRo);

    /**
     * 根据客户id 删除客户信息
     *
     * @param id 客户id
     * @return 根据客户id 删除客户信息
     */
    OutputResult delete(Integer id);
}
