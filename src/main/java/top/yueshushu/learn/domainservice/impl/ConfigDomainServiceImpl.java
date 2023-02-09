package top.yueshushu.learn.domainservice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.yueshushu.learn.domain.ConfigDo;
import top.yueshushu.learn.domainservice.ConfigDomainService;
import top.yueshushu.learn.enumtype.DataFlagType;
import top.yueshushu.learn.mapper.ConfigDoMapper;

import java.util.List;

/**
 * @Description config 基本配置信息
 * @Author yuejianli
 * @Date 2022/5/20 23:23
 **/
@Service
@Slf4j
public class ConfigDomainServiceImpl extends ServiceImpl<ConfigDoMapper, ConfigDo>
        implements ConfigDomainService {


    @Override
    public List<ConfigDo> findByUserIdAndCode(Integer userId, String code) {
        return this.lambdaQuery()
                .eq( userId !=null, ConfigDo::getUserId,userId)
                .eq(code !=null, ConfigDo::getCode,code)
                .list();
    }

    @Override
    public ConfigDo getByUserIdAndCode(Integer userId, String code) {
        List<ConfigDo> configDoList = findByUserIdAndCode(userId, code);
        if (CollectionUtils.isEmpty(configDoList)) {
            return null;
        }
        return configDoList.get(0);
    }

    @Override
    public List<ConfigDo> listEnableByCode(String code) {
        return this.lambdaQuery()
                .eq(code != null, ConfigDo::getCode, code)
                .eq(ConfigDo::getCodeValue, DataFlagType.NORMAL.getCode() + "")
                .list();
    }


}
