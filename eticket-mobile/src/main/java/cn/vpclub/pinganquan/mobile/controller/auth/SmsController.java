package cn.vpclub.pinganquan.mobile.controller.auth;

import cn.vpclub.pinganquan.mobile.dto.UserDto;
import cn.vpclub.pinganquan.mobile.service.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * Created by Administrator on 2016/5/4.
 */
@Controller
public class SmsController {

    @Autowired
    private JwtService jwtService;


    @Autowired
    private ObjectMapper objectMapper;

    @Value("${customer.cookie.max-age}")
    private int maxAge;


    @RequestMapping("/sms/auth")
    public String resolveUserName(@RequestParam("pinganToken") String pinganToken, HttpServletResponse response) {
        // TODO：请参照下面的test()方法进行鉴权

        return "redirect:/ui/activity?activityId=";
    }


    @RequestMapping("/sms/auth/test")
    public String test(@RequestParam("activityId") String activityId,
                       HttpServletResponse response) throws IOException {

        UserDto user = new UserDto("", 1, "0", 0);
        // 将用户信息放入token中
        String token = jwtService.putUserIntoToken(user);

        // 将token放入cookie
        Cookie cookie = new Cookie("token", token);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);

        // 将user放入cookie
        String userString = objectMapper.writeValueAsString(user);
        String userStringEncoded = URLEncoder.encode(userString, "utf-8");
        Cookie cookie2 = new Cookie("user", userStringEncoded);
        cookie2.setPath("/");
        cookie2.setMaxAge(maxAge);
        response.addCookie(cookie2);

        // 将orgin放入cookie
        Cookie cookie3 = new Cookie("origin", "1");
        cookie3.setPath("/");
        cookie3.setMaxAge(Integer.MAX_VALUE);
        response.addCookie(cookie3);

        String redirectUrl = String.format("redirect:/ui/activity?activityId=%s&token=%s"
                , activityId, token); //这里的from和type是给分享链接用的
        return redirectUrl;
    }


    /**
     * 预览
     *
     * @param activityId
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping("activity/preview")
    public String preview(@RequestParam("activityId") String activityId,
                          HttpServletResponse response) throws IOException {
        // 将用户信息放入token中
        UserDto user = new UserDto("preview-user", 0, "0", 0);
        String token = jwtService.putUserIntoToken(user);

        // 将token放入cookie
        Cookie cookie = new Cookie("token", token);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
        // 将user放入cookie
        String userString = objectMapper.writeValueAsString(user);
        String userStringEncoded = URLEncoder.encode(userString, "utf-8");
        Cookie cookie2 = new Cookie("user", userStringEncoded);
        cookie2.setPath("/");
        cookie2.setMaxAge(maxAge);
        response.addCookie(cookie2);


        String redirectUrl = String.format("redirect:/ui/activity?activityId=%s&fromUserName=%s&fromUserType=%s&preview=%s"
                , activityId, "0", 0, true); //这里的from和type是给分享链接用的, 这里都设置为0
        return redirectUrl;
    }


}
