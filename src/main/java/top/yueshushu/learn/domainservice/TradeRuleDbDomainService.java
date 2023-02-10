package top.yueshushu.learn.domainservice;

import com.baomidou.mybatisplus.extension.service.IService;
import top.yueshushu.learn.domain.TradeRuleDbDo;

import java.util.List;

/**
 * <p>
 * 打板交易规则配置 服务类
 * </p>
 *
 * @author 岳建立
 * @since 2023-02-10
 */
public interface TradeRuleDbDomainService extends IService<TradeRuleDbDo> {
    /**
     * 根据用户编号和类型查询配置的打板信息
     *
     * @param userId   用户编号
     * @param mockType 类型
     */
    List<TradeRuleDbDo> listByQuery(Integer userId, Integer mockType);

    /**
     * 根据用户编号和类型查询配置的打板信息
     *
     * @param userId   用户编号
     * @param mockType 类型
     */
    TradeRuleDbDo getByQuery(Integer userId, Integer mockType);
}
