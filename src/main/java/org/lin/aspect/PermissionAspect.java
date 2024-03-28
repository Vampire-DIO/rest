package org.lin.aspect;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.lin.entity.bo.User;
import org.lin.enums.ResultCodeEnum;
import org.lin.utils.AssertUtils;
import org.lin.utils.JwtUtils;
import org.lin.utils.ThreadLocalUtil;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author LvWei
 * @Date 2024/3/14 15:35
 */
@Aspect
@Component
@Slf4j
@Order(201)
public class PermissionAspect {
    @Before("@annotation(org.lin.aspect.Permission)")
    public void before(JoinPoint joinPoint){
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = sra.getRequest();

        String token = request.getHeader("token");
        AssertUtils.isTrue(StringUtils.isNotEmpty(token), ResultCodeEnum.TOKEN_EXPIRED);

        User user = JwtUtils.verifyToken(token);
        ThreadLocalUtil.setUserInfo(user);
    }
}
