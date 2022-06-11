package top.yueshushu.learn.extension.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yueshushu.learn.extension.business.ExtCustomerJobBusiness;
import top.yueshushu.learn.extension.model.ro.ExtCustomerJobRo;
import top.yueshushu.learn.response.OutputResult;

import javax.annotation.Resource;

/**
 * <p>
 * 客户配置接口功能
 * </p>
 *
 * @author 岳建立
 * @date 2022-01-02
 */
@RestController
@RequestMapping("/extCustomerJob")
@Api("客户配置接口功能")
public class ExtCustomerJobController {

    @Resource
    private ExtCustomerJobBusiness extCustomerJobBusiness;

    @PostMapping("/list")
    @ApiOperation("查询用户在某个任务上配置了哪些功能")
    public OutputResult list(@RequestBody ExtCustomerJobRo extCustomerJobRo) {
        if (extCustomerJobRo.getExtCustomerId() == null || extCustomerJobRo.getExtJobId() == null) {
            return OutputResult.buildSucc();
        }
        return extCustomerJobBusiness.list(extCustomerJobRo);
    }

    @PostMapping("/config")
    @ApiOperation("配置用户在某个任务上的功能列表")
    public OutputResult config(@RequestBody ExtCustomerJobRo extCustomerJobRo) {
        return extCustomerJobBusiness.config(extCustomerJobRo);
    }
}
