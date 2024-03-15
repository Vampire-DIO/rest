package org.lin.utils;

import org.apache.commons.lang3.StringUtils;
import org.lin.enums.ResultCodeEnum;
import org.lin.exception.BussinessException;
import org.springframework.lang.Nullable;

public class AssertUtils {
    private static final String DETAIL_ID_MSG = ", id: %s";

    private static final String DETAIL_ERROR_MSG = " errorData: 【%s】";

    private AssertUtils() {
    }

    public static void isTrue(boolean expression, ResultCodeEnum ResultCodeEnum) {
        if (!expression) {
            throw new BussinessException(ResultCodeEnum);
        }
    }

    /**
     *
     * @param exposeDataId 是否暴露异常数据Id，根据该字段拼接不同提示
     * @param errorMsg 异常数据详情
     */

    public static void isTrue(boolean expression, ResultCodeEnum ResultCodeEnum, boolean exposeDataId, String errorMsg) {
        if (!expression) {
            if (exposeDataId) {
                throw new BussinessException(ResultCodeEnum.getCode(), ResultCodeEnum.getMessage() + String.format(DETAIL_ID_MSG, errorMsg));
            } else {
                throw new BussinessException(ResultCodeEnum.getCode(), ResultCodeEnum.getMessage() + String.format(DETAIL_ERROR_MSG, errorMsg));
            }
        }
    }

    public static void isTrue(boolean expression, int code, String msg) {
        if (!expression) {
            throw new BussinessException(code, msg);
        }
    }

    public static void isFalse(boolean expression, ResultCodeEnum ResultCodeEnum) {
        if (expression) {
            throw new BussinessException(ResultCodeEnum);
        }
    }

    public static void isFalse(boolean expression, int code, String msg) {
        if (expression) {
            throw new BussinessException(code,msg);
        }
    }

    /**
     *
     * @param exposeDataId 是否暴露异常数据Id，根据该字段拼接不同提示
     * @param errorMsg 异常数据详情
     */
    public static void isFalse(boolean expression, ResultCodeEnum ResultCodeEnum, boolean exposeDataId, String errorMsg) {
        if (expression) {
            if (exposeDataId) {
                throw new BussinessException(ResultCodeEnum.getCode(), ResultCodeEnum.getMessage() + String.format(DETAIL_ID_MSG, errorMsg));
            } else {
                throw new BussinessException(ResultCodeEnum.getCode(), ResultCodeEnum.getMessage() + String.format(DETAIL_ERROR_MSG, errorMsg));
            }
        }
    }


    public static void isNull(@Nullable Object object, ResultCodeEnum ResultCodeEnum) {
        if (object != null) {
            throw new BussinessException(ResultCodeEnum);
        }
    }

    /**
     *
     * @param exposeDataId 是否暴露异常数据Id，根据该字段拼接不同提示
     * @param errorMsg 异常数据详情
     */
    public static void isNull(@Nullable Object object, ResultCodeEnum ResultCodeEnum, boolean exposeDataId, String errorMsg) {
        if (object != null) {
            if (exposeDataId) {
                throw new BussinessException(ResultCodeEnum.getCode(), ResultCodeEnum.getMessage() + String.format(DETAIL_ID_MSG, errorMsg));
            } else {
                throw new BussinessException(ResultCodeEnum.getCode(), ResultCodeEnum.getMessage() + String.format(DETAIL_ERROR_MSG, errorMsg));
            }
        }
    }

    public static void notNull(@Nullable Object object, ResultCodeEnum ResultCodeEnum) {
        if (object == null) {
            throw new BussinessException(ResultCodeEnum);
        }
    }

    public static void notNull(@Nullable Object object, int code, String msg) {
        if (object == null) {
            throw new BussinessException(code,msg);
        }
    }


    public static void notNull(@Nullable Object object, ResultCodeEnum ResultCodeEnum, boolean exposeDataId, String errorMsg) {
        if (object == null) {
            if (exposeDataId) {
                throw new BussinessException(ResultCodeEnum.getCode(), ResultCodeEnum.getMessage() + String.format(DETAIL_ID_MSG, errorMsg));
            } else {
                throw new BussinessException(ResultCodeEnum.getCode(), ResultCodeEnum.getMessage() + String.format(DETAIL_ERROR_MSG, errorMsg));
            }
        }
    }

    public static void isBlank(String text, ResultCodeEnum ResultCodeEnum) {
        if (!StringUtils.isBlank(text)) {
            throw new BussinessException(ResultCodeEnum);
        }
    }

    public static void isNotBlank(String text, ResultCodeEnum ResultCodeEnum) {
        if (StringUtils.isBlank(text)) {
            throw new BussinessException(ResultCodeEnum);
        }
    }
}
