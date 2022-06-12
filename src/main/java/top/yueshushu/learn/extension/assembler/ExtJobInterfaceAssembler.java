package top.yueshushu.learn.extension.assembler;

import org.mapstruct.Mapper;
import top.yueshushu.learn.extension.domain.ExtJobInterfaceDo;
import top.yueshushu.learn.extension.entity.ExtJobInterface;


/**
 * @Description 扩展，客户任务关联功能
 * @Author yuejianli
 * @Date 2022/5/20 23:01
 **/
@Mapper(componentModel = "spring")
public interface ExtJobInterfaceAssembler {
    /**
     * 任务关联功能 domain 转换成实体entity
     *
     * @param extJobInterfaceDo 任务关联功能Do
     * @return 任务关联功能 domain 转换成实体entity
     */
    ExtJobInterface doToEntity(ExtJobInterfaceDo extJobInterfaceDo);

    /**
     * 任务关联功能 entity 转换成 domain
     *
     * @param extJobInterface 任务关联功能
     * @return 任务关联功能 entity 转换成 domain
     */
    ExtJobInterfaceDo entityToDo(ExtJobInterface extJobInterface);

}
