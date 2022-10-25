package top.yueshushu.learn.domainservice;

import com.baomidou.mybatisplus.extension.service.IService;
import top.yueshushu.learn.domain.TradeEntrustDo;
import top.yueshushu.learn.mode.dto.TradeEntrustQueryDto;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Description 持仓金额的操作
 * @Author yuejianli
 * @Date 2022/5/20 23:23
 **/
public interface TradeEntrustDomainService extends IService<TradeEntrustDo> {
    /**
     * 根据用户编号和交易类型，查询当前天的交易记录信息
     * @param tradeEntrustQueryDto 委托交易查询对象，包含用户编号和类型
     * @return 根据用户编号和交易类型，查询当前天的交易记录信息
     */
    List<TradeEntrustDo> listByQuery(TradeEntrustQueryDto tradeEntrustQueryDto);

    /**
     * 删除今日的真实委托信息
     * @param tradeEntrustQueryDto 今日的委托信息对象
     */
    void deleteToDayByQuery(TradeEntrustQueryDto tradeEntrustQueryDto);

    /**
     * 根据用户编号和交易类型，查询历史14天的交易记录信息
     *
     * @param tradeEntrustQueryDto 委托交易查询对象，包含用户编号和类型
     * @return 根据用户编号和交易类型，查询历史14天的交易记录信息
     */
    List<TradeEntrustDo> listHistoryByQuery(TradeEntrustQueryDto tradeEntrustQueryDto);

    /**
     * 查询总的手续费
     *
     * @param tradeEntrustQueryDto 委托交易查询对象，包含用户编号和类型
     */
    BigDecimal getTotalHandMoney(TradeEntrustQueryDto tradeEntrustQueryDto);

}
