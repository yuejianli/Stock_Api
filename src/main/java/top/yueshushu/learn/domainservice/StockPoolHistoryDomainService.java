package top.yueshushu.learn.domainservice;

import com.baomidou.mybatisplus.extension.service.IService;
import top.yueshushu.learn.domain.StockPoolHistoryDo;
import top.yueshushu.learn.mode.dto.StockPoolQueryDto;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 岳建立
 * @since 2023-02-15
 */
public interface StockPoolHistoryDomainService extends IService<StockPoolHistoryDo> {
    /**
     * 根据条件查询多个信息
     *
     * @param stockPoolQueryDto 查询条件
     */
    List<StockPoolHistoryDo> listByCondition(StockPoolQueryDto stockPoolQueryDto);

}
