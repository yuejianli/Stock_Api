package top.yueshushu.learn.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yueshushu.learn.annotation.AuthToken;
import top.yueshushu.learn.business.BKBusiness;
import top.yueshushu.learn.mode.ro.StockBKMoneyStatRo;
import top.yueshushu.learn.mode.vo.StockBKVo;
import top.yueshushu.learn.response.OutputResult;

import javax.annotation.Resource;
import java.util.List;

/**
 * 版块历史
 *
 * @author yuejianli
 * @date 2023-02-07
 */
@RestController
@RequestMapping("/stockBk")
@ApiOperation("股票版块历史记录")
public class StockBKController {

    @Resource
    private BKBusiness bkBusiness;

    @ApiOperation("查询所有的版块列表")
    @PostMapping("/listBk")
    @AuthToken
    public OutputResult<List<StockBKVo>> listBk(@RequestBody StockBKMoneyStatRo stockBKMoneyStatRo) {
        return bkBusiness.listBk();
    }


    @ApiOperation("查询所有的版块资金类型")
    @PostMapping("/listMoneyType")
    @AuthToken
    public OutputResult<List<StockBKVo>> listMoneyType(@RequestBody StockBKMoneyStatRo stockBKMoneyStatRo) {
        return bkBusiness.listMoneyType();
    }


    @ApiOperation("查询某个版块历史记录列表")
    @PostMapping("/getCharStat")
    @AuthToken
    public OutputResult getCharStat(@RequestBody StockBKMoneyStatRo stockBKMoneyStatRo) {
        return bkBusiness.getCharStat(stockBKMoneyStatRo);
    }

}
