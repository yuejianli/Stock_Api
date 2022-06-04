package top.yueshushu.learn.controller;


import cn.hutool.core.date.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yueshushu.learn.business.JobInfoBusiness;
import top.yueshushu.learn.business.StockUpdateLogBusiness;
import top.yueshushu.learn.common.Const;
import top.yueshushu.learn.common.ResultCode;
import top.yueshushu.learn.enumtype.DataFlagType;
import top.yueshushu.learn.mode.ro.JobInfoRo;
import top.yueshushu.learn.mode.ro.StockUpdateLogRo;
import top.yueshushu.learn.response.OutputResult;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>
 * 股票更新历史
 * </p>
 *
 * @author 岳建立
 * @date 2022-01-02
 */
@RestController
@RequestMapping("/stockLog")
@Api("股票更新历史")
public class StockUpdateLogController extends BaseController {
    @Resource
    private StockUpdateLogBusiness stockUpdateLogBusiness;

    @PostMapping("/list")
    @ApiOperation("查询近一个月的更新记录信息")
    public OutputResult list(@RequestBody StockUpdateLogRo stockUpdateLogRo) {
        Date now = DateUtil.date();
        if (StringUtils.isEmpty(stockUpdateLogRo.getEndDate())){
            stockUpdateLogRo.setEndDate(DateUtil.format(now, Const.SIMPLE_DATE_FORMAT));
        }
        if (StringUtils.isEmpty(stockUpdateLogRo.getStartDate())){
            Date beforeOneMonth = DateUtil.offsetMonth(now,-1);
            stockUpdateLogRo.setStartDate(DateUtil.format(beforeOneMonth, Const.SIMPLE_DATE_FORMAT));
        }
        return stockUpdateLogBusiness.list(stockUpdateLogRo);
    }

}
