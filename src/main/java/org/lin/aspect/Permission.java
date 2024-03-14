package org.lin.aspect;

import java.lang.annotation.*;

/**
 * @Author LvWei
 * @Date 2024/3/14 15:35
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Permission {
}
