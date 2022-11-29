package top.yueshushu.learn.mapper;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import top.yueshushu.learn.domain.StockBigDealDo;

/**
 * 大宗交易的 Mapper
 *
 * @author Yue Jianli
 * @date 2022-11-29
 */
public interface StockBigDealDoMapper extends BaseMapper<StockBigDealDo> {

    void deleteHasAsyncData(@Param("fullCode") String fullCode, @Param("currentDate") DateTime currentDate);


}
