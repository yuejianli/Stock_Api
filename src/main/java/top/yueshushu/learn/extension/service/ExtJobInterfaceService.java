package top.yueshushu.learn.extension.service;

import top.yueshushu.learn.extension.entity.ExtInterface;

import java.util.List;

/**
 * 扩展功能使用的service
 *
 * @author Yue Jianli
 * @date 2022-06-10
 */

public interface ExtJobInterfaceService {
    /**
     * 根据扩展任务id 查询对应的关联功能
     *
     * @param extJobId 扩展任务id
     * @return 根据扩展任务id 查询对应的关联功能
     */
    List<ExtInterface> listAllByJobId(Integer extJobId);
}
