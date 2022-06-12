package top.yueshushu.learn.extension.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.yueshushu.learn.extension.assembler.ExtInterfaceAssembler;
import top.yueshushu.learn.extension.domain.ExtInterfaceDo;
import top.yueshushu.learn.extension.domain.ExtJobInterfaceDo;
import top.yueshushu.learn.extension.domainservice.ExtInterfaceDomainService;
import top.yueshushu.learn.extension.domainservice.ExtJobInterfaceDomainService;
import top.yueshushu.learn.extension.entity.ExtInterface;
import top.yueshushu.learn.extension.service.ExtJobInterfaceService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description 扩展接口
 * @Author yuejianli
 * @Date 2022/6/11 15:07
 **/
@Service
public class ExtJobInterfaceServiceImpl implements ExtJobInterfaceService {
    @Resource
    private ExtJobInterfaceDomainService extJobInterfaceDomainService;
    @Resource
    private ExtInterfaceDomainService extInterfaceDomainService;
    @Resource
    private ExtInterfaceAssembler extInterfaceAssembler;


    @Override
    public List<ExtInterface> listAllByJobId(Integer extJobId) {
        //1. 查询一下，关联的全部任务接口
        List<ExtJobInterfaceDo> extJobInterfaceDoList = extJobInterfaceDomainService.listByJobId(extJobId);
        List<Integer> interfaceIdList = extJobInterfaceDoList.stream().map(ExtJobInterfaceDo::getExtInterfaceId)
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(extJobInterfaceDoList)) {
            return Collections.emptyList();
        }
        List<ExtInterfaceDo> extInterfaceDoList = extInterfaceDomainService.listByIds(interfaceIdList);
        if (CollectionUtils.isEmpty(extInterfaceDoList)) {
            return Collections.emptyList();
        }

        List<ExtInterface> pageResultList = new ArrayList<>(extInterfaceDoList.size());
        extInterfaceDoList.forEach(
                n -> {
                    pageResultList.add(extInterfaceAssembler.doToEntity(n));
                }
        );
        return pageResultList;
    }
}
