package cn.vpclub.pinganquan.mobile.controller.auth;

import cn.vpclub.pinganquan.mobile.common.GenericException;
import cn.vpclub.pinganquan.mobile.common.StringUtils;
import cn.vpclub.pinganquan.mobile.config.WeixinSettings;
import cn.vpclub.pinganquan.mobile.dto.UserDto;
import cn.vpclub.pinganquan.mobile.service.CookieService;
import cn.vpclub.pinganquan.mobile.service.JwtService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

/**
 * Created by Administrator on 2016/5/9.
 */

@Controller
public class WeixinController {

    private static final Logger logger = LoggerFactory.getLogger(WeixinController.class);

    @Autowired
    private CookieService cookieService;
    @Autowired
    private JwtService jwtService;

    @Autowired
    private WeixinSettings weixinSettings;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${customer.cookie.max-age}")
    private int maxAge;

    @RequestMapping("/weixin/auth/getCode")
    public String getCode(@RequestParam(value = "code", required = false) String code,
                          @RequestParam("activityId") String activityId,
                          @RequestParam(value = "token", required = false) String token,
                          @RequestParam(value = "fromUserName", required = false) String fromUserName,
                          HttpServletResponse response){

        logger.info("code --->{}", code);
        logger.info("token --->{}", token);
        if(StringUtils.isNotEmpty(token) && !token.equals("null")){
            try {
                logger.info("userDto--->{}", jwtService.getUserFromToken(token));
                fromUserName = jwtService.getUserFromToken(token).getFromUserName();
            }catch (GenericException e){
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        }
        logger.info("fromUserName --->{}", fromUserName);
        logger.info("validate code --->{}", code == null || code.equals("null"));

        if(code == null || code.equals("null")){
            String encodedRedirectUrl = null;
            try {
                logger.info("改了没得用，要跪了");
                encodedRedirectUrl = URLEncoder.encode(weixinSettings.getRedirectUrl() + "?activityId=" + activityId + "&token=" + token + "&fromUserName=" + fromUserName, "utf-8");
                logger.info("encodedRedirectUrl -->{}", encodedRedirectUrl);

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
            }

            String weixinAuthUrl = String.format("https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_base&state=123eex#wechat_redirect", weixinSettings.getAppid(), encodedRedirectUrl);

            logger.info("weixinAuthUrl:--->" + weixinAuthUrl);
            return "redirect:" + weixinAuthUrl;
        }

        // 从微信api中获取openid
        String url = String.format("https://api.weixin.qq.com/sns/oauth2/access_token?" + "appid=%s&secret=%s&code=%s&grant_type=authorization_code", weixinSettings.getAppid(), weixinSettings.getSecret(), code);
        RestTemplate template = new RestTemplate();
        String result = template.getForObject(url, String.class);
        JsonNode rootNode = null;
        try {
            rootNode = objectMapper.readTree(result);
        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        String openId = rootNode.get("openid").asText();

        logger.info("openId:-->{}" , openId);

        // 将用户信息放入token中
        UserDto user = new UserDto(openId, 2, "", 1);
        logger.info("before: --->{}", user);
        logger.info("validate: token -->{}",StringUtils.isEmpty(token) || token.equals("null"));

        /**
         * cookieToken 用于记录用户的来源，区别拿去分享的token
         */
        String cookieToken = null;
        if(StringUtils.isEmpty(token) || token.equals("null"))
        {
            cookieToken = jwtService.putUserIntoToken(user);
        }else{
            user = jwtService.getUserFromToken(token);
            user.setUserName(openId);
            user.setUserType(2);
            cookieToken = jwtService.putUserIntoToken(user);
        }
        user.setFromUserName(openId);
        user.setFromUserType(2);

        logger.info("after: --->{}", user);
        token = jwtService.putUserIntoToken(user);

        // 输出 cookie
        logger.info(new Date().toString());
        cookieService.add("cookieToken", cookieToken,response);
        cookieService.add("activityId", activityId,response);

//        String userStringEncoded = null;
//        try {
//            userStringEncoded = URLEncoder.encode(userString, "utf-8");
//        } catch (UnsupportedEncodingException e) {
//            logger.error(e.getMessage());
//            e.printStackTrace();
//        }
//        Cookie cookie2 = new Cookie("user", userStringEncoded);
//        cookie2.setPath("/");
//        cookie2.setMaxAge(maxAge);
//        response.addCookie(cookie2);

        String redirectUrl = String.format("redirect:/ui/activity?activityId=%s&token=%s"
                , activityId, token); //这里的token是给分享链接用的
        return redirectUrl;
    }


}
