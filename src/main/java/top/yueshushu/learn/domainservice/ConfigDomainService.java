package top.yueshushu.learn.domainservice;

import com.baomidou.mybatisplus.extension.service.IService;
import top.yueshushu.learn.domain.ConfigDo;

import java.util.List;

/**
 * @Description config 全局性配置
 * @Author yuejianli
 * @Date 2022/5/20 23:23
 **/
public interface ConfigDomainService extends IService<ConfigDo> {
    /**
     * 查询某个员工的某个配置信息
     * @param userId 用户编号
     * @param code 配置类型 可以为空
     * @return  查询某个员工的某个配置信息
     */
    List<ConfigDo> findByUserIdAndCode(Integer userId, String code);

    /**
     * 查询某个员工的某个配置信息
     *
     * @param userId 用户编号
     * @param code   配置类型,不能为空
     * @return 查询某个员工的某个配置信息
     */
    ConfigDo getByUserIdAndCode(Integer userId, String code);

    /**
     * 查询启用的记录信息
     *
     * @param code 类型
     */
    List<ConfigDo> listEnableByCode(String code);
}
