package top.yueshushu.learn.extension.service;

import top.yueshushu.learn.extension.model.ro.ExtInterfaceRo;
import top.yueshushu.learn.response.OutputResult;

/**
 * 扩展功能使用的service
 *
 * @author Yue Jianli
 * @date 2022-06-10
 */

public interface ExtInterfaceService {
    /**
     * 查询提供的接口功能
     *
     * @param extInterfaceRo 接口查询对象
     * @return 查询提供的接口功能
     */
    OutputResult pageList(ExtInterfaceRo extInterfaceRo);

}
