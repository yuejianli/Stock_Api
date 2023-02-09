package top.yueshushu.learn.service;

import top.yueshushu.learn.enumtype.ConfigCodeType;
import top.yueshushu.learn.mode.ro.ConfigRo;
import top.yueshushu.learn.mode.vo.ConfigVo;
import top.yueshushu.learn.response.OutputResult;

import java.util.List;

/**
 * <p>
 * 全局性系统配置 自定义的
 * </p>
 *
 * @author 岳建立
 * @since 2022-01-02
 */
public interface ConfigService {
    /**
     * 查询该员工对应的系统配置.
     * 如果没有配置，使用系统默认的配置信息.
     * @param configRo config 配置对象
     * @return 查询该员工对应的系统配置. 如果没有配置，使用系统默认的配置信息.
     */
    OutputResult listConfig(ConfigRo configRo);
    /**
     * 查询该员工的某种配置信息
     * @param userId 员工编号
     * @param configCodeType 配置对象
     * @return 查询该员工的某种配置信息
     */
    ConfigVo getConfig(Integer userId, ConfigCodeType configCodeType);
    /**
     * 查询该员工的某种配置信息
     * @param userId 员工编号
     * @param code 编码
     * @return  查询该员工的某种配置信息
     */
    ConfigVo getConfigByCode(Integer userId, String code);

    /**
     * 修改配置信息
     * @param configRo 修改的配置对象
     * @return 修改配置信息
     */
    OutputResult update(ConfigRo configRo);

    /**
     * 重置配置参数信息
     * @param configRo 重置配置参数对象
     * @return 重置配置参数信息
     */
    OutputResult reset(ConfigRo configRo);

    /**
     * 获取该用户配置的最大自选数据
     *
     * @param userId 用户id
     * @return 返回配置的最大用户数量
     */
    int getMaxSelectedNumByUserId(Integer userId);

    /**
     * 获取启用的用户编号信息
     *
     * @param configCodeType
     */
    List<Integer> listEnableUserId(ConfigCodeType configCodeType);
}
