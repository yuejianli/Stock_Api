package top.yueshushu.learn.extension.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.yueshushu.learn.entity.TradeMethod;
import top.yueshushu.learn.extension.assembler.ExtInterfaceAssembler;
import top.yueshushu.learn.extension.domain.ExtInterfaceDo;
import top.yueshushu.learn.extension.domainservice.ExtInterfaceDomainService;
import top.yueshushu.learn.extension.entity.ExtInterface;
import top.yueshushu.learn.extension.model.ro.ExtInterfaceRo;
import top.yueshushu.learn.extension.service.ExtInterfaceService;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.response.PageResponse;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description 接口应用实现
 * @Author yuejianli
 * @Date 2022/6/11 15:07
 **/
@Service
public class ExtInterfaceServiceImpl implements ExtInterfaceService {
    @Resource
    private ExtInterfaceDomainService extInterfaceDomainService;
    @Resource
    private ExtInterfaceAssembler extInterfaceAssembler;

    @Override
    public OutputResult pageList(ExtInterfaceRo extInterfaceRo) {
        Page<Object> pageGithubResult = PageHelper.startPage(extInterfaceRo.getPageNum(), extInterfaceRo.getPageSize());
        List<ExtInterfaceDo> extInterfaceDoList = extInterfaceDomainService.listByName(extInterfaceRo.getKeyword());
        if (CollectionUtils.isEmpty(extInterfaceDoList)) {
            return OutputResult.buildSucc(
                    PageResponse.emptyPageResponse()
            );
        }

        List<ExtInterface> pageResultList = new ArrayList<>(extInterfaceDoList.size());
        extInterfaceDoList.forEach(
                n -> {
                    pageResultList.add(extInterfaceAssembler.doToEntity(n));
                }
        );
        PageInfo pageInfo = new PageInfo<>(pageResultList);
        return OutputResult.buildSucc(new PageResponse<TradeMethod>(pageGithubResult.getTotal(),
                pageInfo.getList()));
    }
}
