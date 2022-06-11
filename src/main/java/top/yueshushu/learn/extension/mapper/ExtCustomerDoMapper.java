package top.yueshushu.learn.extension.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import top.yueshushu.learn.extension.domain.ExtCustomerDo;

import java.util.List;

/**
 * <p>
 * 扩展用户
 * </p>
 *
 * @author 岳建立  自定义的
 * @since 2022-01-02
 */
public interface ExtCustomerDoMapper extends BaseMapper<ExtCustomerDo> {
    /**
     * 根据关键字查询客户信息
     *
     * @param keyword 关键字，支持账号,昵称,微信编号 查询
     * @return 根据关键字查询客户信息
     */
    List<ExtCustomerDo> listByKeyword(@Param("keyword") String keyword);
}
