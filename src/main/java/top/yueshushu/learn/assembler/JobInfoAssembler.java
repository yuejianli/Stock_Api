package top.yueshushu.learn.assembler;

import org.mapstruct.Mapper;
import top.yueshushu.learn.domain.JobInfoDo;
import top.yueshushu.learn.entity.JobInfo;
import top.yueshushu.learn.mode.vo.JobInfoVo;

/**
 * @Description 任务转换器
 * @Author yuejianli
 * @Date 2022/5/20 23:01
 **/
@Mapper(componentModel = "spring")
public interface JobInfoAssembler {
    /**
     * 任务 domain 转换成实体entity
     *
     * @param jobInfoDo 任务Do
     * @return 任务 domain 转换成实体entity
     */
    JobInfo doToEntity(JobInfoDo jobInfoDo);

    /**
     * 任务 entity 转换成 domain
     *
     * @param jobInfo 任务
     * @return 任务 entity 转换成 domain
     */
    JobInfoDo entityToDo(JobInfo jobInfo);

    /**
     * 任务 entity 转换成 vo
     *
     * @param jobInfo 任务
     * @return 任务 entity 转换成 vo
     */
    JobInfoVo entityToVo(JobInfo jobInfo);
}
