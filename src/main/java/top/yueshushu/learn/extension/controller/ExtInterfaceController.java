package top.yueshushu.learn.extension.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yueshushu.learn.extension.business.ExtInterfaceBusiness;
import top.yueshushu.learn.extension.model.ro.ExtInterfaceRo;
import top.yueshushu.learn.response.OutputResult;

import javax.annotation.Resource;

/**
 * <p>
 * 扩展应用， 提供的接口信息
 * </p>
 *
 * @author 岳建立
 * @date 2022-01-02
 */
@RestController
@RequestMapping("/extInterface")
@Api("提供的接口信息")
public class ExtInterfaceController {

    @Resource
    private ExtInterfaceBusiness extInterfaceBusiness;

    @PostMapping("/list")
    @ApiOperation("查询提供的接口功能")
    public OutputResult list(@RequestBody ExtInterfaceRo extInterfaceRo) {
        return extInterfaceBusiness.list(extInterfaceRo);
    }

    @RequestMapping("/poem")
    public OutputResult poem() {
        return extInterfaceBusiness.poem();
    }
}
