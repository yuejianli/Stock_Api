package top.yueshushu.learn.extension.assembler;

import org.mapstruct.Mapper;
import top.yueshushu.learn.extension.domain.ExtInterfaceDo;
import top.yueshushu.learn.extension.entity.ExtInterface;
import top.yueshushu.learn.extension.model.vo.ExtInterfaceVo;

/**
 * @Description 任务转换器
 * @Author yuejianli
 * @Date 2022/5/20 23:01
 **/
@Mapper(componentModel = "spring")
public interface ExtInterfaceAssembler {
    /**
     * 任务 domain 转换成实体entity
     *
     * @param extInterfaceDo 任务Do
     * @return 任务 domain 转换成实体entity
     */
    ExtInterface doToEntity(ExtInterfaceDo extInterfaceDo);

    /**
     * 任务 entity 转换成 domain
     *
     * @param extInterface 任务
     * @return 任务 entity 转换成 domain
     */
    ExtInterfaceDo entityToDo(ExtInterface extInterface);

    /**
     * 任务 entity 转换成 vo
     *
     * @param extInterface 任务
     * @return 任务 entity 转换成 vo
     */
    ExtInterfaceVo entityToVo(ExtInterface extInterface);
}
