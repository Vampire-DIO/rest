package org.lin.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.lin.enums.ResultCodeEnum;

import java.io.Serializable;

@Data
@NoArgsConstructor
@ApiModel("全局统一返回结果")
public class R<T> implements Serializable {
    @ApiModelProperty(value = "是否成功")
    private Boolean success;

    @ApiModelProperty(value = "返回码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private T data;

    public R<T> ok() {
        this.success = ResultCodeEnum.SUCCESS.getSuccess();
        this.code = ResultCodeEnum.SUCCESS.getCode();
        this.message = ResultCodeEnum.SUCCESS.getMessage();
        return this;
    }

    public R<T> error() {
        this.message = ResultCodeEnum.UNKNOWN_REASON.getMessage();
        this.success = ResultCodeEnum.UNKNOWN_REASON.getSuccess();
        this.code = ResultCodeEnum.UNKNOWN_REASON.getCode();
        return this;
    }

    public R<T> error(ResultCodeEnum resultCodeEnum){
        this.message = resultCodeEnum.getMessage();
        this.success = false;
        this.code = resultCodeEnum.getCode();
        return this;
    }

    public R<T> error(String message, int code){
        this.message = message;
        this.code = code;
        this.success =false;
        return this;
    }


    public R<T> setResult(ResultCodeEnum resultCodeEnum) {
        R r = new R();
        r.setSuccess(resultCodeEnum.getSuccess());
        r.setCode(resultCodeEnum.getCode());
        r.setMessage(resultCodeEnum.getMessage());
        return r;
    }

    public R<T> message(String message) {
        this.setMessage(message);
        return this;
    }

    public R code(Integer code) {
        this.setCode(code);
        return this;
    }

    public R<T> data(T data) {
        this.data = data;
        this.code = 0;
        this.success = true;
        return this;
    }

}


