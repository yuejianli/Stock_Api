package top.yueshushu.learn.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import top.yueshushu.learn.domain.UserDo;
import top.yueshushu.learn.entity.User;
import top.yueshushu.learn.mode.ro.QueryUserRo;

import java.util.List;

/**
 * <p>
 * 登录用户表 Mapper 接口
 * </p>
 *
 * @author 岳建立  自定义的
 * @since 2022-01-02
 */
public interface UserDoMapper extends BaseMapper<UserDo> {
    /**
     * 根据信息进行查询
     *
     * @param queryUserRo 查询对象
     */
    List<User> listByCondition(@Param("queryUserRo") QueryUserRo queryUserRo);
}
