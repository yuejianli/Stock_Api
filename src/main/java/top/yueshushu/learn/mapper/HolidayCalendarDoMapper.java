package top.yueshushu.learn.mapper;

import org.apache.ibatis.annotations.Param;
import top.yueshushu.learn.domain.HolidayCalendarDo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 法定假期表(只写入法定的类型) Mapper 接口
 * </p>
 *
 * @author 岳建立  自定义的
 * @since 2022-01-02
 */
public interface HolidayCalendarDoMapper extends BaseMapper<HolidayCalendarDo> {

}
