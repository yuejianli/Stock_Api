package top.yueshushu.learn.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yueshushu.learn.business.CacheBusiness;
import top.yueshushu.learn.common.ResultCode;
import top.yueshushu.learn.mode.ro.CacheRo;
import top.yueshushu.learn.mode.ro.ConfigRo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.service.CacheService;
import top.yueshushu.learn.service.ConfigService;

import javax.annotation.Resource;

/**
 * @ClassName:CacheController
 * @Description 缓存的配置信息
 * @Author 岳建立
 * @Date 2022/1/3 10:34
 * @Version 1.0
 **/
@RestController
@RequestMapping("/cache")
@Api("缓存信息")
public class CacheController extends BaseController {
    @Resource
    private CacheBusiness cacheBusiness;

    @PostMapping("/list")
    @ApiOperation("查询全部缓存信息")
    public OutputResult list(@RequestBody CacheRo cacheRo) {
        cacheRo.setUserId(getUserId());
        if (cacheRo.getType() == null){
            return OutputResult.buildAlert(
                    ResultCode.CACHE_TYPE_IS_EMPTY
            );
        }
        return cacheBusiness.listCache(cacheRo);
    }

    @PostMapping("/update")
    @ApiOperation("修改缓存信息")
    public OutputResult update(@RequestBody CacheRo cacheRo) {
        cacheRo.setUserId(getUserId());
        if (cacheRo.getType() == null){
            return OutputResult.buildAlert(
                    ResultCode.CACHE_TYPE_IS_EMPTY
            );
        }
        if (!StringUtils.hasText(cacheRo.getKey())){
            return OutputResult.buildAlert(
                    ResultCode.CACHE_KEY_IS_EMPTY
            );
        }
        return cacheBusiness.update(cacheRo);
    }

    @PostMapping("/delete")
    @ApiOperation("删除缓存信息")
    public OutputResult delete(@RequestBody CacheRo cacheRo) {
        cacheRo.setUserId(getUserId());
        if (cacheRo.getType() == null){
            return OutputResult.buildAlert(
                    ResultCode.CACHE_TYPE_IS_EMPTY
            );
        }
        if (!StringUtils.hasText(cacheRo.getKey())){
            return OutputResult.buildAlert(
                    ResultCode.CACHE_KEY_IS_EMPTY
            );
        }
        return cacheBusiness.delete(cacheRo);
    }
}
