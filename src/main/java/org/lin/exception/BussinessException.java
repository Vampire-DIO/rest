package org.lin.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.lin.entity.enums.ResultCodeEnum;

@Data
@EqualsAndHashCode(callSuper = true)
public class BussinessException extends RuntimeException{
    //状态码

    private Integer code;

    /**
     * 接受状态码和消息
     * @param code
     * @param message
     */
    public BussinessException(Integer code, String message) {
        super(message);
        this.setCode(code);
    }

    /**
     * 接收枚举类型
     * @param resultCodeEnum
     */
    public BussinessException(ResultCodeEnum resultCodeEnum) {
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
