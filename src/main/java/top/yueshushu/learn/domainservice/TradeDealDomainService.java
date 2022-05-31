package top.yueshushu.learn.domainservice;

import com.baomidou.mybatisplus.extension.service.IService;
import top.yueshushu.learn.domain.TradeDealDo;
import top.yueshushu.learn.mode.dto.TradeDealQueryDto;

import java.util.List;

/**
 * @Description  成交信息的操作
 * @Author yuejianli
 * @Date 2022/5/20 23:23
 **/
public interface TradeDealDomainService extends IService<TradeDealDo> {
    /**
     * 根据用户编号和交易类型，查询当前天的成交记录信息
     * @param tradeDealQueryDto 成交交易查询对象，包含用户编号和类型
     * @return 根据用户编号和交易类型，查询当前天的交易记录信息
     */
    List<TradeDealDo> listByQuery(TradeDealQueryDto tradeDealQueryDto);
    /**
     * 删除今日的真实交易信息
     * @param tradeDealQueryDto 今日的交易信息对象
     */
    void deleteToDayByQuery(TradeDealQueryDto tradeDealQueryDto);
    /**
     * 根据用户编号和交易类型，查询历史交易记录信息
     * @param tradeDealQueryDto 成交交易查询对象，包含用户编号和类型
     * @return 根据用户编号和交易类型，查询历史14天的交易记录信息
     */
    List<TradeDealDo> listHistoryByQuery(TradeDealQueryDto tradeDealQueryDto);
}
