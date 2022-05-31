package top.yueshushu.learn.service;

import top.yueshushu.learn.mode.ro.RevokeRo;
import top.yueshushu.learn.response.OutputResult;

/**
 * @ClassName:RevokeService
 * @Description 撤销委托单
 * @Author 岳建立
 * @Date 2022/1/8 16:56
 * @Version 1.0
 **/
public interface RevokeService {
    /**
     * 撤销单处理
     * @param revokeRo
     * @return
     */
    OutputResult revoke(RevokeRo revokeRo);
}
