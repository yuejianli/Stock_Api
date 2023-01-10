package top.yueshushu.learn.business;

import top.yueshushu.learn.mode.ro.RevokeRo;
import top.yueshushu.learn.response.OutputResult;

/**
 * @Description 委托撤销
 * @Author yuejianli
 * @Date 2022/5/28 19:43
 **/
public interface RevokeBusiness {
    /**
     * 撤销委托信息
     *
     * @param revokeRo 委托信息
     * @return 撤销委托信息
     */
    OutputResult revoke(RevokeRo revokeRo);

    /**
     * 真实的撤消操作
     *
     * @param revokeRo 撤消对象
     */
    OutputResult realRevoke(RevokeRo revokeRo);
}
