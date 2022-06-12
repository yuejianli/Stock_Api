package top.yueshushu.learn.extension.assembler;

import org.mapstruct.Mapper;
import top.yueshushu.learn.extension.domain.ExtFastingDo;
import top.yueshushu.learn.extension.entity.ExtFasting;
import top.yueshushu.learn.extension.model.vo.ExtFastingVo;


/**
 * @Description 斋戒日期
 * @Author yuejianli
 * @Date 2022/5/20 23:01
 **/
@Mapper(componentModel = "spring")
public interface ExtFastingAssembler {
    /**
     * 斋戒日期 domain 转换成实体entity
     *
     * @param extFastingDo 斋戒日期Do
     * @return 斋戒日期 domain 转换成实体entity
     */
    ExtFasting doToEntity(ExtFastingDo extFastingDo);

    /**
     * 斋戒日期 entity 转换成 domain
     *
     * @param extFasting 斋戒日期
     * @return 斋戒日期 entity 转换成 domain
     */
    ExtFastingDo entityToDo(ExtFasting extFasting);

    /**
     * 斋戒日期 entity 转换成 vo
     *
     * @param extFasting 斋戒日期
     * @return 斋戒日期 entity 转换成 vo
     */
    ExtFastingVo entityToVo(ExtFasting extFasting);
}
