package top.yueshushu.learn.extension.service.impl;

import org.springframework.stereotype.Service;
import top.yueshushu.learn.extension.assembler.ExtFastingAssembler;
import top.yueshushu.learn.extension.domainservice.ExtFastingDomainService;
import top.yueshushu.learn.extension.entity.ExtFasting;
import top.yueshushu.learn.extension.service.ExtFastingService;

import javax.annotation.Resource;

/**
 * @Description 接口应用实现
 * @Author yuejianli
 * @Date 2022/6/11 15:07
 **/
@Service
public class ExtFastingServiceImpl implements ExtFastingService {
    @Resource
    private ExtFastingDomainService extFastingDomainService;
    @Resource
    private ExtFastingAssembler extFastingAssembler;


    @Override
    public ExtFasting getById(Integer id) {
        return extFastingAssembler.doToEntity(extFastingDomainService.getById(id));
    }

    @Override
    public ExtFasting getByMonthAndDay(int month, int day, String term) {
        return extFastingAssembler.doToEntity(extFastingDomainService.
                getByMonthAndDay(month, day, term));
    }
}
