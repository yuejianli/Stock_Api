package top.yueshushu.learn.business;

import top.yueshushu.learn.mode.ro.ConfigRo;
import top.yueshushu.learn.mode.ro.UserRo;
import top.yueshushu.learn.response.OutputResult;

/**
 * @Description 配置的编排层处理
 * @Author 岳建立
 * @Date 2022/5/20 22:33
 **/
public interface ConfigBusiness {
    /**
     * 查询config 配置信息
     * @param configRo config 查询对象
     * @return  查询config 配置信息
     */
    OutputResult listConfig(ConfigRo configRo);

    /**
     * 更新 config 配置
     * @param configRo config对象
     * @return 更新 config 配置
     */
    OutputResult update(ConfigRo configRo);

    /**
     *  重置 config 配置
     * @param configRo config对象
     * @return 重置 config 配置
     */
    OutputResult reset(ConfigRo configRo);
}
