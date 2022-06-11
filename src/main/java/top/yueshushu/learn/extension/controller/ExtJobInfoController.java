package top.yueshushu.learn.extension.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yueshushu.learn.common.ResultCode;
import top.yueshushu.learn.controller.BaseController;
import top.yueshushu.learn.enumtype.DataFlagType;
import top.yueshushu.learn.extension.business.ExtJobInfoBusiness;
import top.yueshushu.learn.extension.model.ro.ExtJobInfoRo;
import top.yueshushu.learn.response.OutputResult;

import javax.annotation.Resource;

/**
 * <p>
 * 扩展定时任务处理
 * </p>
 *
 * @author 岳建立
 * @date 2022-01-02
 */
@RestController
@RequestMapping("/extjobInfo")
@Api("扩展定时任务处理")
public class ExtJobInfoController extends BaseController {
    @Resource
    private ExtJobInfoBusiness extJobInfoBusiness;

    @PostMapping("/list")
    @ApiOperation("查询任务信息")
    public OutputResult list(@RequestBody ExtJobInfoRo extJobInfoRo) {
        return extJobInfoBusiness.listJob(extJobInfoRo);
    }

    @PostMapping("/disable")
    @ApiOperation("禁用")
    public OutputResult disable(@RequestBody ExtJobInfoRo extJobInfoRo) {
        if (extJobInfoRo.getId() == null) {
            return OutputResult.buildAlert(ResultCode.ID_IS_EMPTY);
        }
        return extJobInfoBusiness.changeStatus(extJobInfoRo.getId(), DataFlagType.DELETE);
    }

    @PostMapping("/enable")
    @ApiOperation("启用")
    public OutputResult enable(@RequestBody ExtJobInfoRo extJobInfoRo) {
        if (extJobInfoRo.getId() == null) {
            return OutputResult.buildAlert(ResultCode.ID_IS_EMPTY);
        }
        return extJobInfoBusiness.changeStatus(extJobInfoRo.getId(), DataFlagType.NORMAL);
    }

    @PostMapping("/delete")
    @ApiOperation("删除定时任务")
    public OutputResult delete(@RequestBody ExtJobInfoRo extJobInfoRo) {
        if (extJobInfoRo.getId() == null) {
            return OutputResult.buildAlert(ResultCode.ID_IS_EMPTY);
        }
        return extJobInfoBusiness.deleteById(extJobInfoRo.getId());
    }

    @PostMapping("/handler")
    @ApiOperation("手动执行定时任务")
    public OutputResult handler(@RequestBody ExtJobInfoRo extJobInfoRo) {
        if (extJobInfoRo.getId() == null) {
            return OutputResult.buildAlert(ResultCode.ID_IS_EMPTY);
        }
        return extJobInfoBusiness.handlerById(extJobInfoRo.getId());
    }
}
