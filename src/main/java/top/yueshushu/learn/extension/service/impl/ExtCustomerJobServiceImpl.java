package top.yueshushu.learn.extension.service.impl;

import cn.hutool.core.date.DateUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.yueshushu.learn.extension.assembler.ExtCustomerJobAssembler;
import top.yueshushu.learn.extension.domain.ExtCustomerJobDo;
import top.yueshushu.learn.extension.domainservice.ExtCustomerJobDomainService;
import top.yueshushu.learn.extension.entity.ExtCustomerJob;
import top.yueshushu.learn.extension.model.ro.ExtCustomerJobRo;
import top.yueshushu.learn.extension.model.vo.ExtCustomerJobVo;
import top.yueshushu.learn.extension.service.ExtCustomerJobService;
import top.yueshushu.learn.response.OutputResult;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description 扩展接口
 * @Author yuejianli
 * @Date 2022/6/11 15:07
 **/
@Service
public class ExtCustomerJobServiceImpl implements ExtCustomerJobService {
    @Resource
    private ExtCustomerJobDomainService extCustomerJobDomainService;
    @Resource
    private ExtCustomerJobAssembler extCustomerJobAssembler;

    @Override
    public OutputResult<ExtCustomerJobVo> list(ExtCustomerJobRo extCustomerJobRo) {
        List<ExtCustomerJobDo> extCustomerDoList =
                extCustomerJobDomainService.listByCustomerIdAndJobId(
                        extCustomerJobRo.getExtCustomerId(), extCustomerJobRo.getExtJobId());

        ExtCustomerJobVo extCustomerJobVo = new ExtCustomerJobVo();
        extCustomerJobVo.setExtCustomerId(extCustomerJobRo.getExtCustomerId());
        extCustomerJobVo.setExtJobId(extCustomerJobRo.getExtJobId());

        if (CollectionUtils.isEmpty(extCustomerDoList)) {
            return OutputResult.buildSucc(extCustomerJobVo);
        }
        // 返回信息列表
        List<Integer> interfaceIdList = extCustomerDoList.stream()
                .map(ExtCustomerJobDo::getExtInterfaceId)
                .collect(Collectors.toList());
        extCustomerJobVo.setCreateTime(extCustomerDoList.get(0).getCreateTime());
        extCustomerJobVo.setInterfaceIdList(interfaceIdList);
        return OutputResult.buildSucc(extCustomerJobVo);

    }

    @Override
    public OutputResult config(ExtCustomerJobRo extCustomerJobRo) {
        // 先删除以前的配置记录信息
        extCustomerJobDomainService.removeByCustomerIdAndJobId(extCustomerJobRo.getExtCustomerId(),
                extCustomerJobRo.getExtJobId());
        if (CollectionUtils.isEmpty(extCustomerJobRo.getInterfaceIdList())) {
            return OutputResult.buildSucc();
        }
        // 删除完成之后，重新配置生成.
        List<ExtCustomerJobDo> extCustomerJobDoList = new ArrayList<>(extCustomerJobRo.getInterfaceIdList().size());
        Date now = DateUtil.date();
        for (Integer interfaceId : extCustomerJobRo.getInterfaceIdList()) {
            ExtCustomerJobDo extCustomerJobDo = new ExtCustomerJobDo();
            extCustomerJobDo.setExtCustomerId(extCustomerJobRo.getExtCustomerId());
            extCustomerJobDo.setExtJobId(extCustomerJobRo.getExtJobId());
            extCustomerJobDo.setExtInterfaceId(interfaceId);
            extCustomerJobDo.setCreateTime(now);
            extCustomerJobDoList.add(extCustomerJobDo);
        }
        extCustomerJobDomainService.saveBatch(extCustomerJobDoList);
        return OutputResult.buildSucc();
    }

    @Override
    public List<ExtCustomerJob> listByJobId(Integer jobId) {
        List<ExtCustomerJobDo> extCustomerDoList =
                extCustomerJobDomainService.listByJobId(jobId);
        return extCustomerDoList.stream().map(
                n -> extCustomerJobAssembler.doToEntity(n)
        ).collect(Collectors.toList());
    }

    @Override
    public void removeByUserId(Integer customerId) {
        extCustomerJobDomainService.removeByUserId(customerId);
    }
}
