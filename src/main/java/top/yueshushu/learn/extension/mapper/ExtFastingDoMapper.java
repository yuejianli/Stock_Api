package top.yueshushu.learn.extension.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import top.yueshushu.learn.extension.domain.ExtFastingDo;

/**
 * <p>
 * 斋戒日期
 * </p>
 *
 * @author 岳建立  自定义的
 * @since 2022-01-02
 */
public interface ExtFastingDoMapper extends BaseMapper<ExtFastingDo> {
    /**
     * 根据农历的 月，天 或者节气查询，是否存在相应的记录信息。
     *
     * @param month 月
     * @param day   天
     * @param term  节气
     * @return 根据农历的 月，天 或者节气查询，是否存在相应的记录信息。
     */
    ExtFastingDo getByMonthAndDayAndTerm(@Param("month") int month, @Param("day") int day, @Param("term") String term);
}
