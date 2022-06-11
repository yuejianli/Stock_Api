package top.yueshushu.learn.extension.assembler;

import org.mapstruct.Mapper;
import top.yueshushu.learn.extension.domain.ExtJobInfoDo;
import top.yueshushu.learn.extension.entity.ExtJobInfo;
import top.yueshushu.learn.extension.model.vo.ExtJobInfoVo;

/**
 * @Description 任务转换器
 * @Author yuejianli
 * @Date 2022/5/20 23:01
 **/
@Mapper(componentModel = "spring")
public interface ExtJobInfoAssembler {
    /**
     * 任务 domain 转换成实体entity
     *
     * @param jobInfoDo 任务Do
     * @return 任务 domain 转换成实体entity
     */
    ExtJobInfo doToEntity(ExtJobInfoDo jobInfoDo);

    /**
     * 任务 entity 转换成 domain
     *
     * @param jobInfo 任务
     * @return 任务 entity 转换成 domain
     */
    ExtJobInfoDo entityToDo(ExtJobInfo jobInfo);

    /**
     * 任务 entity 转换成 vo
     *
     * @param jobInfo 任务
     * @return 任务 entity 转换成 vo
     */
    ExtJobInfoVo entityToVo(ExtJobInfo jobInfo);
}
