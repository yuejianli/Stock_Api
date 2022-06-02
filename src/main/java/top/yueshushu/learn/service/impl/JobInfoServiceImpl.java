package top.yueshushu.learn.service.impl;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.yueshushu.learn.assembler.JobInfoAssembler;
import top.yueshushu.learn.common.ResultCode;
import top.yueshushu.learn.domain.JobInfoDo;
import top.yueshushu.learn.domainservice.JobInfoDomainService;
import top.yueshushu.learn.entity.JobInfo;
import top.yueshushu.learn.enumtype.DataFlagType;
import top.yueshushu.learn.enumtype.JobInfoType;
import top.yueshushu.learn.mode.ro.JobInfoRo;
import top.yueshushu.learn.mode.vo.JobInfoVo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.response.PageResponse;
import top.yueshushu.learn.service.JobInfoService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 定时任务的service信息
 *
 * @author yuejianli
 * @date 2022-06-02
 */
@Service
@Slf4j
public class JobInfoServiceImpl implements JobInfoService {
    @Resource
    private JobInfoDomainService jobInfoDomainService;
    @Resource
    private JobInfoAssembler jobInfoAssembler;

    @Override
    public OutputResult pageJob(JobInfoRo jobInfoRo) {
        PageHelper.startPage(jobInfoRo.getPageNum(), jobInfoRo.getPageSize());
        List<JobInfoDo> jobInfoList = jobInfoDomainService.list();
        if (CollectionUtils.isEmpty(jobInfoList)) {
            return OutputResult.buildSucc(
                    PageResponse.emptyPageResponse()
            );
        }
        List<JobInfoVo> pageResultList = new ArrayList<>(jobInfoList.size());
        jobInfoList.forEach(
                n -> {
                    pageResultList.add(jobInfoAssembler.entityToVo(jobInfoAssembler.doToEntity(n)));
                }
        );
        PageInfo pageInfo = new PageInfo<>(pageResultList);
        return OutputResult.buildSucc(new PageResponse<JobInfoVo>(pageInfo.getTotal(), pageInfo.getList()));

    }

    @Override
    public OutputResult changeStatus(Integer id, DataFlagType dataFlagType) {
        JobInfoDo jobInfoDo = jobInfoDomainService.getById(id);
        if (jobInfoDo == null) {
            return OutputResult.buildAlert(ResultCode.JOB_ID_NOT_EXIST);
        }
        jobInfoDo.setUpdateTime(DateUtil.date().toLocalDateTime());
        jobInfoDo.setTriggerStatus(dataFlagType.getCode());
        //进行更新
        jobInfoDomainService.updateById(jobInfoDo);
        return OutputResult.buildSucc();
    }

    @Override
    public JobInfo getByCode(JobInfoType jobInfoType) {
        if (jobInfoType == null) {
            return null;
        }
        JobInfoDo jobInfoDo = jobInfoDomainService.getByCode(jobInfoType.getCode());
        return jobInfoAssembler.doToEntity(jobInfoDo);
    }

    @Override
    public void updateInfoById(JobInfo jobInfo) {
        jobInfoDomainService.updateById(jobInfoAssembler.entityToDo(jobInfo));
    }

    @Override
    public OutputResult deleteById(Integer id) {
        jobInfoDomainService.removeById(id);
        return OutputResult.buildSucc();
    }

    @Override
    public JobInfo getById(Integer id) {
        JobInfoDo jobInfoDo = jobInfoDomainService.getById(id);
        return jobInfoAssembler.doToEntity(jobInfoDo);
    }
}
