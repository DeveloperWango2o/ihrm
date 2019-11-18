package com.ihrm.common.handler;

import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.common.exception.CommonException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义公共异常处理器
 *      1:声明
 *      2.处理异常
 */
@ControllerAdvice
public class BaseExceptionHandler {


    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result error(HttpServletRequest request, HttpServletResponse response, Exception e){
        if (e.getClass() == CommonException.class){
            CommonException commonException = (CommonException) e;
            //是自定义的异常
            Result result = new Result(commonException.getResultCode());
            return result;
        }else {
            Result result = new Result(ResultCode.SERVER_ERROR);
            return result;
        }

    }
}
