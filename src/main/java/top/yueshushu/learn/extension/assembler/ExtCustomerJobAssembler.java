package top.yueshushu.learn.extension.assembler;

import org.mapstruct.Mapper;
import top.yueshushu.learn.extension.domain.ExtCustomerJobDo;
import top.yueshushu.learn.extension.entity.ExtCustomerJob;


/**
 * @Description 扩展，客户扩展客户
 * @Author yuejianli
 * @Date 2022/5/20 23:01
 **/
@Mapper(componentModel = "spring")
public interface ExtCustomerJobAssembler {
    /**
     * 扩展客户 domain 转换成实体entity
     *
     * @param extCustomerJobDo 扩展客户Do
     * @return 扩展客户 domain 转换成实体entity
     */
    ExtCustomerJob doToEntity(ExtCustomerJobDo extCustomerJobDo);

    /**
     * 扩展客户 entity 转换成 domain
     *
     * @param extCustomerJob 扩展客户
     * @return 扩展客户 entity 转换成 domain
     */
    ExtCustomerJobDo entityToDo(ExtCustomerJob extCustomerJob);

}
