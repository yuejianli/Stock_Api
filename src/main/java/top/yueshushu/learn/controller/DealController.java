package top.yueshushu.learn.controller;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yueshushu.learn.annotation.AuthToken;
import top.yueshushu.learn.business.DealBusiness;
import top.yueshushu.learn.common.ResultCode;
import top.yueshushu.learn.mode.ro.DealRo;
import top.yueshushu.learn.response.OutputResult;

import javax.annotation.Resource;

/**
 * <p>
 * 成交委托单的处理
 * </p>
 *
 * @author 两个蝴蝶飞
 * @date 2022-01-03
 */
@RestController
@RequestMapping("/deal")
@ApiModel("成交委托单")
public class DealController extends BaseController {
    @Resource
    private DealBusiness dealBusiness;
    @PostMapping("/deal")
    @ApiOperation("成交委托信息")
    @AuthToken
    public OutputResult deal(@RequestBody DealRo dealRo){
        dealRo.setUserId(getUserId());
        if (null == dealRo.getId()){
            return OutputResult.buildAlert(ResultCode.ID_IS_EMPTY);
        }
        return dealBusiness.deal(dealRo);
    }
}
