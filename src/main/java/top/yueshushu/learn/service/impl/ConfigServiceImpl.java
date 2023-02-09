package top.yueshushu.learn.service.impl;

import cn.hutool.core.date.DateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import top.yueshushu.learn.assembler.ConfigAssembler;
import top.yueshushu.learn.common.Const;
import top.yueshushu.learn.common.ResultCode;
import top.yueshushu.learn.domain.ConfigDo;
import top.yueshushu.learn.domainservice.ConfigDomainService;
import top.yueshushu.learn.enumtype.ConfigCodeType;
import top.yueshushu.learn.mode.ro.ConfigRo;
import top.yueshushu.learn.mode.vo.ConfigVo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.response.PageResponse;
import top.yueshushu.learn.service.ConfigService;
import top.yueshushu.learn.util.PageUtil;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 * 全局性系统配置 自定义的
 * </p>
 *
 * @author 岳建立
 * @since 2022-01-02
 */
@Service
public class ConfigServiceImpl implements ConfigService {
    @Resource
    private ConfigDomainService configDomainService;
    @Resource
    private ConfigAssembler configAssembler;

    @Override
    public OutputResult listConfig(ConfigRo configRo) {
        //先查询系统全部的配置。 数据量不多 ，可以采用前端分页的方式.
        List<ConfigDo> configDoDefaultList = configDomainService.findByUserIdAndCode(
                Const.DEFAULT_NO, null
        );
        //查询当前用户的信息
        List<ConfigDo> configDoUserList = configDomainService.findByUserIdAndCode(
                configRo.getUserId(), null
        );
        if (!CollectionUtils.isEmpty(configDoUserList)) {
            //将默认的，转换成对应的map 形式
            Map<String, ConfigDo> configMap = configDoUserList.stream().collect(
                    Collectors.toMap(
                            ConfigDo::getCode,
                            n -> n
                    )
            );
            //进行进行
            configDoDefaultList.forEach(
                    n -> {
                        if (configMap.containsKey(n.getCode())) {
                            //进行复制,id 不复制
                            BeanUtils.copyProperties(
                                    configMap.get(n.getCode()), n, "id"
                            );
                        }
                    }
            );
        }

        List<ConfigVo> configVoList = configDoDefaultList.stream()
                .map(
                        n-> configAssembler.entityToVo(
                                configAssembler.doToEntity(
                                        n
                                )
                        )
                ).collect(Collectors.toList());

        List<ConfigDo> list = PageUtil.startPage(configVoList, configRo.getPageNum(),
                configRo.getPageSize());
        return OutputResult.buildSucc(new PageResponse<>((long) configVoList.size(),
                list));
    }

    @Override
    public ConfigVo getConfig(Integer userId, ConfigCodeType configCodeType) {
        if (null == configCodeType) {
            return null;
        }
        return getConfigByCode(userId, configCodeType.getCode());
    }

    @Override
    public ConfigVo getConfigByCode(Integer userId, String code) {
        if (!StringUtils.hasText(code)) {
            return null;
        }
        ConfigDo defaultConfigDo = configDomainService.getByUserIdAndCode(
                Const.DEFAULT_NO, code
        );
        //查询当前用户的配置
        ConfigDo userConfigDo = configDomainService.getByUserIdAndCode(
                userId, code
        );
        if (userConfigDo != null) {
            BeanUtils.copyProperties(userConfigDo, defaultConfigDo);
        }
        return configAssembler.entityToVo(
                configAssembler.doToEntity(
                        defaultConfigDo
                )
        );
    }

    @Override
    public OutputResult update(ConfigRo configRo) {
        //根据id 去查询对应的记录信息
        ConfigDo configDo = configDomainService.getById(configRo.getId());
        if (null == configDo) {
            return OutputResult.buildAlert(ResultCode.CONFIG_ID_NO_EXIST);
        }
        // 如果是自选股票列表配置的话，最多只能有50个.
        if (ConfigCodeType.SELECT_MAX_NUM.getCode().equals(configDo.getCode())) {
            //获取值.
            try {
                Integer maxNum = Integer.parseInt(configRo.getCodeValue());
                if (maxNum > 10) {
                    return OutputResult.buildAlert(ResultCode.CONFIG_SELECTED_MAX);
                }
            } catch (Exception e) {
                return OutputResult.buildAlert(ResultCode.CONFIG_SELECTED_MAX);
            }
        }
        //获取对应的code信息
        ConfigDo userConfigDo = configDomainService.getByUserIdAndCode(
                configRo.getUserId(), configDo.getCode()
        );
        if (null == userConfigDo) {
            //不是用户配置，则重新添加一份.
            ConfigDo addConfigDo = new ConfigDo();
            BeanUtils.copyProperties(configDo, addConfigDo);
            addConfigDo.setId(null);
            addConfigDo.setCodeValue(configDo.getCodeValue());
            addConfigDo.setName(configDo.getName());
            addConfigDo.setCreateTime(DateUtil.date());
            addConfigDo.setUserId(configRo.getUserId());
            configDomainService.save(addConfigDo);
            return OutputResult.buildSucc();
        }else{
            userConfigDo.setCodeValue(configRo.getCodeValue());
            userConfigDo.setName(configRo.getName());
            userConfigDo.setCreateTime(DateUtil.date());
            configDomainService.updateById(userConfigDo);
            return OutputResult.buildSucc();
        }
    }

    @Override
    public OutputResult reset(ConfigRo configRo) {
        //根据id 去查询对应的记录信息
        ConfigDo configDo = configDomainService.getById(configRo.getId());
        if (null == configDo) {
            return OutputResult.buildAlert(ResultCode.CONFIG_ID_NO_EXIST);
        }
        //获取对应的code信息
        ConfigDo userConfigDo = configDomainService.getByUserIdAndCode(
                configRo.getUserId(), configDo.getCode()
        );
        if (null == userConfigDo){
            return OutputResult.buildAlert(ResultCode.CONFIG_IS_DEFAULT);
        }
        configDomainService.removeById(userConfigDo.getId());
        //删除
        return OutputResult.buildSucc();
    }

    @Override
    public int getMaxSelectedNumByUserId(Integer userId) {
        //获取配置信息
        ConfigVo configVo = getConfig(userId, ConfigCodeType.SELECT_MAX_NUM);
        //获取信息
        return Integer.parseInt(
                Optional.ofNullable(configVo.getCodeValue())
                        .orElse("20")
        );
    }

    @Override
    public List<Integer> listEnableUserId(ConfigCodeType configCodeType) {
        if (null == configCodeType) {
            return Collections.emptyList();
        }
        List<ConfigDo> configDoList = configDomainService.listEnableByCode(configCodeType.getCode());
        return configDoList.stream().map(ConfigDo::getUserId).collect(Collectors.toList());
    }
}
