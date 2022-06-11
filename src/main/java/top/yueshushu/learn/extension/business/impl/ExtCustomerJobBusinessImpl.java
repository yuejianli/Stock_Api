package top.yueshushu.learn.extension.business.impl;

import org.springframework.stereotype.Service;
import top.yueshushu.learn.extension.business.ExtCustomerJobBusiness;
import top.yueshushu.learn.extension.entity.ExtInterface;
import top.yueshushu.learn.extension.model.ro.ExtCustomerJobRo;
import top.yueshushu.learn.extension.model.vo.ExtCustomerJobVo;
import top.yueshushu.learn.extension.service.ExtCustomerJobService;
import top.yueshushu.learn.extension.service.ExtInterfaceService;
import top.yueshushu.learn.response.OutputResult;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description 客户配置功能接口
 * @Author yuejianli
 * @Date 2022/6/11 15:09
 **/
@Service
public class ExtCustomerJobBusinessImpl implements ExtCustomerJobBusiness {
    @Resource
    private ExtCustomerJobService extCustomerJobService;
    @Resource
    private ExtInterfaceService extInterfaceService;

    @Override
    public OutputResult list(ExtCustomerJobRo extCustomerJobRo) {
        OutputResult<ExtCustomerJobVo> outputResult = extCustomerJobService.list(extCustomerJobRo);
        //获取信息
        ExtCustomerJobVo data = outputResult.getData();
        List<ExtInterface> extInterfaceList = extInterfaceService.listAll();
        data.setAllInterfaceList(extInterfaceList);
        return OutputResult.buildSucc(data);
    }

    @Override
    public OutputResult config(ExtCustomerJobRo extCustomerJobRo) {
        return extCustomerJobService.config(extCustomerJobRo);
    }
}
