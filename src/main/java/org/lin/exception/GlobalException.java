package org.lin.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.lin.enums.ResultCodeEnum;
import org.lin.entity.vo.R;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e){
        log.error(ExceptionUtils.getMessage(e));
        //返回json数据
        return new R().error("系统错误",5000);
    }

    @ExceptionHandler(BadSqlGrammarException.class)
    @ResponseBody
    public R error(BadSqlGrammarException e){
        log.error(ExceptionUtils.getMessage(e));
        return new R().error(ResultCodeEnum.BAD_SQL_GRAMMAR);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public R error(HttpMessageNotReadableException e){
        log.error(ExceptionUtils.getMessage(e));
        return new R().error(ResultCodeEnum.JSON_PARSE_ERROR);
    }

    //参数异常
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public R error(BindException e){
        String message = e.getFieldError().getDefaultMessage();
        return new R().error(message, ResultCodeEnum.PARAM_ERROR.getCode());
    }

    @ExceptionHandler(BussinessException.class)
    @ResponseBody
    public R error(BussinessException e){
        log.error(ExceptionUtils.getMessage(e));
        return new R().error(e.getMessage(),e.getCode());
    }

}
