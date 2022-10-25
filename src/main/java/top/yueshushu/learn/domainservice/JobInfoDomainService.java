package top.yueshushu.learn.domainservice;

import com.baomidou.mybatisplus.extension.service.IService;
import top.yueshushu.learn.domain.JobInfoDo;

/**
 * @Description 定时任务的操作
 * @Author yuejianli
 * @Date 2022/06/02 23:23
 **/
public interface JobInfoDomainService extends IService<JobInfoDo> {
    /**
     * 根据任务的编码，获取相应的任务信息
     *
     * @param code 任务编码
     */
    JobInfoDo getByCode(String code);

    /**
     * 判断任务是否是启用状态
     *
     * @param jobInfoDo 任务对象
     */
    boolean isValid(JobInfoDo jobInfoDo);
}
