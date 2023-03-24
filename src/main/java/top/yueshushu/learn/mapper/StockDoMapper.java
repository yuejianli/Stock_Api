package top.yueshushu.learn.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import top.yueshushu.learn.domain.StockDo;
import top.yueshushu.learn.mode.dto.StockQueryDto;

import java.util.List;

/**
 * <p>
 * 股票信息基本表 Mapper 接口
 * </p>
 *
 * @author 岳建立  自定义的
 * @since 2022-01-02
 */
public interface StockDoMapper extends BaseMapper<StockDo> {
    /**
     * 根据编码和类型进行查询
     *
     * @param code
     * @param exchange
     * @return
     */
    List<StockDo> selectByCodeAndType(@Param("code") String code, @Param("exchange") Integer exchange);

    /**
     * 全部删除
     */
    void deleteAll();

    /**
     * 根据关键字进行查询
     *
     * @param keyword
     * @return
     */
    List<StockDo> selectByKeyword(@Param("keyword") String keyword);

    /**
     * 查询所有的股票编码列表集合
     *
     * @return 查询所有的股票编码列表集合
     */
    List<String> listAllCode();

    /**
     * 根据条码查询信息
     *
     * @param stockQueryDto 条件
     */
    List<StockDo> findByCondition(@Param("stockQueryDto") StockQueryDto stockQueryDto);
}
