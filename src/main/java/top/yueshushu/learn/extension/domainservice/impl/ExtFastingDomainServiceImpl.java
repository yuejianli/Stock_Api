package top.yueshushu.learn.extension.domainservice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.yueshushu.learn.extension.domain.ExtFastingDo;
import top.yueshushu.learn.extension.domainservice.ExtFastingDomainService;
import top.yueshushu.learn.extension.mapper.ExtFastingDoMapper;

import javax.annotation.Resource;

/**
 * @Description 斋戒日期的处理
 * @Author yuejianli
 * @Date 2022/5/20 23:23
 **/
@Service
@Slf4j
public class ExtFastingDomainServiceImpl extends ServiceImpl<ExtFastingDoMapper, ExtFastingDo>
        implements ExtFastingDomainService {
    @Resource
    private ExtFastingDoMapper extFastingDoMapper;


    @Override
    public ExtFastingDo getByMonthAndDay(int month, int day, String term) {
        return extFastingDoMapper.getByMonthAndDayAndTerm(month, day, term);
    }
}
