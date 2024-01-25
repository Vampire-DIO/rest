package com.lw.exception;

import com.lw.utils.ExceptionUtils;
import com.lw.entity.resp.R;
import com.lw.common.ResultCodeEnum;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
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

    @ExceptionHandler(VBlogException.class)
    @ResponseBody
    public R error(VBlogException e){
        log.error(ExceptionUtils.getMessage(e));
        return new R().error(e.getMessage(),e.getCode());
    }

    @ExceptionHandler(RollBackException.class)
    @ResponseBody
    public R error(RollBackException e){
        log.error(e.getMessage());
        return new R().error(e.getMessage(),e.getCode());
    }

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseBody
    public R error(ExpiredJwtException e){
        log.error(e.getMessage());
        return new R().error(ResultCodeEnum.TOKEN_EXPIRED);
    }

}
