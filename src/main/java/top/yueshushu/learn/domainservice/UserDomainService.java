package top.yueshushu.learn.domainservice;

import com.baomidou.mybatisplus.extension.service.IService;
import top.yueshushu.learn.domain.UserDo;

import java.util.List;

/**
 * @Description 用户的操作
 * @Author yuejianli
 * @Date 2022/5/20 23:23
 **/
public interface UserDomainService extends IService<UserDo> {
    /**
     * 根据账户查询用户的信息
     * @param account 账号
     * @return 根据账户查询用户的信息
     */
    UserDo getByAccount(String account);

    /**
     * 更新用户信息
     *
     * @param userDo 传递过来的用户对象
     */
    void updateUser(UserDo userDo);

    /**
     * 查询所有的用户id 集合
     *
     * @return 查询所有的用户id 集合
     */
    List<Integer> listUserIds();

    /**
     * 查询正在使用的用户列表信息
     */
    List<Integer> listUseUserIds();
}
