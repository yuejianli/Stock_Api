package top.yueshushu.learn.extension.business.impl;

import org.springframework.stereotype.Service;
import top.yueshushu.learn.extension.business.ExtCustomerBusiness;
import top.yueshushu.learn.extension.model.ro.ExtCustomerRo;
import top.yueshushu.learn.extension.service.ExtCustomerJobService;
import top.yueshushu.learn.extension.service.ExtCustomerService;
import top.yueshushu.learn.response.OutputResult;

import javax.annotation.Resource;

/**
 * @Description TODO
 * @Author yuejianli
 * @Date 2022/6/11 15:09
 **/
@Service
public class ExtCustomerBusinessImpl implements ExtCustomerBusiness {
    @Resource
    private ExtCustomerService extCustomerService;
    @Resource
    private ExtCustomerJobService extCustomerJobService;

    @Override
    public OutputResult list(ExtCustomerRo extCustomerRo) {
        return extCustomerService.pageList(extCustomerRo);
    }

    @Override
    public OutputResult add(ExtCustomerRo extCustomerRo) {
        return extCustomerService.add(extCustomerRo);
    }

    @Override
    public OutputResult update(ExtCustomerRo extCustomerRo) {
        return extCustomerService.update(extCustomerRo);
    }

    @Override
    public OutputResult delete(Integer id) {
        OutputResult outputResult = extCustomerService.deleteById(id);
        if (!outputResult.getSuccess()) {
            return outputResult;
        }
        // 删除对应的任务信息
        extCustomerJobService.removeByUserId(id);
        return OutputResult.buildSucc();
    }
}
