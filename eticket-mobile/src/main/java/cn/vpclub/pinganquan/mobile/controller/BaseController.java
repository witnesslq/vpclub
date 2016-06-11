package cn.vpclub.pinganquan.mobile.controller;

import cn.vpclub.pinganquan.mobile.common.GenericException;
import cn.vpclub.pinganquan.mobile.dto.RpcResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        if (exp instanceof GenericException) {
            return new RpcResult(5000, "服务器处理错误", exp.getMessage());
        } else {
            return new RpcResult(5000, "服务器处理错误", exp.getMessage());
        }
    }


    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public RpcResult handleMissingServletRequestParameterException(MissingServletRequestParameterException exp, HttpServletRequest request, HttpServletResponse response) {
        logger.error("错误码：4001", exp);
        return new RpcResult(4001, "缺少必要的参数", exp.getMessage());
    }


    @ExceptionHandler(ServletRequestBindingException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public RpcResult handleServletRequestBindingException(ServletRequestBindingException exp, HttpServletRequest request, HttpServletResponse response) {
        logger.error("错误码：4002", exp);
        return new RpcResult(4002, "请求绑定错误", exp.getMessage());
    }

}
