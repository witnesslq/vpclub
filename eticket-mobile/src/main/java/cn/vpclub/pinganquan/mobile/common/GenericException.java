package cn.vpclub.pinganquan.mobile.common;

/**
 * Created by Administrator on 2016/5/11.
 * 通用异常类，用于捕获自定义异常
 */
public class GenericException extends RuntimeException {

    public GenericException(String message) {
        super(message);
    }
}
