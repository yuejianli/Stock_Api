package top.yueshushu.learn.extension.business;

import top.yueshushu.learn.extension.model.ro.ExtInterfaceRo;
import top.yueshushu.learn.response.OutputResult;

/**
 * @Description 提供的接口
 * @Author yuejianli
 * @Date 2022/6/11 15:10
 **/
public interface ExtInterfaceBusiness {
    /**
     * 查询提供的接口功能
     *
     * @param extInterfaceRo 接口查询对象
     * @return 查询提供的接口功能
     */
    OutputResult list(ExtInterfaceRo extInterfaceRo);

    /**
     * 查询今日的诗
     *
     * @return 返回今日的诗
     */
    OutputResult poem();
}
