package cn.vpclub.pinganquan.mobile.controller.auth1;

import cn.vpclub.pinganquan.mobile.common.GenericException;
import cn.vpclub.pinganquan.mobile.config.WeixinSettings;
import cn.vpclub.pinganquan.mobile.domain.ActivityInfo;
import cn.vpclub.pinganquan.mobile.domain.TemplateInfo;
import cn.vpclub.pinganquan.mobile.dto.UserDto;
import cn.vpclub.pinganquan.mobile.dto.WeixinJsapiConfigDto;
import cn.vpclub.pinganquan.mobile.dto.WeixinJsapiShareMessageDto;
import cn.vpclub.pinganquan.mobile.service.ActivityInfoService;
import cn.vpclub.pinganquan.mobile.service.JwtService;
import cn.vpclub.pinganquan.mobile.service.TemplateInfoService;
import cn.vpclub.pinganquan.mobile.service.WeixinService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Random;

/**
 * Created by Administrator on 2016/5/19.
 */
//@Controller
public class UiController {

    Logger logger = LoggerFactory.getLogger(UiController.class);

    @Autowired
    private WeixinSettings weixinSettings;


    @Value("${customer.client.url-base1}")
    private String urlBase1;


    @Value("${customer.client.url-base2}")
    private String urlBase2;

    @Autowired
    private ActivityInfoService activityInfoService;

    @Autowired
    private JwtService jwtService;


    @Autowired
    private TemplateInfoService templateInfoService;


    @Autowired
    private WeixinService weixinService;


//    @RequestMapping("/ui/activity")
    public String index(@RequestParam("activityId") String activityId,
                        @RequestParam(value = "token", required = false) String token,
                        HttpServletRequest request, Model model) throws IOException {

        // 获取浏览器信息
        String userAgent = request.getHeader("User-Agent");
        if (userAgent == null) {
            throw new GenericException("非法请求，无浏览器标识");
        }
        logger.info("userAgent:--->" + userAgent);
        logger.info("token: " + token);
        logger.info("cookie:token-->" + token);

        String currentPageUrl = request.getRequestURL().toString() + "?" + request.getQueryString();
        // 获取模板信息
        TemplateInfo templateInfo = templateInfoService.findByActivityId(activityId);
        if (templateInfo == null) {
            throw new GenericException("找不到活动模板！");
        }
        String redirect = "";

        // 若为微信浏览器
        if (userAgent.contains("MicroMessenger")) {
            if (token == null || token.equals("null")) {  // 若token为空, 跳转去取token
                String encodedRedirectUrl = URLEncoder.encode(weixinSettings.getRedirectUrl() +
                        "?activityId=" + activityId, "utf-8");

                String weixinAuthUrl = String.format("https://open.weixin.qq.com/connect/oauth2/authorize?" + "appid=%s&redirect_uri=%s&response_type=code"
                        + "&scope=snsapi_base&state=123eex#wechat_redirect",
                        weixinSettings.getAppid(), encodedRedirectUrl);
                logger.info("weixinAuthUrl:--->" + weixinAuthUrl);
                redirect = "redirect:" + weixinAuthUrl;

            } else {
                // 若token不为空，在进入页面的时候，给weixin jsapi所需要的参数赋值

                // 从token中获取用户信息
                UserDto user = jwtService.getUserFromToken(token);
                String fromUserName = user.getFromUserName();
                String userName = user.getUserName();
                logger.info("fromUserName ---> {}",fromUserName);
                logger.info("userName ---> {}",userName);

                if (!userName.equals(fromUserName)) { // 若url地址中的from不等于当前的用户名，则使用重定向来重写url中的参数
                    String redirectUrl = String.format("/ui/activity?activityId=%s&token=%s"
                            , activityId, token);
                    logger.info("redirectUrl ----> " + redirectUrl);
                    redirect = "redirect:" + redirectUrl;
                } else {
                    // jsapi config
                    WeixinJsapiConfigDto jsapiConfig = weixinService.getWeixinJsapiConfig(currentPageUrl);
                    model.addAttribute("jsapiConfig", jsapiConfig);
                    model.addAttribute("token", token);
                    redirect =  String.format("/activity-template/%s/index", templateInfo.getId());
                }
            }
            // 若为普通浏览器，则
        } else {
            model.addAttribute("token", token);
            model.addAttribute("jsapiConfig", new WeixinJsapiConfigDto());
            redirect =  String.format("/activity-template/%s/index", templateInfo.getId());
        }


        model.addAttribute("templateUrl", templateInfo.getTemplateUrl());
        model.addAttribute("urlBase1", urlBase1);
        model.addAttribute("urlBase2", urlBase2);
        model.addAttribute("nonce", Integer.toString(new Random().nextInt(1000000)));

        ActivityInfo activityInfo = activityInfoService.findActivityInfoById(activityId);
        String htmlTitle = activityInfo.getaTitle();
        model.addAttribute("htmlTitle", htmlTitle);

        // jsapi share message
        WeixinJsapiShareMessageDto jsapiShareMessage = weixinService.getWeixinJsapiShareMessage(activityId, currentPageUrl);
        model.addAttribute("jsapiShareMessage", jsapiShareMessage);
        return redirect;
    }


    @RequestMapping("/ui/activity/tokenExpired")
    @ResponseBody
    public String tokenExpired() {
        return "token过期，请重新从短信链接进入!";
    }


}



