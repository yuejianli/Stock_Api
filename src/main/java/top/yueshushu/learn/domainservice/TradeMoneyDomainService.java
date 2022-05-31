package top.yueshushu.learn.domainservice;

import com.baomidou.mybatisplus.extension.service.IService;
import top.yueshushu.learn.domain.TradeMoneyDo;

/**
 * @Description 持仓金额的操作
 * @Author yuejianli
 * @Date 2022/5/20 23:23
 **/
public interface TradeMoneyDomainService extends IService<TradeMoneyDo> {
    /**
     * 根据用户编号和类型获取相应的资金信息
     * @param userId 用户编号
     * @param mockType 股票类型
     * @return 根据用户编号和类型获取相应的资金信息
     */
    TradeMoneyDo getByUserIdAndMockType(Integer userId, Integer mockType);
}
