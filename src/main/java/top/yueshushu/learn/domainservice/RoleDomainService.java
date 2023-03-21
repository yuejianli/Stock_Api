package top.yueshushu.learn.domainservice;

import com.baomidou.mybatisplus.extension.service.IService;
import top.yueshushu.learn.domain.RoleDo;

import java.util.List;

/**
 * 角色列表
 *
 * @author Yue Jianli
 * @date 2023-03-16
 */

public interface RoleDomainService extends IService<RoleDo> {
    /**
     * 根据角色名称模糊查询
     *
     * @param name 角色名称
     */
    List<RoleDo> listByName(String name);
}
