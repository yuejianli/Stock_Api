package top.yueshushu.learn.domainservice;

import com.baomidou.mybatisplus.extension.service.IService;
import top.yueshushu.learn.domain.RebootDo;

/**
 * 机器人使用
 *
 * @author Yue Jianli
 * @date 2023-01-11
 */

public interface RebootDomainService extends IService<RebootDo> {

    RebootDo getByCode(String code);
}
