package org.lin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.lin.entity.bo.User;
import org.lin.enums.ResultCodeEnum;
import org.lin.mapper.UserMapper;
import org.lin.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lin.utils.AssertUtils;
import org.lin.utils.JwtUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lw
 * @since 2024-03-13
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public String login(User user) {
        User one = getOne(new LambdaQueryWrapper<User>().eq(User::getEmail, user.getEmail()).eq(User::getPassword, user.getPassword()));
        AssertUtils.isFalse(one == null, ResultCodeEnum.USER_NOT_EXIST);
        return JwtUtils.createToken(one);
    }
}
