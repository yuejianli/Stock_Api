package top.yueshushu.learn.extension.service;

import top.yueshushu.learn.extension.entity.ExtCustomer;
import top.yueshushu.learn.extension.model.ro.ExtCustomerRo;
import top.yueshushu.learn.response.OutputResult;

import java.util.List;

/**
 * 扩展功能使用的 客户 service
 *
 * @author Yue Jianli
 * @date 2022-06-10
 */

public interface ExtCustomerService {
    /**
     * 查询分页参数信息
     *
     * @param extCustomerRo
     */
    OutputResult pageList(ExtCustomerRo extCustomerRo);

    /**
     * 添加客户列表信息
     *
     * @param extCustomerRo 客户对象
     * @return 返回添加客户
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
    OutputResult deleteById(Integer id);

    /**
     * 查询所有正常使用的用户列表
     *
     * @return 查询所有正常使用的用户列表
     */
    List<ExtCustomer> findAll();
}
