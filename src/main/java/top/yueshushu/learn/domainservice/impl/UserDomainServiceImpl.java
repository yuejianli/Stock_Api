package top.yueshushu.learn.domainservice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.yueshushu.learn.domain.UserDo;
import top.yueshushu.learn.domainservice.UserDomainService;
import top.yueshushu.learn.mapper.UserDoMapper;
import top.yueshushu.learn.service.UserService;

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
        if (CollectionUtils.isEmpty(userList)){
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
    public List<Integer> listUserId() {
        return this.lambdaQuery()
                .list()
                .stream().map(
                        UserDo::getId
                ).collect(Collectors.toList());
    }
}
