package top.yueshushu.learn.domainservice;

import com.baomidou.mybatisplus.extension.service.IService;
import top.yueshushu.learn.domain.TradePositionDo;

import java.util.List;

/**
 * @Description 持仓的操作
 * @Author yuejianli
 * @Date 2022/5/20 23:23
 **/
public interface TradePositionDomainService extends IService<TradePositionDo> {
    /**
     * 根据用户编号和类型查询相应的持仓记录信息
     * @param userId 用户编号
     * @param mockType 持仓类型
     * @param code 股票编码
     * @return 根据用户编号和类型查询相应的持仓记录信息
     */
    List<TradePositionDo> listByUserIdAndMockTypeAndCode(Integer userId, Integer mockType, String code);

    /**
     * 定时同步可用的股票数量
     */
    void syncUseAmountByXxlJob();

    /**
     * 删除真实的股票持仓记录信息
     * @param userId 用户编号
     * @param mockType 交易类型
     */
    void deleteByUserIdAndMockType(Integer userId, Integer mockType);
}
