package top.yueshushu.learn.extension.domainservice;

import com.baomidou.mybatisplus.extension.service.IService;
import top.yueshushu.learn.extension.domain.ExtCustomerDo;

import java.util.List;

/**
 * @Description 系统客户
 * @Author yuejianli
 * @Date 2022/5/20 23:23
 **/
public interface ExtCustomerDomainService extends IService<ExtCustomerDo> {
    /**
     * 根据关键字查询客户信息
     *
     * @param keyword 关键字，支持账号,昵称,微信编号 查询
     * @return 根据关键字查询客户信息
     */
    List<ExtCustomerDo> listByKeyword(String keyword);

    /**
     * 查询账号是否存在
     *
     * @param userAccount 用户账号
     * @return 返回账号对应的客户信息
     */
    ExtCustomerDo getByAccount(String userAccount);
}
