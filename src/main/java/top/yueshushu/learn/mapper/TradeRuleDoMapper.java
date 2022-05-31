package top.yueshushu.learn.mapper;

import org.apache.ibatis.annotations.Param;
import top.yueshushu.learn.mode.dto.StockRuleDto;
import top.yueshushu.learn.mode.dto.TradeRuleStockQueryDto;
import top.yueshushu.learn.domain.TradeRuleDo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 交易规则表 Mapper 接口
 * </p>
 *
 * @author 两个蝴蝶飞  自定义的
 * @since 2022-01-26
 */
public interface TradeRuleDoMapper extends BaseMapper<TradeRuleDo> {
    /**
     * 查询股票适用的规则信息
     *
     * @param tradeRuleStockQueryDto 查询条件
     * @return java.util.List<top.yueshushu.learn.mode.dto.StockRuleDto>
     * @date 2022/1/28 15:50
     * @author zk_yjl
     */
    List<StockRuleDto> listRuleByQuery(@Param("tradeRuleStockQueryDto") TradeRuleStockQueryDto tradeRuleStockQueryDto);
}
