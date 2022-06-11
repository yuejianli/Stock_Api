package top.yueshushu.learn.extension.assembler;

import org.mapstruct.Mapper;
import top.yueshushu.learn.extension.domain.ExtCustomerDo;
import top.yueshushu.learn.extension.entity.ExtCustomer;
import top.yueshushu.learn.extension.model.ro.ExtCustomerRo;
import top.yueshushu.learn.extension.model.vo.ExtCustomerVo;


/**
 * @Description 扩展，客户扩展客户
 * @Author yuejianli
 * @Date 2022/5/20 23:01
 **/
@Mapper(componentModel = "spring")
public interface ExtCustomerAssembler {
	/**
	 * 扩展客户 domain 转换成实体entity
	 *
	 * @param extCustomerDo 扩展客户Do
	 * @return 扩展客户 domain 转换成实体entity
     */
    ExtCustomer doToEntity(ExtCustomerDo extCustomerDo);

    /**
     * 扩展客户 entity 转换成 domain
     *
     * @param extCustomer 扩展客户
     * @return 扩展客户 entity 转换成 domain
     */
    ExtCustomerDo entityToDo(ExtCustomer extCustomer);

    /**
     * 扩展客户 entity 转换成 vo
     *
     * @param extCustomer 扩展客户
     * @return 扩展客户 entity 转换成 vo
     */
    ExtCustomerVo entityToVo(ExtCustomer extCustomer);

    /**
     * 扩展客户 ro 转换成 entity
     *
     * @param extCustomerRo 扩展客户ro
     * @return 扩展客户 ro 转换成 entity
     */
    ExtCustomer roToEntity(ExtCustomerRo extCustomerRo);
}
