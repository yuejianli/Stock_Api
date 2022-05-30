package top.yueshushu.learn.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import top.yueshushu.learn.domain.StockSelectedDo;

import java.util.List;

/**
 * <p>
 * 股票自选表,是用户自己选择的 Mapper 接口
 * </p>
 *
 * @author 岳建立  自定义的
 * @since 2022-01-02
 */
public interface StockSelectedDoMapper extends BaseMapper<StockSelectedDo> {
    /**
     * 根据用户和关键字筛选出相应的自选记录信息
     * @param userId 系统用户Id
     * @param keyword 关键字
     * @return 根据用户和关键字筛选出相应的自选记录信息
     */
    List<StockSelectedDo> listByUserIdAndKeyword(@Param("userId") Integer userId, @Param("keyword") String keyword);


    /**
     * 查询所有的股票代码编号，用于同步
     *
     * @return
     */
    List<String> findCodeList(@Param("userId") Integer userId);
}
