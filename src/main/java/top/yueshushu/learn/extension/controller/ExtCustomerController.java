package top.yueshushu.learn.extension.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yueshushu.learn.extension.business.ExtCustomerBusiness;
import top.yueshushu.learn.extension.model.ro.ExtCustomerRo;
import top.yueshushu.learn.mode.ro.IdRo;
import top.yueshushu.learn.response.OutputResult;

import javax.annotation.Resource;

/**
 * <p>
 * 客户查询
 * </p>
 *
 * @author 岳建立
 * @date 2022-01-02
 */
@RestController
@RequestMapping("/extCustomer")
@Api("查询客户列表")
public class ExtCustomerController {

    @Resource
    private ExtCustomerBusiness extCustomerBusiness;

    @PostMapping("/list")
    @ApiOperation("查询客户列表")
    public OutputResult list(@RequestBody ExtCustomerRo extCustomerRo) {
        return extCustomerBusiness.list(extCustomerRo);
    }

    @PostMapping("/add")
    @ApiOperation("添加客户对象")
    public OutputResult add(@RequestBody ExtCustomerRo extCustomerRo) {
        return extCustomerBusiness.add(extCustomerRo);
    }

    @PostMapping("/update")
    @ApiOperation("更新客户对象")
    public OutputResult update(@RequestBody ExtCustomerRo extCustomerRo) {
        return extCustomerBusiness.update(extCustomerRo);
    }

    @PostMapping("/delete")
    @ApiOperation("删除客户对象")
    public OutputResult delete(@RequestBody IdRo idRo) {
        return extCustomerBusiness.delete(idRo.getId());
    }
}
