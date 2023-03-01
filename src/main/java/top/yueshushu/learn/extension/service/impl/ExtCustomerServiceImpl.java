package top.yueshushu.learn.extension.service.impl;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.yueshushu.learn.common.Const;
import top.yueshushu.learn.common.ResultCode;
import top.yueshushu.learn.enumtype.DataFlagType;
import top.yueshushu.learn.extension.assembler.ExtCustomerAssembler;
import top.yueshushu.learn.extension.domain.ExtCustomerDo;
import top.yueshushu.learn.extension.domainservice.ExtCustomerDomainService;
import top.yueshushu.learn.extension.entity.ExtCustomer;
import top.yueshushu.learn.extension.model.ro.ExtCustomerRo;
import top.yueshushu.learn.extension.model.vo.ExtCustomerVo;
import top.yueshushu.learn.extension.service.ExtCustomerService;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.response.PageResponse;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Description TODO
 * @Author yuejianli
 * @Date 2022/6/11 15:07
 **/
@Service
public class ExtCustomerServiceImpl implements ExtCustomerService {
    @Resource
    private ExtCustomerDomainService extCustomerDomainService;
    @Resource
    private ExtCustomerAssembler extCustomerAssembler;

    @Override
    public OutputResult pageList(ExtCustomerRo extCustomerRo) {
        Page<Object> pageGithubResult = PageHelper.startPage(extCustomerRo.getPageNum(), extCustomerRo.getPageSize());
        List<ExtCustomerDo> extCustomerDOList = extCustomerDomainService.listByKeyword(extCustomerRo.getKeyword());
        if (CollectionUtils.isEmpty(extCustomerDOList)) {
            return OutputResult.buildSucc(
                    PageResponse.emptyPageResponse()
            );
        }

        List<ExtCustomerVo> pageResultList = new ArrayList<>(extCustomerDOList.size());
        extCustomerDOList.forEach(
                n -> {
                    pageResultList.add(extCustomerAssembler.entityToVo(extCustomerAssembler.doToEntity(n)));
                }
        );
        return OutputResult.buildSucc(new PageResponse<>(pageGithubResult.getTotal(), pageResultList));
    }

    @Override
    public OutputResult add(ExtCustomerRo extCustomerRo) {
        // 看账号是否存在
        ExtCustomerDo dbextCustomerDo = extCustomerDomainService.getByAccount(extCustomerRo.getUserAccount());
        if (dbextCustomerDo != null) {
            return OutputResult.buildFail(ResultCode.EXT_ACCOUNT_EXIST);
        }
        ExtCustomer extCustomer = extCustomerAssembler.roToEntity(extCustomerRo);
        extCustomer.setBirthday(DateUtil.parse(extCustomerRo.getBirthdayStr(), Const.SIMPLE_DATE_FORMAT));
        ExtCustomerDo extCustomerDo = extCustomerAssembler.entityToDo(extCustomer);
        extCustomerDo.setFlag(DataFlagType.NORMAL.getCode());
        extCustomerDomainService.save(extCustomerDo);
        return OutputResult.buildSucc(ResultCode.SUCCESS);
    }

    @Override
    public OutputResult update(ExtCustomerRo extCustomerRo) {
        ExtCustomerDo idDo = extCustomerDomainService.getById(extCustomerRo.getId());
        if (idDo == null) {
            return OutputResult.buildFail(ResultCode.EXT_ID_NOT_EXIST);
        }
        if (!idDo.getUserAccount().equals(extCustomerRo.getUserAccount())) {
            // 看账号是否存在
            ExtCustomerDo dbextCustomerDo = extCustomerDomainService.getByAccount(extCustomerRo.getUserAccount());
            if (dbextCustomerDo != null) {
                return OutputResult.buildFail(ResultCode.EXT_ACCOUNT_EXIST);
            }
        }
        ExtCustomer extCustomer = extCustomerAssembler.roToEntity(extCustomerRo);
        extCustomer.setBirthday(DateUtil.parse(extCustomerRo.getBirthdayStr(), Const.SIMPLE_DATE_FORMAT));
        ExtCustomerDo extCustomerDo = extCustomerAssembler.entityToDo(extCustomer);
        extCustomerDo.setFlag(DataFlagType.NORMAL.getCode());
        extCustomerDomainService.updateById(extCustomerDo);
        return OutputResult.buildSucc(ResultCode.SUCCESS);
    }

    @Override
    public OutputResult deleteById(Integer id) {
        ExtCustomerDo idDo = extCustomerDomainService.getById(id);
        if (idDo == null) {
            return OutputResult.buildFail(ResultCode.EXT_ID_NOT_EXIST);
        }
        extCustomerDomainService.removeById(id);
        return OutputResult.buildSucc(ResultCode.SUCCESS);
    }

    @Override
    public List<ExtCustomer> findAll() {
        List<ExtCustomerDo> extCustomerDOList = extCustomerDomainService.listByKeyword("");
        if (CollectionUtils.isEmpty(extCustomerDOList)) {
            return Collections.emptyList();
        }

        List<ExtCustomer> resultList = new ArrayList<>(extCustomerDOList.size());
        extCustomerDOList.forEach(
                n -> {
                    resultList.add(extCustomerAssembler.doToEntity(n));
                }
        );
        return resultList;
    }
}
