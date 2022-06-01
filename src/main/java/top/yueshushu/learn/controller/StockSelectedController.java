package top.yueshushu.learn.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yueshushu.learn.business.StockSelectedBusiness;
import top.yueshushu.learn.common.ResultCode;
import top.yueshushu.learn.mode.ro.IdRo;
import top.yueshushu.learn.mode.ro.StockSelectedRo;
import top.yueshushu.learn.response.OutputResult;

import javax.annotation.Resource;

/**
 * <p>
 * 股票自选表,是用户自己选择的 我是自定义的
 * </p>
 *
 * @author 岳建立
 * @date 2022-01-02
 */
@RestController
@RequestMapping("/stockSelected")
@Api("自选表")
public class StockSelectedController extends BaseController{

    @Resource
    private StockSelectedBusiness stockSelectedBusiness;

    @PostMapping("/list")
    @ApiOperation("查询自选表信息")
    public OutputResult list(@RequestBody StockSelectedRo stockSelectedRo){
        stockSelectedRo.setUserId(getUserId());
        return stockSelectedBusiness.listSelected(stockSelectedRo);
    }

    @PostMapping("/yesHistory")
    @ApiOperation("查询当前自选表里面的记录信息")
    public OutputResult yesHistory(@RequestBody StockSelectedRo stockSelectedRo){
        stockSelectedRo.setUserId(getUserId());
        return stockSelectedBusiness.yesHistory(stockSelectedRo);
    }

    @PostMapping("/add")
    @ApiOperation("添加到自选表")
    public OutputResult add(@RequestBody StockSelectedRo stockSelectedRo){
        if (!StringUtils.hasText(stockSelectedRo.getStockCode())){
            return OutputResult.buildAlert(ResultCode.STOCK_CODE_IS_EMPTY);
        }
        stockSelectedRo.setUserId(getUserId());
        return stockSelectedBusiness.add(stockSelectedRo);
    }
    @PostMapping("/delete")
    @ApiOperation("单个移出自选表")
    public OutputResult delete(@RequestBody IdRo idRo){
        if (idRo.getId() == null){
            return OutputResult.buildAlert(ResultCode.ID_IS_EMPTY);
        }
        return stockSelectedBusiness.delete(idRo,getUserId());
    }
    @PostMapping("/deleteByCode")
    @ApiOperation("根据股票code进行移除,股票页面使用")
    public OutputResult deleteByCode(@RequestBody StockSelectedRo stockSelectedRo){
        stockSelectedRo.setUserId(getUserId());
        if (!StringUtils.hasText(stockSelectedRo.getStockCode())){
            return OutputResult.buildAlert(
                    ResultCode.STOCK_CODE_IS_EMPTY
            );
        }
        return stockSelectedBusiness.deleteByCode(stockSelectedRo);
    }

    @PostMapping("/editNotes")
    @ApiOperation("根据自选记录，编辑笔记")
    public OutputResult editNotes(@RequestBody StockSelectedRo stockSelectedRo){
        stockSelectedRo.setUserId(getUserId());
        if (stockSelectedRo.getId() == null){
            return OutputResult.buildAlert(
                    ResultCode.ID_IS_EMPTY
            );
        }
        if (!StringUtils.hasText(stockSelectedRo.getNotes())){
            return OutputResult.buildAlert(
                    ResultCode.STOCK_SELECTED_NOTES_EMPTY
            );
        }
        return stockSelectedBusiness.editNotes(stockSelectedRo);
    }




}
