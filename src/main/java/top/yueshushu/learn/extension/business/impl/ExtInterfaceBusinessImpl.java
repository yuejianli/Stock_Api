package top.yueshushu.learn.extension.business.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import top.yueshushu.learn.extension.business.ExtInterfaceBusiness;
import top.yueshushu.learn.extension.model.dto.shici.PoemResponse;
import top.yueshushu.learn.extension.model.ro.ExtInterfaceRo;
import top.yueshushu.learn.extension.model.vo.PoemVo;
import top.yueshushu.learn.extension.service.ExtFunctionService;
import top.yueshushu.learn.extension.service.ExtInterfaceService;
import top.yueshushu.learn.response.OutputResult;

import javax.annotation.Resource;

/**
 * @Description 接口实现应用
 * @Author yuejianli
 * @Date 2022/6/11 15:10
 **/
@Service
public class ExtInterfaceBusinessImpl implements ExtInterfaceBusiness {
    @Resource
    private ExtInterfaceService extInterfaceService;
    @Resource
    private ExtFunctionService extFunctionService;

    @Override
    public OutputResult list(ExtInterfaceRo extInterfaceRo) {
        return extInterfaceService.pageList(extInterfaceRo);
    }

    @Override
    public OutputResult poem() {
        PoemResponse poemResponse = extFunctionService.getPoem();
        PoemVo poemVo = new PoemVo();
        BeanUtils.copyProperties(poemResponse, poemVo);
        return OutputResult.buildSucc(poemVo);
    }
}
