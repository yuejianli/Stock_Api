package top.yueshushu.learn.extension.service.impl;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.yueshushu.learn.common.ResultCode;
import top.yueshushu.learn.enumtype.DataFlagType;
import top.yueshushu.learn.enumtype.ExtJobInfoType;
import top.yueshushu.learn.extension.assembler.ExtJobInfoAssembler;
import top.yueshushu.learn.extension.domain.ExtJobInfoDo;
import top.yueshushu.learn.extension.domainservice.ExtJobInfoDomainService;
import top.yueshushu.learn.extension.entity.ExtJobInfo;
import top.yueshushu.learn.extension.model.ro.ExtJobInfoRo;
import top.yueshushu.learn.extension.model.vo.ExtJobInfoVo;
import top.yueshushu.learn.extension.service.ExtJobInfoService;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.response.PageResponse;

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
public class ExtJobInfoServiceImpl implements ExtJobInfoService {
    @Resource
    private ExtJobInfoDomainService extJobInfoDomainService;
    @Resource
    private ExtJobInfoAssembler extJobInfoAssembler;

    @Override
    public OutputResult pageJob(ExtJobInfoRo extJobInfoRo) {
        Page<Object> pageInfo = PageHelper.startPage(extJobInfoRo.getPageNum(), extJobInfoRo.getPageSize());
        List<ExtJobInfoDo> jobInfoList = extJobInfoDomainService.list();
        if (CollectionUtils.isEmpty(jobInfoList)) {
            return OutputResult.buildSucc(
                    PageResponse.emptyPageResponse()
            );
        }
        List<ExtJobInfoVo> pageResultList = new ArrayList<>(jobInfoList.size());
        jobInfoList.forEach(
                n -> {
                    pageResultList.add(extJobInfoAssembler.entityToVo(extJobInfoAssembler.doToEntity(n)));
                }
        );
        return OutputResult.buildSucc(new PageResponse<>(pageInfo.getTotal(), pageResultList));

    }

    @Override
    public OutputResult changeStatus(Integer id, DataFlagType dataFlagType) {
        ExtJobInfoDo jobInfoDo = extJobInfoDomainService.getById(id);
        if (jobInfoDo == null) {
            return OutputResult.buildAlert(ResultCode.JOB_ID_NOT_EXIST);
        }
        jobInfoDo.setUpdateTime(DateUtil.date().toLocalDateTime());
        jobInfoDo.setTriggerStatus(dataFlagType.getCode());
        //进行更新
        extJobInfoDomainService.updateById(jobInfoDo);
        return OutputResult.buildSucc();
    }

    @Override
    public ExtJobInfo getByCode(ExtJobInfoType extJobInfoType) {
        if (extJobInfoType == null) {
            return null;
        }
        ExtJobInfoDo jobInfoDo = extJobInfoDomainService.getByCode(extJobInfoType.getCode());
        return extJobInfoAssembler.doToEntity(jobInfoDo);
    }

    @Override
    public void updateInfoById(ExtJobInfo jobInfo) {
        extJobInfoDomainService.updateById(extJobInfoAssembler.entityToDo(jobInfo));
    }

    @Override
    public OutputResult deleteById(Integer id) {
        extJobInfoDomainService.removeById(id);
        return OutputResult.buildSucc();
    }

    @Override
    public ExtJobInfo getById(Integer id) {
        ExtJobInfoDo jobInfoDo = extJobInfoDomainService.getById(id);
        return extJobInfoAssembler.doToEntity(jobInfoDo);
    }
}
