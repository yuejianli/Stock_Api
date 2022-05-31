package top.yueshushu.learn.mapper;

import org.apache.ibatis.annotations.Param;
import top.yueshushu.learn.mode.dto.TradeRuleStockQueryDto;
import top.yueshushu.learn.domain.TradeRuleStockDo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 规则股票对应信息表 Mapper 接口
 * </p>
 *
 * @author 两个蝴蝶飞  自定义的
 * @since 2022-01-26
 */
public interface TradeRuleStockDoMapper extends BaseMapper<TradeRuleStockDo> {
    /**
     * 查询同一个类型的 其它配置的股票信息
     *
     * @param tradeRuleStockQueryDto
     * @return java.util.List<top.yueshushu.learn.pojo.TradeRuleStock>
     * @date 2022/1/27 15:14
     * @author zk_yjl
     */
    List<TradeRuleStockDo> listNotInRuleId(@Param("tradeRuleStockQueryDto") TradeRuleStockQueryDto tradeRuleStockQueryDto);


    /**
     * 移除之前的配置
     * @param userId 用户编号
     * @param mockType 类型
     * @param removeCodeList 要删除的股票编码
     */
    void removeOtherStock(@Param("userId") Integer userId, @Param("mockType") Integer mockType,
                          @Param("removeCodeList") List<String> removeCodeList);
}
