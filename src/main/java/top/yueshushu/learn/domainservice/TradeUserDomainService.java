package top.yueshushu.learn.domainservice;

import com.baomidou.mybatisplus.extension.service.IService;
import top.yueshushu.learn.domain.TradeUserDo;
import top.yueshushu.learn.domain.UserDo;

/**
 * @Description 交易用户的操作
 * @Author yuejianli
 * @Date 2022/5/21 06:19
 **/
public interface TradeUserDomainService extends IService<TradeUserDo> {
    /**
     * 根据系统用户编号获取对应的交易用户对象信息
     * @param userId 系统用户编号
     * @return 返回交易用户对象信息
     */
    TradeUserDo getByUserId(Integer userId);
}
