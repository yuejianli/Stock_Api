package top.yueshushu.learn.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yueshushu.learn.annotation.AuthToken;
import top.yueshushu.learn.business.ConfigBusiness;
import top.yueshushu.learn.common.ResultCode;
import top.yueshushu.learn.mode.ro.ConfigRo;
import top.yueshushu.learn.response.OutputResult;

import javax.annotation.Resource;

/**
 * <p>
 * 全局性系统配置 我是自定义的
 * </p>
 *
 * @author 岳建立
 * @date 2022-01-02
 */
@RestController
@RequestMapping("/config")
@Api("系统配置参数")
public class ConfigController extends BaseController {
    @Resource
    private ConfigBusiness configBusiness;

    @PostMapping("/list")
    @ApiOperation("查询配置参数")
    public OutputResult list(@RequestBody ConfigRo configRo) {
        configRo.setUserId(getUserId());
        return configBusiness.listConfig(configRo);
    }

    @PostMapping("/update")
    @ApiOperation("修改配置参数")
    @AuthToken
    public OutputResult update(@RequestBody ConfigRo configRo) {
        configRo.setUserId(getUserId());
        if (configRo.getId() == null){
            return OutputResult.buildAlert(
                    ResultCode.ID_IS_EMPTY
            );
        }
        return configBusiness.update(configRo);
    }

    @PostMapping("/reset")
    @ApiOperation("自定义配置信息重置")
    @AuthToken
    public OutputResult reset(@RequestBody ConfigRo configRo) {
        configRo.setUserId(getUserId());
        if (configRo.getId() == null){
            return OutputResult.buildAlert(
                    ResultCode.ID_IS_EMPTY
            );
        }
        return configBusiness.reset(configRo);
    }
}
