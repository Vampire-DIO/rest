package org.lin.utils;

import lombok.extern.slf4j.Slf4j;
import org.lin.entity.bo.User;
import org.lin.enums.ResultCodeEnum;

/**
 * @Author LvWei
 * @Date 2024/3/14 15:38
 */
@Slf4j
public class ThreadLocalUtil {

    private static final ThreadLocal<User> userInfo = new ThreadLocal<>();
    public static User getUser(){
        User user = ThreadLocalUtil.userInfo.get();
        AssertUtils.isFalse(user == null, ResultCodeEnum.FETCH_USERINFO_ERROR);
        return user;
    }

    public static void setUserInfo(User userInfo) {
        ThreadLocalUtil.userInfo.set(userInfo);
    }


    public static void cleanUp() {
        userInfo.remove();
    }

}
