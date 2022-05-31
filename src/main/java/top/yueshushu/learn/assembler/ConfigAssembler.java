package top.yueshushu.learn.assembler;

import org.mapstruct.Mapper;
import top.yueshushu.learn.domain.ConfigDo;
import top.yueshushu.learn.entity.Config;
import top.yueshushu.learn.mode.vo.ConfigVo;

/**
 * @Description 配置转换器
 * @Author yuejianli
 * @Date 2022/5/20 23:01
 **/
@Mapper(componentModel = "spring" )
public interface ConfigAssembler {
    /**
     * 配置 domain 转换成实体entity
     * @param ConfigDo 配置Do
     * @return 配置 domain 转换成实体entity
     */
    Config doToEntity(ConfigDo ConfigDo);

    /**
     * 配置 entity 转换成 domain
     * @param Config 配置
     * @return 配置 entity 转换成 domain
     */
    ConfigDo entityToDo(Config Config);

    /**
     * 配置 entity 转换成 vo
     * @param Config 配置
     * @return 配置 entity 转换成 vo
     */
    ConfigVo entityToVo(Config Config);
}
