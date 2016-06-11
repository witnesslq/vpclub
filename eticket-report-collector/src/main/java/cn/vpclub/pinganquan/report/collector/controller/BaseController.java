package cn.vpclub.pinganquan.report.collector.controller;


import cn.vpclub.pinganquan.report.collector.dto.RpcResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/3/8.
 */

@ControllerAdvice
public class BaseController {

    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    @ModelAttribute
    public void init(HttpServletRequest request, HttpServletResponse response) {
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public RpcResult handleException(Exception exp, HttpServletRequest request, HttpServletResponse response) {
        logger.error("错误码：5000", exp);
        return new RpcResult(5000, "服务器处理错误", null);
    }


    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public RpcResult handleException2(MissingServletRequestParameterException exp, HttpServletRequest request, HttpServletResponse response) {
        logger.error("错误码：4000", exp);
        return new RpcResult(4000, "缺少必要的参数:" + exp.getMessage(), null);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public RpcResult handleException3(MethodArgumentNotValidException exp, HttpServletRequest request, HttpServletResponse response) {
        logger.error("错误码：4000", exp);
        List<ObjectError> objectErrors = exp.getBindingResult().getAllErrors();
        HashMap<String, String> result = new HashMap<>();
        for (ObjectError objectError : objectErrors) {
            FieldError fieldError = (FieldError) objectError;
            String field = fieldError.getField();
            result.put(fieldError.getField(), fieldError.getDefaultMessage());
            System.out.print(field);
        }
        return new RpcResult(4000, "入参不合法", result);
    }

}
