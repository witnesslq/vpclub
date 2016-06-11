package cn.vpclub.pinganquan.mobile.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2016/6/11 0011.
 */
@Service
public class CookieService {

    @Value("${customer.cookie.max-age}")
    private int maxAge;

    public void add(String key, String value, HttpServletResponse response){
        // 输出 cookie
        Cookie cookie = new Cookie(key, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }
}
