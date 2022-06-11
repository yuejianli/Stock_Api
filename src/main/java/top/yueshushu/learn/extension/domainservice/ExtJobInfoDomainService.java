package top.yueshushu.learn.extension.domainservice;

import com.baomidou.mybatisplus.extension.service.IService;
import top.yueshushu.learn.extension.domain.ExtJobInfoDo;

/**
 * @Description 定时任务的操作
 * @Author yuejianli
 * @Date 2022/06/02 23:23
 **/
public interface ExtJobInfoDomainService extends IService<ExtJobInfoDo> {
    /**
     * 根据任务的编码，获取相应的任务信息
     *
     * @param code 任务编码
     */
    ExtJobInfoDo getByCode(String code);
}
