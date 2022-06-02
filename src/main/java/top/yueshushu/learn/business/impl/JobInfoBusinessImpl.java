package top.yueshushu.learn.business.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.yueshushu.learn.business.JobInfoBusiness;
import top.yueshushu.learn.enumtype.DataFlagType;
import top.yueshushu.learn.mode.ro.JobInfoRo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.service.JobInfoService;

import javax.annotation.Resource;

/**
 * 用途描述
 *
 * @author yuejianli
 * @date 2022-06-02
 */
@Service
@Slf4j
public class JobInfoBusinessImpl implements JobInfoBusiness {
    @Resource
    private JobInfoService jobInfoService;

    @Override
    public OutputResult listJob(JobInfoRo jobInfoRo) {
        return jobInfoService.pageJob(jobInfoRo);
    }

    @Override
    public OutputResult changeStatus(Integer id, DataFlagType dataFlagType) {
        return jobInfoService.changeStatus(id, dataFlagType);
    }
}
