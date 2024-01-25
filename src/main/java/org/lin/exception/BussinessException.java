package com.lw.exception;

import com.lw.common.ResultCodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class VBlogException extends RuntimeException{
    //状态码

    private Integer code;

    /**
     * 接受状态码和消息
     * @param code
     * @param message
     */
    public VBlogException(Integer code, String message) {
        super(message);
        this.setCode(code);
    }

    /**
     * 接收枚举类型
     * @param resultCodeEnum
     */
    public VBlogException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.setCode(resultCodeEnum.getCode());
    }

    @Override
    public String toString() {
        return "VBlogException{" +
                "code=" + code +
                ", message=" + this.getMessage() +
                '}';
    }
}
