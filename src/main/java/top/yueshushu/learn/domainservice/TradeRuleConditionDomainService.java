package top.yueshushu.learn.domainservice;

import com.baomidou.mybatisplus.extension.service.IService;
import top.yueshushu.learn.domain.TradeRuleConditionDo;

/**
 * @Description 交易的规则
 * @Author yuejianli
 * @Date 2022/5/20 23:23
 **/
public interface TradeRuleConditionDomainService extends IService<TradeRuleConditionDo> {
    /**
     * 根据条件编码获取信息
     *
     * @param code 条件编码
     */
    TradeRuleConditionDo getByCode(String code);
}
