package top.yueshushu.learn.domainservice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.yueshushu.learn.domain.UserDo;
import top.yueshushu.learn.domainservice.UserDomainService;
import top.yueshushu.learn.entity.User;
import top.yueshushu.learn.enumtype.DataFlagType;
import top.yueshushu.learn.mapper.UserDoMapper;
import top.yueshushu.learn.mode.ro.QueryUserRo;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description TODO
 * @Author yuejianli
 * @Date 2022/5/20 23:23
 **/
@Service
@Slf4j
public class UserDomainServiceImpl extends ServiceImpl<UserDoMapper, UserDo>
        implements UserDomainService {
    @Resource
    private UserDoMapper userDoMapper;

    @Override
    public UserDo getByAccount(String account) {
        List<UserDo> userList = this.lambdaQuery()
                .eq(
                        UserDo::getAccount,
                        account
                ).list();
        if (CollectionUtils.isEmpty(userList)) {
            return null;
        }
        return userList.get(0);
    }

    @Override
    public void updateUser(UserDo userDo) {
        userDoMapper.updateById(
                userDo
        );
    }

    @Override
    public List<Integer> listUserIds() {
        return this.lambdaQuery()
                .list()
                .stream().map(
                        UserDo::getId
                ).collect(Collectors.toList());
    }

    @Override
    public List<Integer> listUseUserIds() {
        return this.lambdaQuery()
                .eq(UserDo::getStatus, DataFlagType.NORMAL.getCode())
                .list()
                .stream().map(
                        UserDo::getId
                ).collect(Collectors.toList());
    }

    @Override
    public List<User> listByCondition(QueryUserRo queryUserRo) {
        return userDoMapper.listByCondition(queryUserRo);
    }

    @Override
    public User getByCondition(QueryUserRo queryUserRo) {
        List<User> userList = listByCondition(queryUserRo);
        return CollectionUtils.isEmpty(userList) ? null : userList.get(0);
    }
}
