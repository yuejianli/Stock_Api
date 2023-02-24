package top.yueshushu.learn.controller;

import cn.hutool.core.date.DateUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yueshushu.learn.annotation.AuthToken;
import top.yueshushu.learn.business.StockPoolBusiness;
import top.yueshushu.learn.common.Const;
import top.yueshushu.learn.mode.ro.StockPoolRo;
import top.yueshushu.learn.mode.vo.DistVo;
import top.yueshushu.learn.response.OutputResult;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 股票池信息
 *
 * @author yuejianli
 * @date 2023-02-24
 */
@RestController
@RequestMapping("/stockPool")
@ApiOperation("股票池历史记录")
public class StockPoolController {
    @Resource
    private StockPoolBusiness stockPoolBusiness;

    @ApiOperation("查询股票池类型")
    @PostMapping("/listPoolType")
    @AuthToken
    public OutputResult<List<DistVo>> listPoolType(@RequestBody StockPoolRo stockPoolRo) {
        return stockPoolBusiness.listPoolType();
    }


    @ApiOperation("查询股票的池统计信息")
    @PostMapping("/listPool")
    @AuthToken
    public OutputResult listPool(@RequestBody StockPoolRo stockPoolRo) {
        handlerDate(stockPoolRo);
        return stockPoolBusiness.listPool(stockPoolRo);
    }


    /**
     * 处理日期
     *
     * @param stockPoolRo 历史记录 日期对象调整
     */
    private void handlerDate(StockPoolRo stockPoolRo) {
        Date now = DateUtil.date();
        if (!StringUtils.hasText(stockPoolRo.getEndDate())) {
            Date endDate = DateUtil.offsetDay(now, -1);
            stockPoolRo.setEndDate(DateUtil.format(endDate, Const.SIMPLE_DATE_FORMAT));
        }

        if (!StringUtils.hasText(stockPoolRo.getStartDate())) {
            Date startDate = DateUtil.offsetDay(now, -14);
            stockPoolRo.setStartDate(DateUtil.format(startDate, Const.SIMPLE_DATE_FORMAT));
        }
    }

}
